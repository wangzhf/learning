package demo

import (
	"fmt"
	"log"
	"net/http"
	"reflect"
	"runtime/debug"
	"time"

	"github.com/gin-gonic/gin/binding"

	"gopkg.in/go-playground/validator.v8"

	"github.com/gin-gonic/gin"
)

// Init is a gin test
func Init() {
	r := gin.Default()
	r.GET("/ping", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "pong",
		})
	})

	// match /user/join, not match /user/ or /user
	r.GET("/user/:name", func(c *gin.Context) {
		name := c.Param("name")
		c.String(http.StatusOK, "Hello %s", name)
	})

	// match /user/join/ and also /user/john/send
	r.GET("/user/:name/*action", func(c *gin.Context) {
		name := c.Param("name")
		action := c.Param("action")
		message := name + " is " + action
		c.String(http.StatusOK, message)
	})

	// query parameters
	r.GET("/welcome", func(c *gin.Context) {
		firstname := c.DefaultQuery("firstname", "Guest")
		lastname := c.Query("lastname")
		c.String(http.StatusOK, "Hello %s %s ", firstname, lastname)
	})

	// post form
	r.POST("/form_post", func(c *gin.Context) {
		message := c.PostForm("message")
		nick := c.DefaultPostForm("nick", "anonymous")

		c.JSON(http.StatusOK, gin.H{
			"status":  "posted",
			"message": message,
			"nick":    nick,
		})
	})

	/**
	query and post form
	POST /post?id=1234&page=1 HTTP/1.1
	Content-Type: application/x-www-form-urlencoded

	name=manu&message=this_is_great
	*/
	r.POST("/post", func(c *gin.Context) {
		id := c.Query("id")
		page := c.DefaultQuery("page", "0")
		name := c.PostForm("name")
		message := c.PostForm("message")

		fmt.Printf("id %s; page: %s; name: %s; message: %s", id, page, name, message)
	})

	/**
	 * Map as querystring or postform parameters
	 * POST /post?ids[a]=1234&ids[b]=hello HTTP/1.1
		Content-Type: application/x-www-form-urlencoded

		names[first]=thinkerou&names[second]=tianou
	*/
	r.POST("/post_map", func(c *gin.Context) {
		ids := c.QueryMap("ids")
		names := c.PostFormMap("names")
		fmt.Printf("ids: %v; names: %v", ids, names)
	})

	/**
	 * upload files
	 *
	 */
	r.POST("/upload", func(c *gin.Context) {
		// single file
		file, err := c.FormFile("file")
		if err != nil {
			log.Println("error ocurred...")
			log.Println(err.Error())
			log.Println(debug.Stack())
			return
		}
		log.Println(file.Filename)

		// Upload the file to specific dist
		// dist := "~/Download/"
		// c.SaveUploadedFile(file, dist)

		c.String(http.StatusOK, fmt.Sprintf("'%s' uploaded !", file.Filename))

	})

	// Model binding and validation
	type Login struct {
		User     string `form:"user" json:"user" xml:"user" binding:"required"`
		Password string `form:"password" json:"password" xml:"password" binding:"required" `
	}

	r.POST("/loginJSON", func(c *gin.Context) {
		var json Login
		if err := c.ShouldBindJSON(&json); err != nil {
			log.Println(err.Error())
			c.JSON(http.StatusBadRequest, gin.H{
				"error": err.Error(),
			})
			return
		}

		if json.User != "manu" || json.Password != "123" {
			c.JSON(http.StatusUnauthorized, gin.H{
				"status": "unauthorized",
			})
			return
		}

		c.JSON(http.StatusOK, gin.H{
			"status": "you are logged in",
		})
	})

	// Custom Validators
	if v, ok := binding.Validator.Engine().(*validator.Validate); ok {
		v.RegisterValidation("bookabledate", bookableDate)
	}
	r.GET("/bookable", getBookable)

	r.Run(":8080")
}

// Booking is a Custom Validator
type Booking struct {
	CheckIn  time.Time `form:"check_in" binding:"required,bookabledate" time_format:"2006-01-02"`
	CheckOut time.Time `form:"check_out" binding:"required,gtfield=CheckIn" time_format:"2006-01-02"`
}

func bookableDate(
	v *validator.Validate, topStruct reflect.Value, currentStructOrField reflect.Value,
	field reflect.Value, fieldType reflect.Type, fieldKind reflect.Kind, param string,
) bool {
	if date, ok := field.Interface().(time.Time); ok {
		today := time.Now()
		if today.Year() > date.Year() || today.YearDay() > date.YearDay() {
			return false
		}
	}
	return true
}

func getBookable(c *gin.Context) {
	var b Booking
	if err := c.ShouldBindWith(&b, binding.Query); err == nil {
		c.JSON(http.StatusOK, gin.H{
			"message": "Booking dates are valid!",
		})
	} else {
		c.JSON(http.StatusOK, gin.H{
			"message": err.Error(),
		})
	}
}

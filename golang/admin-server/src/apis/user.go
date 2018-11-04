package user

import (
	mongodb "db"
	"fmt"
	"log"
	user "models"
	"net/http"

	"github.com/gin-gonic/gin"
)

// AddUserAPI should add a user
func AddUserAPI(c *gin.Context) {
	// validation
	var u user.User
	if err := c.ShouldBind(&u); err != nil {
		fmt.Println("here")
		log.Println(err.Error())
		c.JSON(http.StatusBadRequest, gin.H{
			"error": err.Error(),
		})
		return
	}

	id, err := u.Add()
	if err != nil {
		log.Println(err.Error())
	}

	// add to mongo
	mongodb.AddUser()

	msg := fmt.Sprintf("add user successful %d", id)
	c.JSON(http.StatusOK, gin.H{
		"msg": msg,
	})
}

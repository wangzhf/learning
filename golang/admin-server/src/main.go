package main

import (
	apis "apis"

	"github.com/gin-gonic/gin"
)

func main() {
	router := initRouter()
	router.Run(":8080")
}

func initRouter() *gin.Engine {
	router := gin.Default()
	router.GET("/user/add", apis.AddUserAPI)
	return router
}

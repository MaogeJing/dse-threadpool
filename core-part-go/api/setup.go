package api

import (
	"github.com/gin-gonic/gin"
	"github.com/maogejing/dse/threadpool/api/router"
)

func SetUpRouter(gin *gin.Engine) {
	publicRouter := gin.Group("")
	router.NewToolRouter(publicRouter)
}

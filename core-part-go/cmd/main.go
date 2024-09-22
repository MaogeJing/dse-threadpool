package main

import (
	"github.com/gin-gonic/gin"
	"github.com/maogejing/dse/threadpool/api"
)

func main() {
	engine := gin.Default()
	api.SetUpRouter(engine)
	engine.Run() // listen and serve on 0.0.0.0:8080 (for windows "localhost:8080")
}

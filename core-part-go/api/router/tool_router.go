package router

import (
	"github.com/gin-gonic/gin"
	"github.com/maogejing/dse/threadpool/api/controller"
)

func NewToolRouter(group *gin.RouterGroup) {
	tool_controller := &controller.ToolController{}
	group.POST("/tool/delay", tool_controller.Delay)
}

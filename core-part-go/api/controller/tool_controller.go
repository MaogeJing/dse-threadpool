package controller

import (
	"net/http"
	"time"

	"github.com/gin-gonic/gin"
	"github.com/maogejing/dse/threadpool/domain"
)

type ToolController struct {
}

func (tc *ToolController) Delay(c *gin.Context) {
	var params domain.ToolAPIDelayRequestParams
	if err := c.ShouldBindJSON(&params); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"err": err.Error()})
	}

	acceptTime := time.Now()
	targetDelay := time.Duration(params.ExpMsDelay) * time.Millisecond
	time.Sleep(targetDelay)
	returnTime := time.Now()

	actualDelayMs := returnTime.Sub(acceptTime).Milliseconds()

	response := domain.ToolAPIDelayResultResponse{
		RequestNo:  params.RequestNo,
		AcceptTime: acceptTime,
		ReturnTime: returnTime,
		ActDelayMs: actualDelayMs,
	}
	c.JSON(http.StatusOK, response)
}

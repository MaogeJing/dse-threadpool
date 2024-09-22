package domain

import "time"

type ToolAPIDelayRequestParams struct {
	RequestNo  string `json:"requestNo"`
	ExpMsDelay int64  `json:"expMsDelay"` //期望接口 delay 的ms
}

type ToolAPIDelayResultResponse struct {
	RequestNo  string    `json:"requestNo"`
	ActDelayMs int64     `json:"actDelayMs"`
	AcceptTime time.Time `json:"acceptTime"`
	ReturnTime time.Time `json:"returnTime"`
}

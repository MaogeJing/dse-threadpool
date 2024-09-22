package com.maoge.dosomexperiments.domain.goend;

import lombok.Data;

import java.util.Date;

@Data
public class ToolAPIDelayResultResponse {
    private String requestNo;
    private Integer actDelayMs;
    private String acceptTime;
    private String returnTime;
}

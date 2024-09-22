package com.maoge.dosomexperiments.domain.task;

import lombok.Getter;

/**
 * 请求类型枚举
 */
@Getter
public enum RequestTaskRunStrategyTypeEnum {
    OKHTTP_SYNC("OS"),
    OKHTTP_ASYNC("OA"),
    ;

    private String taskPrefix;
    RequestTaskRunStrategyTypeEnum(String taskPrefix){
        this.taskPrefix = taskPrefix;
    }

}

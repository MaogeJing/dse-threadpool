package com.maoge.dosomexperiments.domain.task;

/**
 * 请求任务状态
 */
public enum RequestTaskStatusEnum {
    /** 新请求 */
    INCOMING(0, false),
    /** 运行中 */
    RUNNING(1, false),
    /** 等待中 */
    WAITING(2, false),
    /** 已拒绝【终态】 */
    REJECTED(3, true),
    /** 已完成【终态】 */
    FINISHED(4, true),
    /** 有报错【终态】 */
    ERROR(5, true),
    ;

    private final Integer statusCode;

    private final Boolean isEndStatus;

    RequestTaskStatusEnum(Integer code,Boolean isEndStatus){

        this.statusCode = code;
        this.isEndStatus = isEndStatus;
    }

    public static RequestTaskStatusEnum getByCode(Integer code){
        switch (code){
            case 0:
                return INCOMING;
            case 1:
                return RUNNING;
            case 2:
                return WAITING;
            case 3:
                return REJECTED;
            case 4:
                return FINISHED;
            // case 5:
                // return ERROR;
            default:
                return ERROR;
        }
    }

    public Integer getStatusCodeValue(){
        return this.statusCode;
    }

    public Boolean getIsEndStatus(){
        return isEndStatus;
    }
}

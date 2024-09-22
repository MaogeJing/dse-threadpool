package com.maoge.dosomexperiments.domain.task;

import lombok.Getter;

/**
 * 任务状态流转枚举
 */
public enum RequestTaskFlowEnum {
    /**
     *
     * 这两个状态确实不好区分
     * INCOMING_RUNNING(
     *             "可直接运行", RequestTaskStatusEnum.RUNNING),
     * WAITING_RUNNING(
     *             "等待后运行", RequestTaskStatusEnum.RUNNING),
     */
    INTO_RUNNING(
            "进入运行", RequestTaskStatusEnum.RUNNING),
    INCOMING_WAITING(
            "需要先等待", RequestTaskStatusEnum.WAITING),

    INCOMING_REJECTED(
            "直接拒绝", RequestTaskStatusEnum.REJECTED),
    RUNNING_FINISHED(
            "运行完成", RequestTaskStatusEnum.FINISHED),
    RUNNING_ERROR(
            "运行报错", RequestTaskStatusEnum.ERROR),
    ;


    @Getter
    private String statusFlowDesc;

    private RequestTaskStatusEnum targetStatusCode;


    RequestTaskFlowEnum(String desc, RequestTaskStatusEnum targetStatus){
        this.statusFlowDesc = desc;
        this.targetStatusCode = targetStatus;
    }

    public RequestTaskStatusEnum getTargetStatus(){
        return targetStatusCode;
    }

}

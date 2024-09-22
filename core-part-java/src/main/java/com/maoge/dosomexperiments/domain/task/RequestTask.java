package com.maoge.dosomexperiments.domain.task;

import com.maoge.dosomexperiments.internal.CoreStatusCenter;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class RequestTask {

    @Getter
    private String requestNo;

    @Getter
    private Integer expMsDelay;

    private final RequestTaskRunStrategyTypeEnum taskTypeEnum;

    private final CoreStatusCenter coreStatusCenter;

    /**
     * {@link RequestTaskStatusEnum}
     */
    private AtomicInteger taskStatusCode;

    /** 初建时间戳 */
    private Long startTs;

    /** 终态时间戳 */
    private Long endTs;

    private Long deltaMs;

    public RequestTask(String requestNo,
                       Integer expMsDelay,
                       RequestTaskRunStrategyTypeEnum taskTypeEnum,
                       CoreStatusCenter coreStatusCenter){
        this.requestNo = requestNo;
        this.expMsDelay = expMsDelay;
        this.taskTypeEnum = taskTypeEnum;
        this.coreStatusCenter = coreStatusCenter;
        this.startTs = System.currentTimeMillis();
        this.taskStatusCode = new AtomicInteger(RequestTaskStatusEnum.INCOMING.getStatusCodeValue());
        coreStatusCenter.increment(RequestTaskStatusEnum.INCOMING);
        log.info("generate new request task [{}][{}][{}]", this.requestNo, this.expMsDelay, this.taskTypeEnum );
    }

    /**
     * {@link RequestTaskFlowEnum}
     */
    public void statusFlow(RequestTaskFlowEnum flowEnum){
        RequestTaskStatusEnum currentTaskStatus = RequestTaskStatusEnum.getByCode(this.taskStatusCode.get());
        if (currentTaskStatus.getIsEndStatus()){
            // 如果 task 已经进入终态不再流转
            return;
        }
        RequestTaskStatusEnum nextTaskStatus = flowEnum.getTargetStatus();
        log.info("[{}] StatusFlow:{}->{}", requestNo, currentTaskStatus,nextTaskStatus);
        this.taskStatusCode.set(nextTaskStatus.getStatusCodeValue());
        if (nextTaskStatus.getIsEndStatus()){
            // 如果 task需要进入终态, 记录进入终态的时间
            this.endTs = System.currentTimeMillis();
            this.deltaMs = this.endTs - this.startTs;
        }
        // 状态记录
        coreStatusCenter.decrement(currentTaskStatus);
        coreStatusCenter.increment(nextTaskStatus);
    }

    public RequestTaskRunStrategyTypeEnum getStrategyType(){
        return taskTypeEnum;
    }

}

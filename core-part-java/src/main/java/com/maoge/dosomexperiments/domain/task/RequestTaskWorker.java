package com.maoge.dosomexperiments.domain.task;

import lombok.Data;


public class RequestTaskWorker implements Runnable{

    public RequestTask taskRef;

    public RequestTaskWorker(RequestTask task){
        taskRef = task;
    }

    @Override
    public void run() {
        taskRef.statusFlow(RequestTaskFlowEnum.INTO_RUNNING);
        try{
            RequestTaskRunStrategyTypeEnum strategyType = taskRef.getStrategyType();
            RequestTaskRunAbsStrategy runnableStrategy = RequestTaskRunFactory.getStrategy(strategyType);
            runnableStrategy.runTaskWrapper(taskRef);
        } catch (Exception ignored){
            taskRef.statusFlow(RequestTaskFlowEnum.RUNNING_ERROR);
        }
    }
}

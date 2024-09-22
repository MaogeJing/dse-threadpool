package com.maoge.dosomexperiments.domain.task;

import javax.annotation.PostConstruct;

public abstract class RequestTaskRunAbsStrategy implements RequestTaskRunIStrategy {

    abstract RequestTaskRunStrategyTypeEnum getType();

    @PostConstruct
    public void registerIntoFactory(){
        RequestTaskRunFactory.register(getType(),this);
    }

    public void runTaskWrapper(RequestTask task) {
        runTask(task);
    }
}

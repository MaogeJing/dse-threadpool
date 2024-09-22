package com.maoge.dosomexperiments.domain.task;

import java.util.HashMap;
import java.util.Map;

public class RequestTaskRunFactory {

    private static final Map<RequestTaskRunStrategyTypeEnum, RequestTaskRunAbsStrategy> runHandlerMap = new HashMap<>();

    public static void register(RequestTaskRunStrategyTypeEnum strategyType, RequestTaskRunAbsStrategy runStrategy){
        runHandlerMap.put(strategyType, runStrategy);
    }

    public static RequestTaskRunAbsStrategy getStrategy(RequestTaskRunStrategyTypeEnum strategyType){
        return runHandlerMap.get(strategyType);
    }
}

package com.maoge.dosomexperiments.internal;

import com.maoge.dosomexperiments.domain.CoreStatusSnapshot;
import com.maoge.dosomexperiments.domain.task.RequestTask;
import com.maoge.dosomexperiments.domain.task.RequestTaskRunStrategyTypeEnum;
import com.maoge.dosomexperiments.domain.task.RequestTaskWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class RequestTaskDispatcher {

    @Autowired
    DelayGenerator delayGenerator;

    @Autowired
    CoreStatusCenter coreStatusCenter;

    @Autowired
    DynThreadPoolExecutor dynThreadPoolExecutor;

    private final Map<RequestTaskRunStrategyTypeEnum, AtomicLong>  requestNoGeneratorMap = new ConcurrentHashMap<RequestTaskRunStrategyTypeEnum, AtomicLong>(){{
        put(RequestTaskRunStrategyTypeEnum.OKHTTP_ASYNC, new AtomicLong(0L));
        put(RequestTaskRunStrategyTypeEnum.OKHTTP_SYNC, new AtomicLong(0L));
    }};

    public RequestTaskWorker generateWorker(RequestTaskRunStrategyTypeEnum strategyTypeEnum){
        Integer expMsDelay = delayGenerator.getMsDelay();
        long requestNumber = requestNoGeneratorMap.get(strategyTypeEnum).incrementAndGet();
        String requestNo = strategyTypeEnum.getTaskPrefix() + requestNumber;
        RequestTask task =  new RequestTask(requestNo, expMsDelay, strategyTypeEnum, coreStatusCenter);
        return new RequestTaskWorker(task);
    }

    public Boolean fireAsync() {
        RequestTaskWorker worker = generateWorker(RequestTaskRunStrategyTypeEnum.OKHTTP_ASYNC);
        dynThreadPoolExecutor.submit(worker);
        return true;
    }

    public Boolean fireSync(){
        RequestTaskWorker worker = generateWorker(RequestTaskRunStrategyTypeEnum.OKHTTP_SYNC);
        dynThreadPoolExecutor.submit(worker);
        return true;
    }

    public CoreStatusSnapshot dumpCoreStatusSnapshot(){
        return coreStatusCenter.dumpSnapShot();
    }
}

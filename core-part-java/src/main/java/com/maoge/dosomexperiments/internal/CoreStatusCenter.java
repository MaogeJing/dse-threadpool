package com.maoge.dosomexperiments.internal;

import com.maoge.dosomexperiments.domain.CoreStatusSnapshot;
import com.maoge.dosomexperiments.domain.task.RequestTaskStatusEnum;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import static com.maoge.dosomexperiments.domain.task.RequestTaskStatusEnum.*;

/**
 * 核心状态
 */
@Component
public class CoreStatusCenter {

    private final ConcurrentHashMap<RequestTaskStatusEnum, AtomicLong> statusRecorder = new ConcurrentHashMap<RequestTaskStatusEnum, AtomicLong>(){{
        put(INCOMING, new AtomicLong(0L));
        put(RUNNING, new AtomicLong(0L));
        put(WAITING, new AtomicLong(0L));
        put(REJECTED, new AtomicLong(0L));
        put(FINISHED, new AtomicLong(0L));
        put(ERROR, new AtomicLong(0L));
    }};


    public void increment(RequestTaskStatusEnum statusEnum){
        statusRecorder.get(statusEnum).incrementAndGet();
    }

    public void decrement(RequestTaskStatusEnum statusEnum){
        statusRecorder.get(statusEnum).decrementAndGet();
    }

    public CoreStatusSnapshot dumpSnapShot(){
        Map<String, Long> statusCountMap = new HashMap<>();
        for (RequestTaskStatusEnum key:statusRecorder.keySet()){
            statusCountMap.put(key.name(), statusRecorder.get(key).get());
        }
        return new CoreStatusSnapshot(statusCountMap);
    }

}

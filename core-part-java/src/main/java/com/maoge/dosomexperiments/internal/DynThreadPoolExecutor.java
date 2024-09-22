package com.maoge.dosomexperiments.internal;

import com.maoge.dosomexperiments.domain.DynThreadConfigParams;
import com.maoge.dosomexperiments.domain.task.RequestTaskFlowEnum;
import com.maoge.dosomexperiments.domain.task.RequestTaskWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 动态线程池对象
 */
@Component
public class DynThreadPoolExecutor {

    private static final Logger log = LoggerFactory.getLogger(DynThreadPoolExecutor.class);
    @Value("${dyn.thread-pool.params.core-size}")
    private Integer DefaultCoreSize;

    @Value("${dyn.thread-pool.params.max-size}")
    private Integer DefaultMaxSize;

    @Value("${dyn.thread-pool.params.keep-alive-second}")
    private Integer DefaultKeepAliveSecond;

    private ThreadPoolExecutor threadPoolExecutor;

    @PostConstruct
    public void init(){
        threadPoolExecutor = new ThreadPoolExecutor(
                DefaultCoreSize,
                DefaultMaxSize,
                DefaultKeepAliveSecond,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>()
        );

    }

    public void reset(DynThreadConfigParams updateParams){
    }

    public void submit(RequestTaskWorker worker){
        try{
            threadPoolExecutor.submit(worker);
        } catch (RejectedExecutionException rejectedExecutionException){
            log.error("rejected");
            worker.taskRef.statusFlow(RequestTaskFlowEnum.INCOMING_REJECTED);
        } catch (Exception ignored){
            log.error("other");
            worker.taskRef.statusFlow(RequestTaskFlowEnum.INCOMING_REJECTED);
        }
    }

    public String getDefaultCoreSize() {
        return String.format("%d", this.DefaultCoreSize);
    }
}


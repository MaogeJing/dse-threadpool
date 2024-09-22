package com.maoge.dosomexperiments.controller;

import com.maoge.dosomexperiments.domain.CoreStatusSnapshot;
import com.maoge.dosomexperiments.internal.RequestTaskDispatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Slf4j
@RestController
public class TriggerController {

    @Autowired
    RequestTaskDispatcher requestTaskDispatcher;

    @PostMapping("/trigger/async-http")
    public Boolean httpTriggerAsync(){
        return requestTaskDispatcher.fireAsync();
    }

    @PostMapping("/trigger/sync-http")
    public Boolean httpTriggerSync(){
        return requestTaskDispatcher.fireSync();
    }

    @PostMapping("/flow/status")
    public Flux<ServerSentEvent<CoreStatusSnapshot>> flowCoreCenterStatus() {
        return Flux.interval(Duration.ofSeconds(3))
                .map(sequence->{
                    CoreStatusSnapshot statusSnapshot = requestTaskDispatcher.dumpCoreStatusSnapshot();
                    return ServerSentEvent.<CoreStatusSnapshot>builder()
                            .data(statusSnapshot)
                            .build();
                });
    }
}

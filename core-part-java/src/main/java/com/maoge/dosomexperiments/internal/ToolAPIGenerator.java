package com.maoge.dosomexperiments.internal;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ToolAPIGenerator {

    @Value("${go.end.tool.delay.url}")
    private String goEndUrl;
}

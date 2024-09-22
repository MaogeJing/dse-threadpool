package com.maoge.dosomexperiments.domain;

import lombok.Data;

/**
 * 动态线程池配置参数
 */
@Data
public class DynThreadConfigParams {
    /** 核心线程池数量 */
    private Integer coreSize;
    /** 最大线程池数量 */
    private Integer maxSize;
    /** 非核心线程存活时长(秒) */
    private Integer keepAliveSecond;
}

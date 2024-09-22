package com.maoge.dosomexperiments.internal;

import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * 概率配置
 * a, b, c
 *
 */
@Component
public class DelayGenerator {

    public Integer getMsDelay(){
        Random random = new Random();
        int randomNumber;
        int range = random.nextInt(100); // 生成 0 到 99 之间的随机数
        if (range < 50) {
            // 50% 的概率生成 500 到 1000 之间的随机数
            randomNumber = random.nextInt(501) + 500;
        } else if (range < 80) {
            // 30% 的概率生成 1000 到 2000 之间的随机数
            randomNumber = random.nextInt(1001) + 1000;
        } else {
            // 20% 的概率生成 3000 到 5000 之间的随机数
            randomNumber = random.nextInt(2001) + 3000;
        }
        return randomNumber;
    }
}

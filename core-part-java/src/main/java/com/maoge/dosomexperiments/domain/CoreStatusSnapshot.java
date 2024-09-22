package com.maoge.dosomexperiments.domain;

import com.maoge.dosomexperiments.domain.task.RequestTaskStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@Data
public class CoreStatusSnapshot {

    private Long dumpTs;

    Map<String, Long> statusCountMap;

    public CoreStatusSnapshot(Map<String, Long> statusCountMap){
        this.dumpTs = System.currentTimeMillis();
        this.statusCountMap = statusCountMap;
    }
}

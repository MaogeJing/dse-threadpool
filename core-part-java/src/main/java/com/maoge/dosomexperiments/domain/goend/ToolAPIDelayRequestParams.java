package com.maoge.dosomexperiments.domain.goend;

import com.alibaba.fastjson.JSONObject;
import com.maoge.dosomexperiments.domain.task.RequestTask;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class ToolAPIDelayRequestParams {
    private String requestNo;
    private Integer expMsDelay;

    public static ToolAPIDelayRequestParams fromTask(RequestTask task){
        ToolAPIDelayRequestParams requestParams = new ToolAPIDelayRequestParams();
        requestParams.requestNo = task.getRequestNo();
        requestParams.expMsDelay = task.getExpMsDelay();
        return requestParams;
    }


    public String toJSONString(){
        return JSONObject.toJSONString(this);
    }
}

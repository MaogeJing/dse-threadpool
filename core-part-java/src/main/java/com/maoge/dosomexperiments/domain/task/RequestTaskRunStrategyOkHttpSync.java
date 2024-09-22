package com.maoge.dosomexperiments.domain.task;

import com.alibaba.fastjson.JSON;
import com.maoge.dosomexperiments.domain.goend.ToolAPIDelayRequestParams;
import com.maoge.dosomexperiments.domain.goend.ToolAPIDelayResultResponse;
import com.maoge.dosomexperiments.internal.DynThreadPoolExecutor;
import com.maoge.dosomexperiments.internal.ToolAPIGenerator;
import com.squareup.okhttp.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RequestTaskRunStrategyOkHttpSync extends RequestTaskRunAbsStrategy{

    @Autowired
    ToolAPIGenerator toolAPIGenerator;

    @Override
    RequestTaskRunStrategyTypeEnum getType() {
        return RequestTaskRunStrategyTypeEnum.OKHTTP_SYNC;
    }

    public void runTask(RequestTask task){
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        ToolAPIDelayRequestParams params = ToolAPIDelayRequestParams.fromTask(task);
        RequestBody requestBody = RequestBody.create(mediaType, params.toJSONString());
        Request request = new Request.Builder()
                .url(toolAPIGenerator.getGoEndUrl())
                .method("POST", requestBody)
                .build();
        try{
            Response response = client.newCall(request).execute();
            ToolAPIDelayResultResponse structureResponse = JSON.parseObject(
                    response.body().toString(),
                    ToolAPIDelayResultResponse.class);
            task.statusFlow(RequestTaskFlowEnum.RUNNING_FINISHED);
        } catch (Exception ignored){
            task.statusFlow(RequestTaskFlowEnum.RUNNING_ERROR);
        }
    }

}

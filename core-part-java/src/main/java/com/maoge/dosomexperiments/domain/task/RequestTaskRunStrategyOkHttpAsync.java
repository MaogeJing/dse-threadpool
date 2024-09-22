package com.maoge.dosomexperiments.domain.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.maoge.dosomexperiments.domain.goend.ToolAPIDelayRequestParams;
import com.maoge.dosomexperiments.domain.goend.ToolAPIDelayResultResponse;
import com.maoge.dosomexperiments.internal.DynThreadPoolExecutor;
import com.maoge.dosomexperiments.internal.ToolAPIGenerator;
import com.squareup.okhttp.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.Http2;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class RequestTaskRunStrategyOkHttpAsync extends RequestTaskRunAbsStrategy {
    @Autowired
    ToolAPIGenerator toolAPIGenerator;

    @Override
    RequestTaskRunStrategyTypeEnum getType() {
        return RequestTaskRunStrategyTypeEnum.OKHTTP_ASYNC;
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
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Response response) throws IOException {
                try{
                    ToolAPIDelayResultResponse structureResponse = JSON.parseObject(
                            response.body().toString(),
                            ToolAPIDelayResultResponse.class);
                    task.statusFlow(RequestTaskFlowEnum.RUNNING_FINISHED);
                } catch (Exception ignored) {
                    log.error("RequestTaskFlowEnum.RUNNING_ERROR");
                    task.statusFlow(RequestTaskFlowEnum.RUNNING_ERROR);
                }
            }

            @Override
            public void onFailure(Request request, IOException e) {
                task.statusFlow(RequestTaskFlowEnum.RUNNING_ERROR);
            }
        });
    }
}

package com.nhnacademy.shoppingmall.common.initialize;

import com.nhnacademy.shoppingmall.thread.channel.RequestChannel;
import com.nhnacademy.shoppingmall.thread.request.impl.PointChannelRequest;
import com.nhnacademy.shoppingmall.thread.worker.WorkerThread;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Set;

public class PointThreadInitializer implements ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {

        RequestChannel requestChannel = new RequestChannel(10);
        //todo#14-1 servletContext에 requestChannel을 등록합니다.
        ctx.setAttribute("requestChannel", requestChannel);

        //todo#14-2 WorkerThread 사작합니다.
        // 쓰레드들 둥둥 떠다님.
        // 미리 만들어 야되는거 아녀?
        WorkerThread workerThread = new WorkerThread(requestChannel);
        workerThread.start(); // queue에서 channelRequest를 하나 꺼내서 실행하고, queue에 반납한다.
        // queue가 가득 차거나, 비어있으면 wait 걸린다.
    }
}

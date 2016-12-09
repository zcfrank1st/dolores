package com.chaos.dolores.client;

import com.chaos.dolores.utils.Configuration;
import com.chaos.dolores.transform.MsgPackTransform;
import com.chaos.dolores.transform.Transform;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.io.IOException;
import java.util.Map;

/**
 * Created by zcfrank1st on 09/12/2016.
 */
public class Dolores {

    public static Map<String, String> call(String clazzName, String methodName, String... params) throws IOException {

        Transform transform = MsgPackTransform.INSTANCE;

        ZContext context = new ZContext();
        ZMQ.Socket client = context.createSocket(ZMQ.REQ);

        client.connect("tcp://" + Configuration.conf.getString("dolores.rpc.ip") + ":" + Configuration.conf.getInt("dolores.rpc.port"));
        client.send(transform.wrapRequest(clazzName, methodName, params));

        Map<String, String> res = transform.unwrapResponse(client.recv());

        // TODO 连接池化, 超时重试、丢弃
        client.close();
        context.close();

        return res;
    }
}

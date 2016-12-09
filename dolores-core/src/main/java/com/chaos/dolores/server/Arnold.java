package com.chaos.dolores.server;

import com.chaos.dolores.utils.Configuration;
import com.chaos.dolores.transform.MsgPackTransform;
import com.chaos.dolores.transform.Transform;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zcfrank1st on 09/12/2016.
 */
public class Arnold {

    public static void start() throws IOException {

        ZContext ctx = new ZContext();
        ZMQ.Socket server = ctx.createSocket(ZMQ.REP);
        server.bind("tcp://*:" + Configuration.conf.getString("dolores.rpc.port"));

        System.out.printf ("arnold server is running ...");
        Transform transform = MsgPackTransform.INSTANCE;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                ZMsg msg = ZMsg.recvMsg(server);

                List<String> callList = transform.unwrapRequest(msg.element().getData());

                Class target = Class.forName(callList.get(0));
                List<Class> clazzs = new ArrayList<>();
                List<Object> args = new ArrayList<>();
                for (int i = 0; i <= callList.size() - 3; i++) {
                    clazzs.add(i, String.class);
                    args.add(i, callList.get(i + 2));
                }

                Class[] classArr = new Class[clazzs.size()];
                Method method = target.getMethod(callList.get(1), clazzs.toArray(classArr));

                Object[] objectArr = new Object[args.size()];
                Object result = method.invoke(target.newInstance(), args.toArray(objectArr));

                Map<String, String> resultMap = new HashMap<>();
                resultMap.put("status", "0");
                resultMap.put("result", (String) result);

                ZMsg newMessage = new ZMsg();
                newMessage.add(transform.wrapResponse(resultMap));
                newMessage.send(server);
            } catch (Exception e) {
                Map<String, String> resultMap = new HashMap<>();
                resultMap.put("status", "1");
                resultMap.put("result", "server error");

                ZMsg newMessage = new ZMsg();
                newMessage.add(transform.wrapResponse(resultMap));
                newMessage.send(server);
            }
        }

        server.close();
        ctx.destroy();
    }
}

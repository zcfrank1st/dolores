package com.chaos.dolores.transform;

import org.msgpack.MessagePack;
import org.msgpack.template.Templates;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by zcfrank1st on 09/12/2016.
 */
public enum  MsgPackTransform implements Transform {
    INSTANCE;

    public byte[] wrapRequest(String clazzName, String methodName, String... params) throws IOException {
        MessagePack msgpack = new MessagePack();
        List<String> requestList = new ArrayList<>();
        requestList.add(clazzName);
        requestList.add(methodName);
        Collections.addAll(requestList, params);
        return msgpack.write(requestList);
    }

    public List<String> unwrapRequest(byte[] request) throws IOException {
        MessagePack msgpack = new MessagePack();
        return msgpack.read(request, Templates.tList(Templates.TString));
    }

    public byte[] wrapResponse(Map<String, String> response) throws IOException {
        MessagePack msgpack = new MessagePack();
        return msgpack.write(response);
    }

    public Map<String, String> unwrapResponse(byte[] response) throws IOException {
        MessagePack msgpack = new MessagePack();
        return msgpack.read(response, Templates.tMap(Templates.TString, Templates.TString));
    }
}

package com.chaos.dolores.transform;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by zcfrank1st on 09/12/2016.
 */
public interface Transform {
    byte[] wrapRequest (String clazzName, String methodName, String... params) throws IOException;

    List<String> unwrapRequest (byte[] request) throws IOException;

    byte[] wrapResponse (Map<String, String> response) throws IOException;

    Map<String, String> unwrapResponse (byte[] response) throws IOException;
}

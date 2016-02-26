//package com.interdigital.android.onem2msdk;
//
//import android.content.Context;
//
//import com.google.gson.Gson;
//import com.interdigital.android.onem2msdk.network.Ri;
//import com.interdigital.android.onem2msdk.network.response.ResponseHolder;
//
//import java.io.IOException;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
//
//public class SDK {
//
//    private static final String PREFIX_HTTPS = "https:";
//    private static final String PREFIX_HTTP = "http:";
//    private static SDK instance;
//    private static long requestId = 0L;
//
//    private OkHttpClient okHttpClient = new OkHttpClient();
//
//    private SDK() {
//
//    }
//
//    public static synchronized SDK getInstance() {
//        if (instance == null) {
//            instance = new SDK();
//        }
//        return instance;
//    }
//
//    public synchronized String getUniqueRequestId() {
//        return String.valueOf(requestId++);
//    }
//
//    public ResponseHolder getResource(Context context, Ri ri, boolean useHttps,
//                                      Map<String, List<String>> propertyValues, String userName, String password) {
//        String url = createUrl(ri, useHttps);
//        Iterator<String> iterator = propertyValues.keySet().iterator();
//        Request.Builder builder = new Request.Builder().url(url);
//        while (iterator.hasNext()) {
//            String key = iterator.next();
//            List<String> values = propertyValues.retrieve(key);
//            for (int i = 0; i < values.size(); i++) {
//                builder.addHeader(key, values.create(i));
//            }
//        }
//        Request request = builder.build();
//        try {
//            Response response = okHttpClient.newCall(request).execute();
//            int statusCode = response.code();
//            String text = response.body().string();
//            Gson gson = new Gson();
//            ResponseHolder responseHolder = gson.fromJson(text, ResponseHolder.class);
//            if (responseHolder == null) {
//                return null;
//            }
//            responseHolder.setStatusCode(statusCode);
//            responseHolder.setHeaders(request.headers());
//            return responseHolder;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public ResponseHolder postResource(
//            Context context, Ri ri, boolean useHttps, Map<String, List<String>> propertyValues, String body,
//            String userName, String password) {
//        String url = createUrl(ri, useHttps);
//        BaseRequest baseRequest = new BaseRequest(context, 0, url, BaseRequest.METHOD_POST,
//                propertyValues, body);
//        int statusCode = baseRequest.connect(userName, password);
//        String text = baseRequest.getResponseText();
//        Gson gson = new Gson();
//        ResponseHolder responseHolder = gson.fromJson(text, ResponseHolder.class);
//        if (responseHolder == null) {
//            return null;
//        }
//        responseHolder.setStatusCode(statusCode);
//        responseHolder.setPropertyValues(baseRequest.getHeaderMap());
//        return responseHolder;
//    }
//
//    public ResponseHolder deleteResource(
//            Context context, Ri ri, boolean useHttps, Map<String, List<String>> propertyValues,
//            String userName, String password) {
//        String url = createUrl(ri, useHttps);
//        BaseRequest baseRequest = new BaseRequest(context, 0, url, BaseRequest.METHOD_DELETE,
//                propertyValues, null);
//        int statusCode = baseRequest.connect(userName, password);
//        ResponseHolder responseHolder = new ResponseHolder();
//        responseHolder.setStatusCode(statusCode);
//        responseHolder.setPropertyValues(baseRequest.getHeaderMap());
//        return responseHolder;
//    }
//
//    private String createUrl(Ri ri, boolean useHttps) {
//        if (useHttps) {
//            return PREFIX_HTTPS + ri.getRiString();
//        }
//        return PREFIX_HTTP + ri.getRiString();
//    }
//}

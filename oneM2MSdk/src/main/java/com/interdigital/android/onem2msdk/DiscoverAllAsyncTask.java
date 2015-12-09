//package com.interdigital.android.onem2msdk;
//
//import android.content.Context;
//import android.os.AsyncTask;
//import android.widget.TextView;
//
//import com.interdigital.android.onem2msdk.network.BaseRequest;
//
//public class DiscoverAllAsyncTask extends AsyncTask<Void, Void, Void> {
//
//    private TextView textView;
//    private String applicationEntityId;
//    private int responseCode;
//    private String responseText = "Error";
//    private BaseRequest baseRequest;
//
//    public DiscoverAllAsyncTask(Context context, TextView textView, int pkcs12Resource, String url,
//                                String applicationEntityId) {
//        this.textView = textView;
//        this.applicationEntityId = applicationEntityId;
//        baseRequest = new BaseRequest(context, pkcs12Resource, url, BaseRequest.METHOD_GET);
//    }
//
//    @Override
//    protected Void doInBackground(Void... voids) {
//        baseRequest.addProperty("X-M2M-RI", String.valueOf(SDK.getInstance().getUniqueRequestId()));
//        baseRequest.addProperty("X-M2M-Origin", applicationEntityId);
//        responseCode = baseRequest.connect();
//        responseText = baseRequest.getResponseText();
//        return null;
//    }
//
//    @Override
//    protected void onPostExecute(Void aVoid) {
//        super.onPostExecute(aVoid);
//        textView.setText("Response code: " + responseCode + "\nText:\n" + responseText);
//    }
//}

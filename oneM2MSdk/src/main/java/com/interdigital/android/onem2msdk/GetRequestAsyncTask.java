package com.interdigital.android.onem2msdk;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class GetRequestAsyncTask extends AsyncTask<Void, Void, Void> {

    private Context context;
    private TextView textView;
    private int pkcs12Resource;
    private String responseText = "Error";

    public GetRequestAsyncTask(Context context, TextView textView, int pkcs12Resource) {
        this.context = context;
        this.textView = textView;
        this.pkcs12Resource = pkcs12Resource;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        String cseBaseUrl = "https://onet-cse-01.cloudapp.net";
        String cseId = "ONET-CSE-01";
        String cseName = "ONETCSE01";
        String aeId = "C-ANDROID";
        String aeName = "ANDROID";
        String url = cseBaseUrl + "/" + cseName + "/" + aeName;
        HttpsURLConnection urlConnection = null;
        int lastResponseCode;
        String lastContentType;
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            InputStream is = context.getResources().openRawResource(pkcs12Resource);
            keyStore.load(is, "".toCharArray());
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("X509");
            kmf.init(keyStore, "".toCharArray());
            KeyManager[] keyManagers = kmf.getKeyManagers();
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagers, new TrustManager[]{
                    new X509TrustManager() {
                        public void checkClientTrusted(X509Certificate[] chain, String authType) {
                        }

                        public void checkServerTrusted(X509Certificate[] chain, String authType) {
                        }

                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[]{};
                        }
                    }
            }, null);

            String result = null;
            URL requestedUrl = new URL(url);
            urlConnection = (HttpsURLConnection) requestedUrl.openConnection();
            if (urlConnection instanceof HttpsURLConnection) {
                urlConnection.setSSLSocketFactory(sslContext.getSocketFactory());
            }
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(1500);
            urlConnection.setReadTimeout(1500);
            urlConnection.setRequestProperty("X-M2M-RI", "xyz");
            urlConnection.setRequestProperty("X-M2M-Origin", aeId);
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            lastResponseCode = urlConnection.getResponseCode();
            byte[] buffer = new byte[8192];
            is = urlConnection.getInputStream();
            int count;
            while ((count = is.read(buffer, 0, 8192)) >= 0) {
                baos.write(buffer, 0, count);
            }
            responseText = new String(baos.toByteArray());
            lastContentType = urlConnection.getContentType();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        textView.setText(responseText);
    }
}

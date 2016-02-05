package com.interdigital.android.onem2msdk.network.request;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.ProtocolException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class BaseRequest {

    public static final String METHOD_GET = "GET";
    public static final String METHOD_PUT = "PUT";
    public static final String METHOD_POST = "POST";
    public static final String METHOD_DELETE = "DELETE";

    private Context context;
    private int pkcs12Resource;
    private String url;
    private String method;
    private Map<String, List<String>> propertyValues;
    private String body;
    private String contentType = null;
    private String responseText = null;
    private Map<String, List<String>> headerMap;

    public BaseRequest(@NonNull Context context, int pkcs12Resource, @NonNull String url,
                       @NonNull String method, Map<String, List<String>> propertyValues, String body) {
        this.context = context;
        this.pkcs12Resource = pkcs12Resource;
        this.url = url;
        this.method = method;
        this.propertyValues = propertyValues;
        this.body = body;
    }

    public int connect(String userName, String password) {
        HttpsURLConnection httpsURLConnection = null;
        HttpURLConnection httpURLConnection = null;
        int responseCode = 0;
        try {
//            KeyManager[] keyManagers = getKeyManagers();
            URL requestedUrl = new URL(url);
            if (url.startsWith("https")) {
                SSLContext sslContext = getSslContext(null);
                httpsURLConnection = getHttpsURLConnection(sslContext, requestedUrl, userName, password);
                responseCode = httpsURLConnection.getResponseCode();
                responseText = extractResponseText(httpsURLConnection);
                headerMap = httpsURLConnection.getHeaderFields();
                contentType = httpsURLConnection.getContentType();
            } else {
                httpURLConnection = getHttpUrlConnection(requestedUrl, userName, password);
                responseCode = httpURLConnection.getResponseCode();
                responseText = extractResponseText(httpURLConnection);
                headerMap = httpURLConnection.getHeaderFields();
                contentType = httpURLConnection.getContentType();
            }
//        } catch (KeyStoreException e) {
//            e.printStackTrace();
//        } catch (CertificateException e) {
//            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
//        } catch (UnrecoverableKeyException e) {
//            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (httpsURLConnection != null) {
                httpsURLConnection.disconnect();
            }
        }
        return responseCode;
    }

    public String getContentType() {
        return contentType;
    }

    public String getResponseText() {
        return responseText;
    }

    public Map<String, List<String>> getHeaderMap() {
        return headerMap;
    }

    private KeyManager[] getKeyManagers() throws KeyStoreException, IOException,
            NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        InputStream is = context.getResources().openRawResource(pkcs12Resource);
        keyStore.load(is, "".toCharArray());
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("X509");
        kmf.init(keyStore, "".toCharArray());
        return kmf.getKeyManagers();
    }

    @NonNull
    private SSLContext getSslContext(KeyManager[] keyManagers) throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslContext = SSLContext.getInstance("TLS");
//        sslContext.init(keyManagers, new TrustManager[]{
        sslContext.init(null, new TrustManager[]{
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
        return sslContext;
    }

    private HttpURLConnection getHttpUrlConnection(URL requestedUrl,
                                                   String userName, String password) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) requestedUrl.openConnection();
        if (httpURLConnection != null) {
            setHttpParameters(httpURLConnection, userName, password);
            addBody(httpURLConnection);
        }
        return httpURLConnection;
    }

    private void setHttpParameters(HttpURLConnection httpURLConnection,
                                   final String userName, final String password) throws ProtocolException {
        httpURLConnection.setRequestMethod(method);
        httpURLConnection.setConnectTimeout(5000);
        httpURLConnection.setReadTimeout(5000);
        Authenticator.setDefault(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password.toCharArray());
            }
        });
        Iterator<String> keyIterator = propertyValues.keySet().iterator();
        while (keyIterator.hasNext()) {
            String key = keyIterator.next();
            List<String> values = propertyValues.get(key);
            for (int i = 0; i < values.size(); i++) {
                httpURLConnection.setRequestProperty(key, values.get(i));
            }
        }
        httpURLConnection.setRequestProperty("Accept", "application/vnd.onem2m-res+json");
    }

    private HttpsURLConnection getHttpsURLConnection(SSLContext sslContext, URL requestedUrl,
                                                     String userName, String password) throws IOException {
        HttpsURLConnection urlConnection = (HttpsURLConnection) requestedUrl.openConnection();
        if (urlConnection != null) {
            urlConnection.setSSLSocketFactory(sslContext.getSocketFactory());
            setHttpParameters(urlConnection, userName, password);
            urlConnection.setHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            addBody(urlConnection);
        }
        return urlConnection;
    }

    private String extractResponseText(HttpURLConnection urlConnection) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[8192];
        InputStream is;
        is = urlConnection.getInputStream();
        int count;
        while ((count = is.read(buffer, 0, 8192)) >= 0) {
            baos.write(buffer, 0, count);
        }
        return new String(baos.toByteArray());
    }

    private void addBody(HttpURLConnection urlConnection) {
        if (body != null) {
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            try {
                OutputStream os = urlConnection.getOutputStream();
                os.write(body.getBytes("UTF-8"));
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

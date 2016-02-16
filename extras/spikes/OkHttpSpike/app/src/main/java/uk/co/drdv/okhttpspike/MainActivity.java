package uk.co.drdv.okhttpspike;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OkHttpClient client = new OkHttpClient();
        String url = "https://cse-01.onetransport.uk.net/ONETCSE01/BUCKS-OXON-TRANSPORT";
        Request request = new Request.Builder()
                .url(url)
                .addHeader("X-M2M-RI", "xyz")
                .addHeader("X-M2M-Origin", "C-BUCKS-OXON-TRANSPORT")
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", Credentials.basic("pthomas", "EKFYGUCC"))
                .build();
        client.newCall(request).enqueue(this);
    }

    @Override
    public void onFailure(Call call, IOException e) {
        e.printStackTrace();
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        Log.i("MainActivity", response.body().string());
    }
}

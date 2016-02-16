package com.interdigital.gsonspike;

import android.os.AsyncTask;
import android.widget.TextView;

import com.google.gson.Gson;
import com.interdigital.gsonspike.serial.C;

public class SerialAsyncTask extends AsyncTask<Void, Void, Void> {

    private TextView textView;
    private String text;

    public SerialAsyncTask(TextView textView) {
        this.textView = textView;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Gson gson = new Gson();
        C c = new C();
        text = gson.toJson(c);
        C newC = gson.fromJson(text, C.class);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        textView.setText(text);
    }
}

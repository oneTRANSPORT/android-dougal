package uk.co.drdv.onem2msqlspike;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.interdigital.android.dougal.resource.ContentInstance;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

public class GetContentTask extends AsyncTask<Void, Void, Void> {

    private static final String CSE_NAME = "ONETCSE01";
    private static final String AE_ID = "C-BUCKS-OXON-TRANSPORT";
    private static final String AE_NAME = "BUCKS-OXON-TRANSPORT";
    private static final String DC_NAME = "carparkSqlData";

    private Context context;

    public GetContentTask(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        String hostName = "onet-cse-01.cloudapp.net";
        int port = 443;
        boolean useHttps = true;
        ContentInstance contentInstance = ContentInstance.getLast(context,
                hostName, port, useHttps, CSE_NAME, AE_NAME, DC_NAME, AE_ID,
                "pthomas", "EKFYGUCC");
        String base64 = contentInstance.getContent();
        byte[] zipBytes = Base64.decode(base64, 0);
        ByteArrayInputStream bais = new ByteArrayInputStream(zipBytes);
        try {
            GZIPInputStream gzis = new GZIPInputStream(bais);
            InputStreamReader isr = new InputStreamReader(gzis);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                Log.i("MainActivity", "SQL = " + line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bais.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

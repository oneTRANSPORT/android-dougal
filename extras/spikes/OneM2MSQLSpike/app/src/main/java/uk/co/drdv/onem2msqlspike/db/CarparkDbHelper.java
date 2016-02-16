package uk.co.drdv.onem2msqlspike.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Base64;

import com.interdigital.android.onem2msdk.resource.ContentInstance;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

public class CarparkDbHelper extends SQLiteOpenHelper {

    private static final String CSE_NAME = "ONETCSE01";
    private static final String AE_ID = "C-BUCKS-OXON-TRANSPORT";
    private static final String AE_NAME = "BUCKS-OXON-TRANSPORT";
    private static final String DC_NAME = "carparkSqlData";
    private static final String HOST_NAME = "onet-cse-01.cloudapp.net";
    private static final int PORT = 443;
    private static final boolean USE_HTTPS = true;
    private static final String USER_NAME = "pthomas";
    private static final String PASSWORD = "EKFYGUCC";

    private Context context;

    // No name == in memory database.
    public CarparkDbHelper(Context context) {
        super(context, null, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        ContentInstance contentInstance = ContentInstance.getLast(context,
                HOST_NAME, PORT, USE_HTTPS, CSE_NAME, AE_NAME, DC_NAME, AE_ID,
                USER_NAME, PASSWORD);
        String base64 = contentInstance.getContent();
        byte[] zipBytes = Base64.decode(base64, 0);
        ByteArrayInputStream bais = new ByteArrayInputStream(zipBytes);
        try {
            GZIPInputStream gzis = new GZIPInputStream(bais);
            InputStreamReader isr = new InputStreamReader(gzis);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                sqLiteDatabase.execSQL(line);
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
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Never going to be upgraded.
    }
}

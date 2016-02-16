package uk.co.drdv.onem2msqlspike.db;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashMap;

import static uk.co.drdv.onem2msqlspike.db.DbContract.CARPARK_DB_NAME;
import static uk.co.drdv.onem2msqlspike.db.DbContract.CountyCarpark;

public class CarparkContentProvider extends ContentProvider {

    public static final String AUTHORITY = "uk.co.drdv.onem2msqlspike.carparkprovider";
    public static final String CONTENT_PREFIX = "content://" + AUTHORITY + "/";
    public static final Uri COUNTY_CARPARK_URI = Uri.parse(CONTENT_PREFIX
            + CARPARK_DB_NAME + "/" + CountyCarpark.TABLE_NAME);

    private static final String MIME_ITEM_PREFIX = "vnd.android.cursor.item/vnd." + AUTHORITY + ".";
    private static final String MIME_DIR_PREFIX = "vnd.android.cursor.dir/vnd." + AUTHORITY + ".";

    private static final String COUNTY_CARPARK_MIME_TYPE = MIME_DIR_PREFIX
            + CountyCarpark.TABLE_NAME;
    private static final String COUNTY_CARPARK_ID_MIME_TYPE = MIME_ITEM_PREFIX
            + CountyCarpark.TABLE_NAME;

    // UriMatcher codes.
    private static final int COUNTY_CARPARKS = 2;
    private static final int COUNTY_CARPARK_ID = 3;

    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    // This content provider can manage multiple in-memory databases.
    private HashMap<String, CarparkDbHelper> helpers = new HashMap<>();

    public CarparkContentProvider() {
        uriMatcher.addURI(AUTHORITY, CARPARK_DB_NAME + "/"
                + CountyCarpark.TABLE_NAME, COUNTY_CARPARKS);
        uriMatcher.addURI(AUTHORITY, CARPARK_DB_NAME + "/"
                + CountyCarpark.TABLE_NAME + "/#", COUNTY_CARPARK_ID);
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        //noinspection ConstantConditions getContext only null in constructor.
        ContentResolver contentResolver = getContext().getContentResolver();
        if (uri.toString().contains(CARPARK_DB_NAME)) {
            if (!helpers.containsKey(CARPARK_DB_NAME)) {
                helpers.put(CARPARK_DB_NAME, new CarparkDbHelper(getContext()));
            }
            SQLiteDatabase db;
            db = helpers.get(CARPARK_DB_NAME).getReadableDatabase();
            switch (uriMatcher.match(uri)) {
                case COUNTY_CARPARKS:
                    Cursor cursor = db.query(CountyCarpark.TABLE_NAME, new String[]{
                                    CountyCarpark._ID,
                                    CountyCarpark.COLUMN_COUNTY,
                                    CountyCarpark.COLUMN_NAME,
                                    CountyCarpark.COLUMN_ALMOST_FULL_DECREASING,
                                    CountyCarpark.COLUMN_ALMOST_FULL_INCREASING,
                                    CountyCarpark.COLUMN_ENTRANCE_FULL,
                                    CountyCarpark.COLUMN_FULL_DECREASING,
                                    CountyCarpark.COLUMN_FULL_INCREASING,
                                    CountyCarpark.COLUMN_LATITUDE,
                                    CountyCarpark.COLUMN_LONGITUDE,
                                    CountyCarpark.COLUMN_TOTAL_PARKING_CAPACITY},
                            null, null, null, null, CountyCarpark.COLUMN_NAME);
                    cursor.setNotificationUri(contentResolver, COUNTY_CARPARK_URI);
                    return cursor;
            }
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case COUNTY_CARPARKS:
                return COUNTY_CARPARK_MIME_TYPE;
            case COUNTY_CARPARK_ID:
                return COUNTY_CARPARK_ID_MIME_TYPE;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        // Keeping things read-only for the moment.
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        // Read-only.
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        // Read-only.
        throw new UnsupportedOperationException("Not implemented");
    }
}

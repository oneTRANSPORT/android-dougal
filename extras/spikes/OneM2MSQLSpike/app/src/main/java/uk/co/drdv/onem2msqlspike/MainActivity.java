package uk.co.drdv.onem2msqlspike;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import uk.co.drdv.onem2msqlspike.db.CarparkContentProvider;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOADER_CARPARKS = 1;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1, new String[]{"Waiting for data..."});
        listView.setAdapter(adapter);
        getSupportLoaderManager().initLoader(LOADER_CARPARKS, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_CARPARKS:
                return new CursorLoader(this, CarparkContentProvider.COUNTY_CARPARK_URI,
                        null, null, null, null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        switch (loader.getId()) {
            case LOADER_CARPARKS:
                CarparkAdapter adapter = new CarparkAdapter(getApplicationContext(), cursor, 0);
                listView.setAdapter(adapter);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            case LOADER_CARPARKS:
                // Nothing needed.
                break;
        }
    }
}

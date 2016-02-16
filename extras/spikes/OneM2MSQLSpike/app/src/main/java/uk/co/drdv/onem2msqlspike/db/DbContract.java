package uk.co.drdv.onem2msqlspike.db;

import android.provider.BaseColumns;

public class DbContract {

    public static final String CARPARK_DB_NAME = "carpark";

    private DbContract() {
    }

    public static final class County implements BaseColumns {
        public static final String TABLE_NAME = "county";
        public static final String COLUMN_NAME = "name";
    }

    public static final class Carpark implements BaseColumns {
        public static final String TABLE_NAME = "carpark";
        public static final String COLUMN_COUNTY_ID = "county_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_ALMOST_FULL_DECREASING = "almost_full_decreasing";
        public static final String COLUMN_ALMOST_FULL_INCREASING = "almost_full_increasing";
        public static final String COLUMN_ENTRANCE_FULL = "entrance_full";
        public static final String COLUMN_FULL_DECREASING = "full_decreasing";
        public static final String COLUMN_FULL_INCREASING = "full_increasing";
        public static final String COLUMN_LATITUDE = "latitude";
        public static final String COLUMN_LONGITUDE = "longitude";
        public static final String COLUMN_TOTAL_PARKING_CAPACITY = "total_parking_capacity";
    }

    public static final class CountyCarpark implements BaseColumns {
        public static final String TABLE_NAME = "county_carpark";
        public static final String COLUMN_COUNTY = "county";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_ALMOST_FULL_DECREASING = "almost_full_decreasing";
        public static final String COLUMN_ALMOST_FULL_INCREASING = "almost_full_increasing";
        public static final String COLUMN_ENTRANCE_FULL = "entrance_full";
        public static final String COLUMN_FULL_DECREASING = "full_decreasing";
        public static final String COLUMN_FULL_INCREASING = "full_increasing";
        public static final String COLUMN_LATITUDE = "latitude";
        public static final String COLUMN_LONGITUDE = "longitude";
        public static final String COLUMN_TOTAL_PARKING_CAPACITY = "total_parking_capacity";
    }
}

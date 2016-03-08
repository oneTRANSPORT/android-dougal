package com.interdigital.android.dougal.shared;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Timestamp {

    // Millis to YYYYMMDDThhmmss.
    public static String millisToTimestamp(long millis) {
        Calendar calender = Calendar.getInstance();
        calender.setTimeInMillis(millis);
        StringBuffer buf = new StringBuffer();
        buf.append(calender.get(Calendar.YEAR));
        buf.append(calender.get(Calendar.MONTH));
        buf.append(calender.get(Calendar.DAY_OF_MONTH));
        buf.append("T");
        buf.append(calender.get(Calendar.HOUR_OF_DAY));
        buf.append(calender.get(Calendar.MINUTE));
        buf.append(calender.get(Calendar.SECOND));
        return buf.toString();
    }

    public static long timestampToMillis(String timestamp) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddTHHmmss");
        Date date = simpleDateFormat.parse(timestamp);
        return date.getTime();
    }
}

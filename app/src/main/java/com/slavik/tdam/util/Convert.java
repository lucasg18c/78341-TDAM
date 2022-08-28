package com.slavik.tdam.util;

import java.util.Calendar;

public class Convert {
    public static Calendar unixToCalendar(long unixTime){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(unixTime * 1000);
        return calendar;
    }
}

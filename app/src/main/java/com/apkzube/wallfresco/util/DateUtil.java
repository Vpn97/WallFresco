package com.apkzube.wallfresco.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Wall Fresco
 * Created by Vishal Nagvadiya on 03-05-2020.
 */
public class DateUtil {

    public static boolean isSameDateRequest(DataStorage dataStorage) {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("ddMMMyyyy");
        String today = df.format(c);

        if (today.equals(dataStorage.read("wallpaper_today", DataStorage.STRING))) {
            //do your work if its same day
            return true;
        } else {
            //do your work if its NOT same day
            dataStorage.write("wallpaper_today", today);
            return false;
        }
    }

}

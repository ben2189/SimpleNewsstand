package vn.me.simplenewsstand.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by taq on 23/10/2016.
 */

public class DisplayUtil {

    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static String getFormattedDate(long time) {
        if (time <= 0) {
            return "Today";
        }

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        int year = c.get(Calendar.YEAR);
        int monthOfYear = c.get(Calendar.MONTH);
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);

        c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_YEAR, -1);

        if (c.get(Calendar.DAY_OF_MONTH) == dayOfMonth) {
            return "Yesterday";
        } else {
            String pattern = c.get(Calendar.YEAR) == year ? "MMMM d" : "MMMM d, yyyy";
            SimpleDateFormat f = new SimpleDateFormat(pattern);
            c.set(year, monthOfYear, dayOfMonth);
            return f.format(c.getTime());
        }
    }

    public static String getFormattedDateForApi(long time) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        return new SimpleDateFormat("yyyyMMdd").format(c.getTime());
    }
}

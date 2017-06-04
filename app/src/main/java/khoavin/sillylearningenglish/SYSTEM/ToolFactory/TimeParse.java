package khoavin.sillylearningenglish.SYSTEM.ToolFactory;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeParse {
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;


    public static String getTimeAgo(final String time) throws ParseException {
        String result = "";
        int year = 0;
        int month = 0;
        int day = 0;
        int hour = 0;
        int minute = 0;
        int second = 0;
        String[] times = time.split(":");
        int t_hour = Integer.parseInt(times[0]);
        int t_minute = Integer.parseInt(times[1]);
        int t_second = Integer.parseInt(times[2]);
        if (t_hour > 24) {
            day = t_hour / 24;
            hour = t_hour % 24;
        }

        if (day > 30) {
            month = day / 30;
        }
        if (month > 12) {
            year = month / 12;
        }
        if (year > 0) {
            if (year == 1) {
                return "last year";
            }
            result = result + year + " year";
            return result + " ago";
        }
        if (month > 0) {
            if (month == 1) {
                return "last month";
            }
            result = result + month + " month";
            return result + " ago";
        }
        if (day > 0) {
            if (hour == 1) {
                return "yesterday";
            }
            result = result + day + " day";
            return result + " ago";
        }
        if (hour > 0) {
            if (hour == 1) {
                return "an hour ago";
            }
            result = result + hour + " hour";
            return result + " ago";
        }
        if (t_minute > 0) {
            if (t_minute == 1) {
                return "a minute ago";
            }
            result = result + t_minute + " minute";
            return result + " ago";
        }
        if (t_second > 0) {

            result = result + t_second + " second";
            return result + " ago";
        }
        return "just now";
    }

    public static Date currentDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    public static Date format(String strDate, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        Date startDate = new Date();
        try {
            startDate = df.parse(strDate);
            //String newDateString = df.format(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return startDate;
    }

    public static String getTimeAgo(long time) {
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }
        long now = currentDate().getTime();
        if (time > now || time <= 0) {
            return null;
        }
        final long diff = now - time;
        long daysAgo = TimeUnit.MILLISECONDS.toDays(diff);
        if (daysAgo > 365) {
            return (daysAgo / 365) + " years ago";
        } else if (daysAgo > 30) {
            return (daysAgo / 30) + " months ago";
        } else {
            if (diff < MINUTE_MILLIS) {
                return "just now";
            } else if (diff < 2 * MINUTE_MILLIS) {
                return "a minute ago";
            } else if (diff < 50 * MINUTE_MILLIS) {
                return diff / MINUTE_MILLIS + " minutes ago";
            } else if (diff < 90 * MINUTE_MILLIS) {
                return "an hour ago";
            } else if (diff < 24 * HOUR_MILLIS) {
                return diff / HOUR_MILLIS + " hours ago";
            } else if (diff < 48 * HOUR_MILLIS) {
                return "yesterday";
            } else {
                return diff / DAY_MILLIS + " days ago";
            }
        }
    }

    public static long convertStringToMiliSeconds(String time){
        try {
            String timeSplit[] = time.split(":");
            int seconds = Integer.parseInt(timeSplit[0]) * 60 * 60 +  Integer.parseInt(timeSplit[1]) * 60 + Integer.parseInt(timeSplit[2]);
            System.out.println(seconds);
            return TimeUnit.SECONDS.toMillis(seconds);
        }catch (Exception ex){
            Log.e("converMiliSeconds", ex.getMessage());
            return 0;
        }

    }
}

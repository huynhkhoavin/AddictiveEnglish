package khoavin.sillylearningenglish.SYSTEM.ToolFactory;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SingleViewObject.Common;

/**
 * Created by OatOal on 5/23/2017.
 */

public class SillyDateFormat {

    private final SimpleDateFormat reading = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
    private final SimpleDateFormat writingFormat = new SimpleDateFormat("yyy/MM/dd HH:mm a");

    /**
     * Find total date from now.
     */
    public String FindTotalDateFromNow(Date fromDate, Context context) {
        Date currentDate = new Date();
        long diff = Math.abs(currentDate.getTime() - fromDate.getTime()) / 1000;
        long diffDays = 0;

        diffDays = diff / 365 / 24 / 3600;
        if (diffDays > 0) {
            return String.format(context.getResources().getString(R.string.mail_years_ago), String.valueOf(diffDays));
        }

        diffDays = diff / 30 / 24 / 3600;
        if (diffDays > 0) {
            return String.format(context.getResources().getString(R.string.mail_months_ago), String.valueOf(diffDays));
        }

        diffDays = diff / 7 / 24 / 3600;
        if (diffDays > 0) {
            return String.format(context.getResources().getString(R.string.mail_weeks_ago), String.valueOf(diffDays));
        }

        diffDays = diff / 24 / 3600;
        if (diffDays > 0) {
            return String.format(context.getResources().getString(R.string.mail_days_ago), String.valueOf(diffDays));
        }

        diffDays = diff / 3600;
        if (diffDays > 0) {
            return String.format(context.getResources().getString(R.string.mail_hours_ago), String.valueOf(diffDays));
        }

        diffDays = diff / 60;
        if (diffDays > 0) {
            return String.format(context.getResources().getString(R.string.mail_mins_ago), String.valueOf(diffDays));
        }

        return context.getResources().getString(R.string.mail_less_than_a_min);
    }

    /**
     * Find total date from now.
     *
     * @param fromDate
     * @return
     */
    public String FindTotalDateFromNow(String fromDate, Context context) {
        try {
            Date date = reading.parse(fromDate);
            return FindTotalDateFromNow(date, context);
        } catch (ParseException e) {
            e.printStackTrace();
            return context.getResources().getString(R.string.mail_unknown_times);
        }
    }

    /**
     * Format date time string to our format.
     * Example: 12-4-2017 03:12:05 -> 12/4/2017 03:12 AM
     *
     * @param dateInput The input string as format: "yyy-MM-dd HH:mm:ss"
     * @return The out put string as format: "yyy/MM/dd HH:mm a"
     */
    public String FormatDateTimeString(String dateInput) {
        try {
            Date date = reading.parse(dateInput);
            return writingFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return writingFormat.format(new Date());
        }
    }

    /**
     * Format date time string to our format: 12/4/2017 03:12 AM
     *
     * @param dateInput The date.
     * @return The out put string as format: "yyy/MM/dd HH:mm a"
     */
    public String FormatDateTimeString(Date dateInput) {
        return writingFormat.format(dateInput);
    }

    /**
     * Convert millisecond to string : HH:mm:ss
     */
    public String MillisecondToString(long milliSecond) {
        long seconds, mins, hours;
        seconds = milliSecond / 1000;
        if (seconds < 60) {
            return "0:" + seconds;
        } else if (seconds > 60 && seconds < 3600) {
            mins = seconds / 60;
            seconds = seconds % 60;
            return mins + ":" + seconds;
        } else {
            hours = seconds / 3600;
            seconds = seconds % 3600;
            mins = seconds / 60;
            seconds = seconds % 60;
            return hours + ":" + mins + ":" + seconds;
        }

    }
}

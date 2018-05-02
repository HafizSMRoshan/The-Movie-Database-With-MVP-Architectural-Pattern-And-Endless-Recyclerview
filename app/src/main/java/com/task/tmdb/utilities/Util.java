package com.task.tmdb.utilities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.DatePicker;

import com.task.tmdb.main.TMDBApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by HSM Roshan on 02/05/2018.
 */

public class Util {

    public static final String BUNDLES = "BUNDLES";
    public static final String MOVIE_DETAIL = "MOVIE_DETAIL";
    public static final String MOVIES = "MOVIES";

    public static boolean isInternetOn() {
        ConnectivityManager
                cm = (ConnectivityManager) TMDBApplication.getInstance().getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
    }

    public interface SelectDateListener {
        void onSelectDate(String date);
    }

    public static void getSelectedDate(Context context, final SelectDateListener listener) {

        final String[] date = {""};
        final Calendar mCalendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, monthOfYear);
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                date[0] = sdf.format(mCalendar.getTime());

                listener.onSelectDate(date[0]);
            }
        }, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }

    public static boolean compareDate(String startDate, String endDate) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;
        try {
            date1 = sdf.parse(startDate);

            Date date2 = sdf.parse(endDate);

//            System.out.println("date1 : " + sdf.format(date1));
//            System.out.println("date2 : " + sdf.format(date2));

            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(date1);
            cal2.setTime(date2);

            if (cal1.after(cal2)) {
                System.out.println("Date1 is after Date2");
            }

            if (cal1.before(cal2)) {
                System.out.println("Date1 is before Date2");
                return true;
            }

            if (cal1.equals(cal2)) {
                System.out.println("Date1 is equal Date2");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;

    }

    public static boolean isDateWithinRange(String testDate, String startDate, String endDate) {

        try {
            return !(convertStringToDate(testDate).before(convertStringToDate(startDate))
                    || convertStringToDate(testDate).after(convertStringToDate(endDate)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Date convertStringToDate(String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;

        date = sdf.parse(dateString);

        return date;
    }

}



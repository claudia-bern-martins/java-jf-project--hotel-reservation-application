package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateFormatter {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
    private static final Calendar calendar = Calendar.getInstance();
    private static final TimeZone timezone = TimeZone.getTimeZone("Europe/Lisbon");
    private DateFormatter(){}

    public static String formatDate(Date date) {
        formatter.setTimeZone(timezone);
        return formatter.format(date);
    }

    public static Date getDate(String dateString) throws IllegalArgumentException {
        calendar.setTimeZone(timezone);
        try {
            Date date = formatter.parse(dateString);
            calendar.setTime(date);
            return calendar.getTime();
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please input dates in the yyyy/MM/dd format. "
                    + e.getLocalizedMessage());
        }
    }


}

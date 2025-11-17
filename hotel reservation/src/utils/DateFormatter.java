package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Utility class for formatting and parsing dates in the hotel reservation
 * application.
 * Provides methods to format Date objects into strings and parse strings into
 * Date objects
 * using the "yyyy/MM/dd" format.
 * 
 * @author Cláudia Martins
 */
public class DateFormatter {

    /**
     * The date formatter using the "yyyy/MM/dd" pattern
     */
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
    /**
     * The calendar instance for date manipulations
     */
    private static final Calendar calendar = Calendar.getInstance();
    /**
     * The time zone set to Europe/Lisbon
     */
    private static final TimeZone timezone = TimeZone.getTimeZone("Europe/Lisbon");

    /**
     * Private constructor to prevent instantiation
     */
    private DateFormatter() {
    }

    /**
     * Formats a Date object into a string using the "yyyy/MM/dd" format
     * 
     * @param date: the Date object to be formatted
     * @return the formatted date string
     */
    public static String formatDate(Date date) {
        formatter.setTimeZone(timezone);
        return formatter.format(date);
    }

    /**
     * Parses a date string in the "yyyy/MM/dd" format into a Date object
     * 
     * @param dateString: the date string to be parsed
     * @return the parsed Date object
     * @throws IllegalArgumentException if the date string is not in the correct
     *                                  format
     */
    public static Date getDate(String dateString) throws IllegalArgumentException {
        calendar.setTimeZone(timezone);
        try {
            String[] dateParts = dateString.split("/");
            if (dateParts.length < 3) {
                throw new IllegalArgumentException("Separator must be \"/\".");
            }
            int year = Integer.parseInt(dateParts[0]);
            int month = Integer.parseInt(dateParts[1]);
            int day = Integer.parseInt(dateParts[2]);

            if (year < 1 || month < 1 || day < 1) {
                throw new IllegalArgumentException("Year, month and day must " +
                        "be positive numbers");
            }

            if (month > 12) {
                throw new IllegalArgumentException("Month must be comprised " +
                        "between 01 (January) and 12 (December).");
            }

            checkMonthLength(month, day, year);

            Date date = formatter.parse(dateString);
            calendar.setTime(date);
            return calendar.getTime();
        } catch (ParseException | IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid date format. Please input dates in the yyyy/MM/dd format. "
                    + e.getLocalizedMessage());
        }
    }

    /**
     * Checks if the given day is valid for the specified month and year
     * 
     * @param month: the month (1-12)
     * @param day:   the day of the month
     * @param year:  the year
     * @throws IllegalArgumentException if the day is not valid for the given month
     *                                  and year
     */
    private static void checkMonthLength(int month, int day, int year) {
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12: {
                if (day > 31) {
                    throw new IllegalArgumentException("The given month " +
                            "only has 31 days.");
                }
                break;
            }
            case 4:
            case 6:
            case 9:
            case 11: {
                if (day > 30) {
                    throw new IllegalArgumentException("The given month " +
                            "only has 30 days.");
                }
                break;
            }
            case 2: {
                int februaryDays = isLeapYear(year) ? 29 : 28;
                if (day > februaryDays) {
                    throw new IllegalArgumentException("The given month " +
                            "only has " + februaryDays + " days.");
                }
                break;
            }
        }
    }

    /**
     * Checks if the given year is a leap year
     * 
     * @param year: the year to be checked
     * @return true if the year is a leap year, false otherwise
     */
    private static boolean isLeapYear(int year) {
        if (year % 4 == 0) {
            if (year % 100 == 0) {
                return year % 400 == 0;
            }
            return true;
        }
        return false;
    }
}

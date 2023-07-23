package uz.cluster.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static String convertToDateString(LocalDate date) {
        if (date == null)
            return null;

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(dateFormat);
    }

    public static String convertToDateTimeString(LocalDateTime date) {
        if (date == null)
            return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return date.format(formatter);
    }

    public static LocalDate convertToStringToDateTime(String date) {
        if (date == null){
            return null;
        }
        LocalDate formatter = LocalDate.parse(date,DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        return formatter;
    }



}

package challenger.purple.Util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.time.temporal.ChronoField.EPOCH_DAY;

public class Util {

    public static Long convertStringDateToMillis(String date){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        LocalDate parseDate = LocalDate.parse(date, dateTimeFormatter);
        return parseDate.getLong(EPOCH_DAY);
    }
}

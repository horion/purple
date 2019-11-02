package challenger.purple.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Util {

    public static LocalDateTime convertStringDateToLocalDateTime(String date) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date dateResult = null;
        try {
            dateResult = inputFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert dateResult != null;
        return dateResult.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}

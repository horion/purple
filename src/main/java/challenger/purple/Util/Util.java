package challenger.purple.Util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

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

    public static JsonNode getJsonNode(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readTree(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

}
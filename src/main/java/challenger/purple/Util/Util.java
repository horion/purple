package challenger.purple.Util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Util {

    private static final Logger logger = LoggerFactory.getLogger(Util.class);

    public static LocalDateTime convertStringDateToLocalDateTime(String date) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date dateResult = null;
        try {
            dateResult = inputFormat.parse(date);
        } catch (ParseException e) {
            logger.error(e.getMessage(),e);
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
            logger.error(e.getMessage(),e);
            return null;
        }
    }

    public static String objectToJson(Object o){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(),e);
        }
        return "";
    }

}
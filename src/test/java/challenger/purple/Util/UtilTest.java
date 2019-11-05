package challenger.purple.Util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class UtilTest {

    ObjectNode jNode;
    private String date1;
    private String date2;
    private String date3;

    @BeforeEach
    void init(){
        ObjectMapper mapper = new ObjectMapper();
        jNode =  mapper.createObjectNode();
        ObjectNode account = mapper.createObjectNode();
        account.put("activeCard",true);
        account.put("availableLimit",100L);
        jNode.put( "account", account );
        date1 = "2019-02-13T10:00:00.000Z";

    }

    @Test
    void convertStringDateToLocalDateTime() {
        LocalDateTime localDateTime = Util.convertStringDateToLocalDateTime(date1);
        assertNotEquals(null,localDateTime);
    }

    @Test
    void getJsonNode() {
        String json = "{ \"account\": { \"activeCard\": true, \"availableLimit\": 100 } }";
        JsonNode  jsonNode = Util.getJsonNode(json);
        assert jsonNode != null;
        assertNotEquals(null,jsonNode.get("account"));
        assertEquals(jNode.get("account").get("activeCard"),jsonNode.get("account").get("activeCard"));
        assertEquals(jNode.get("account").get("availableLimit").asLong(),jsonNode.get("account").get("availableLimit").asLong());

    }

    @Test
    void getJsonNodeLimitFailed() {
        String json = "{ \"account\": { \"activeCard\": true, \"availableLimit\": 1000 } }";
        JsonNode  jsonNode = Util.getJsonNode(json);
        assert jsonNode != null;
        assertNotEquals(null,jsonNode.get("account"));
        assertEquals(jNode.get("account").get("activeCard"),jsonNode.get("account").get("activeCard"));
        assertNotEquals(jNode.get("account").get("availableLimit").asLong(),jsonNode.get("account").get("availableLimit").asLong());

    }

    @Test
    void getJsonNodeActiveCardFailed() {
        String json = "{ \"account\": { \"activeCard\": false, \"availableLimit\": 100 } }";
        JsonNode  jsonNode = Util.getJsonNode(json);
        assert jsonNode != null;
        assertNotEquals(null,jsonNode.get("account"));
        assertNotEquals(jNode.get("account").get("activeCard"),jsonNode.get("account").get("activeCard"));
        assertEquals(jNode.get("account").get("availableLimit").asLong(),jsonNode.get("account").get("availableLimit").asLong());

    }

}
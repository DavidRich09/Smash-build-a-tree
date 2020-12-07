

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Clase para le manejo de json
 */

public class Json {
    private static ObjectMapper objectMapper = getDefaultObjectMapper();

    private static ObjectMapper getDefaultObjectMapper() {
        ObjectMapper defaultObjectMapper = new ObjectMapper();
        return defaultObjectMapper;
    }

    /**
     * De objeto a json
     * @param o
     * @return JsonNode
     */

    public static JsonNode toJson(Object o){
        //De objeto a JsonNode
        return objectMapper.valueToTree(o);
    }

    /**
     * De jsonnode a String
     * @param node
     * @param pretty
     * @return String
     * @throws JsonProcessingException
     */

    public static String generateString(JsonNode node, boolean pretty) throws JsonProcessingException{
        //De Json node a string
        ObjectWriter objectWriter = objectMapper.writer();
        if(pretty){
            objectWriter = objectWriter.with(SerializationFeature.INDENT_OUTPUT);
        }
        return objectWriter.writeValueAsString(node);
    }
}

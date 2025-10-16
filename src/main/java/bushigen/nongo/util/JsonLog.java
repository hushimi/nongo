package bushigen.nongo.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class JsonLog {
    private static final ObjectWriter pretty = new ObjectMapper().writerWithDefaultPrettyPrinter();

    public static String of(Object obj) {
        try {
            return pretty.writeValueAsString(obj);
        } catch (Exception e) {
            return String.valueOf(obj);
        }
    }
}

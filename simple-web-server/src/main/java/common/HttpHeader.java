package common;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpHeader {
    private Map<String, Object> headers;

    public HttpHeader() {
        this.headers = new HashMap<>();
    }

    public void set(String headerName, Object headerValue) {
        this.headers.put(headerName, headerValue);
    }

    public Object get(String headerName) {
        return this.headers.get(headerName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Set<String> keys = headers.keySet();
        for(String key : keys) {
            sb.append(String.format("%s: %s\r\n", key, headers.get(key)));
        }
        return sb.toString();
    }
}

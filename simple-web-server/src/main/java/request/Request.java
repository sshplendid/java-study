package request;

import common.HttpHeader;
import common.Protocol;

public class Request {
    private HttpMethod method;
    private String uri;
    private Protocol protocol;
    private HttpHeader headers;

    public HttpMethod getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public HttpHeader getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    private String body;


    public static Request of(String requestMessage) {
        Request result = new Request();

        String[] messages = requestMessage.split("[\r\n]");
        String[] firstLine = messages[0].split("\\s");
        result.method = result.extractMethod(firstLine[0]);
        result.uri = firstLine[1];
        result.protocol = result.extractProtocol(firstLine[1]);



        return result;
    }

    private Protocol extractProtocol(String s) {
        return Protocol.HTTP;
    }

    private HttpMethod extractMethod(String methodString) {
        switch (methodString) {
            case "GET":
                return HttpMethod.GET;
            case "POST":
                return HttpMethod.POST;
            case "DELETE":
                return HttpMethod.DELETE;
            default:
                return HttpMethod.GET;
        }

    }
}

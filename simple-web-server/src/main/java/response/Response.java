package response;

import common.HttpHeader;

public class Response {
    private ResponseCode status;
    private String responseBody;
    private HttpHeader header;

    public static Response ok(String responseBody) {
        Response response = new Response();

        response.status = ResponseCode.OK;
        response.header = getDefaultHeaders(responseBody.getBytes().length);
        response.responseBody = responseBody;

        return response;
    }

    private static HttpHeader getDefaultHeaders(int contentLength) {
        HttpHeader headers = new HttpHeader();
        headers.set("Server", "MySimpleWebServer");
        headers.set("Content-Type", "text/html; charset=UTF-8");
        headers.set("Content-Length", contentLength);

        return headers;
    }

    public static Response notFound() {
        Response response = new Response();

        response.status = ResponseCode.NOT_FOUND;
        response.header = getDefaultHeaders(0);

        return response;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("HTTP/1.1 %s %s\n", this.status.getCode(), this.status.getMessage()));
        sb.append(this.header.toString());
        sb.append("\r\n");
        sb.append(this.responseBody);
        return super.toString();
    }
}

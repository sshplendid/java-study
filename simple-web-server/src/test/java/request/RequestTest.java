package request;


import org.junit.Before;
import org.junit.Test;

public class RequestTest {

    private String requestExample1 = "GET /favicon.ico HTTP/1.1\r\n" +
            "Host: localhost\r\n" +
            "Connection: keep-alive\r\n" +
            "Pragma: no-cache\r\n" +
            "Cache-Control: no-cache\r\n" +
            "User-Agent: Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36 NetHelper70\r\n" +
            "Accept: image/webp,image/apng,image/*,*/*;q=0.8\r\n" +
            "Referer: http://localhost/\r\n" +
            "Accept-Encoding: gzip, deflate, br\r\n" +
            "Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7\r\n" +
            "Cookie: Idea-5056d9be=7efe1eb1-b443-4756-9519-255c8a5ad571";

    @Before
    public void setUp() {

    }

    @Test
    public void requestTest() {
        Request req = Request.of(requestExample1);


    }

}
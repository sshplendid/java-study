package example.es;

import org.apache.http.HttpHost;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class ElasticSearchTests {

    private final String ENDPOINT = "vpc-es-dev-bo-com-01-hb62ztsnvhwvqtiqpizdxuh7me.ap-northeast-2.es.amazonaws.com";
    private RestHighLevelClient client;

    @Before
    public void setUp() throws Exception {
        client = new RestHighLevelClient(
                RestClient.builder(new HttpHost(ENDPOINT, 443, "https"))
        );
    }

    @After
    public void tearDown() throws Exception {
        client.close();
    }

    @Test
    public void syncGetRequestTest() throws IOException {
        GetResponse result = client.get(new GetRequest("shawn", "test", "0"), RequestOptions.DEFAULT);
        System.out.println(result.getSourceAsString());
    }

    @Test
    public void asyncGetRequestTest() throws InterruptedException {
        System.out.println(System.getProperty("java.io.tmpdir"));
        client.getAsync(new GetRequest("shawn", "test", "0"), RequestOptions.DEFAULT, new ActionListener<GetResponse>() {
            @Override
            public void onResponse(GetResponse documentFields) {
                System.out.println(Thread.currentThread().getName());
                System.out.println("finished to getResponse.");
                System.out.println(documentFields.getSourceAsString());
                try {
                    FileWriter fw = new FileWriter(new File(System.getProperty("java.io.tmpdir") + "/async.txt"));
                    fw.write("Async finished.");
                    fw.write(documentFields.getSourceAsString());
                    fw.write(LocalDateTime.now().toString());
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Exception e) {
                System.out.println("request failure.");
                System.out.println(e.getMessage());

            }
        });

        System.out.println(Thread.currentThread().getName());
        System.out.println("End of method.");
        Thread.sleep(5000);
        System.out.println("real end");
    }
}

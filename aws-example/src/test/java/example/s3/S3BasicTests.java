package example.s3;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

public class S3BasicTests {
    private AmazonS3 s3Client;
    private final String BUCKET_NAME = "bocommon-sample";
    private final String PROFILE_NAME = "dev";
    private final String PATH = System.getProperty("java.io.tmpdir");
    private final String FILE_NAME = "/hello-" + LocalDateTime.now() + ".txt";

    @Before
    public void setUp() {
        this.s3Client = AmazonS3Client.builder()
                .withCredentials(new ProfileCredentialsProvider(PROFILE_NAME))
                .withRegion(Regions.AP_NORTHEAST_2)
                .build();
    }

    @Test
    public void putObject() throws IOException {
        File f = new File(PATH + FILE_NAME);
        System.out.println(f.getAbsolutePath());
        if(!f.exists()) f.createNewFile();
        FileWriter fw = new FileWriter(f);
        fw.write("S3 테스트: " + LocalDateTime.now());
        fw.close();
        String key = "upload-by-sdk" + FILE_NAME;
        PutObjectResult response = s3Client.putObject(new PutObjectRequest(BUCKET_NAME, key, f));

        System.out.println(
                response
        );
    }

    @Test
    public void listBuckets() {
        s3Client.listBuckets().forEach(bucket -> {
            System.out.println("bucket: " + bucket.getName());
        });
    }

    @Test
    public void listObjects() {
        s3Client.listObjects(BUCKET_NAME).getObjectSummaries().forEach(obj -> {
            System.out.println("s3 object: " + obj.getKey());
        });
    }

    @Test
    public void getObjectToOutputStream() throws Exception {
        String key = "my/path/hi.txt";
        System.out.println("==Trying to get Object from S3.");
        S3Object obj = s3Client.getObject(BUCKET_NAME, key);
        try(InputStream is = obj.getObjectContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            br.lines().forEach(System.out::println);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        System.out.println("==Finished to get object.");
    }

    @Test
    public void getObjectToFile() {
        String key = "my/path/hi.txt";
        System.out.println("Trying to get Object from S3.");
        File file = new File(PATH + "/object.txt");
        if(file.exists()) {
            file.delete();
        }

        s3Client.getObject(new GetObjectRequest(BUCKET_NAME, key), file);
        System.out.println("Finished to get object.");
        System.out.println("==Started to read a file.");
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.lines().forEach(System.out::println);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        System.out.println("==Finished to read a file.");
    }

    @Test
    public void generatePreSignedURL() {
        long expirationTime = 5000;
        String key = "my/path/hi.txt";
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(BUCKET_NAME, key)
                .withExpiration(new Date(System.currentTimeMillis() + expirationTime));
        URL url = s3Client.generatePresignedUrl(request);
        System.out.println(url);
    }

    @Test
    public void generatePreSignedUrlAndPutObject() throws IOException {
        long expirationTime = 30 * 1000;
        String key = "presigned-upload.txt";

        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(BUCKET_NAME, key)
                .withMethod(HttpMethod.PUT)
                .withExpiration(new Date(System.currentTimeMillis() + expirationTime));

        URL url = s3Client.generatePresignedUrl(request);
        System.out.println("PreSignedURL: " + url);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("PUT");
        try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream())) {
            writer.write("미리 서명된 URL을 통한 파일 업로드 테스트");
        }

        System.out.println("업로드 결과: " + connection.getResponseCode());
    }

    @Test
    public void generatePreSignedUrlAndPutS3SelectJson() throws IOException {
        String sqlJson = "[{\n" +
                "    \"Rules\": [\n" +
                "        {\"id\": \"id-1\", \"condition\": \"x < 20\"},\n" +
                "        {\"condition\": \"y > x\"},\n" +
                "        {\"id\": \"id-2\", \"condition\": \"z = DEBUG\"}\n" +
                "    ]\n" +
                "},\n" +
                "{\n" +
                "    \"created\": \"June 27\",\n" +
                "    \"modified\": \"July 6\"\n" +
                "}]";
        String objectKey = "s3-sql.json";

        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(BUCKET_NAME, objectKey)
                .withMethod(HttpMethod.PUT)
                .withExpiration(Date.from(Instant.now().plusSeconds(60)));

        URL preSignedUrl = s3Client.generatePresignedUrl(request);
        HttpURLConnection connection = (HttpURLConnection) preSignedUrl.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("PUT");
        try (OutputStreamWriter writer  = new OutputStreamWriter(connection.getOutputStream())) {
            writer.write(sqlJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("S3 PutObject Result: " + connection.getResponseCode());
    }
}

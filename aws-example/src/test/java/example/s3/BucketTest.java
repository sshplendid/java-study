package example.s3;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.DeleteBucketRequest;
import org.junit.Before;
import org.junit.Test;

import java.util.logging.Logger;

public class BucketTest {
    private static final Logger log = Logger.getLogger(BucketTest.class.getName());

    private final String PROFILE_NAME = "s3user";
    private final String BUCKET_NAME = "shawn-sdk-bucket";
    private AmazonS3 s3Client;

    @Before
    public void setUp() {
        this.s3Client = AmazonS3Client.builder()
                .withCredentials(new ProfileCredentialsProvider(PROFILE_NAME))
                .withRegion(Regions.AP_NORTHEAST_2)
                .build();
    }

    @Test
    public void createBucket() {
        CreateBucketRequest createBucketRequest = new CreateBucketRequest(BUCKET_NAME);
        Bucket bucket = s3Client.createBucket(createBucketRequest);
        System.out.println(bucket.getCreationDate());
    }

    @Test
    public void deleteBucket() {
        DeleteBucketRequest deleteBucketRequest = new DeleteBucketRequest(BUCKET_NAME);
        s3Client.deleteBucket(deleteBucketRequest);
    }
}

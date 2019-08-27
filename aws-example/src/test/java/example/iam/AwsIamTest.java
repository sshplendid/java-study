package example.iam;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementAsyncClientBuilder;
import com.amazonaws.services.identitymanagement.model.*;
import org.junit.Before;
import org.junit.Test;

public class AwsIamTest {
    private AmazonIdentityManagement iamClient;
    private String PROFILE_NAME = "admin";

    @Before
    public void setUp() throws Exception {
        iamClient = AmazonIdentityManagementAsyncClientBuilder.standard()
                .withRegion(Regions.AP_NORTHEAST_2)
                .withCredentials(new ProfileCredentialsProvider(PROFILE_NAME)).build();
    }

    @Test
    public void createS3AccessPolicy() {
        String s3PolicyDocument = getS3PolicyDocument();
        CreatePolicyRequest request = new CreatePolicyRequest()
                .withPolicyName("s3user")
                .withPolicyDocument(s3PolicyDocument)
                .withDescription("S3_test");
        CreatePolicyResult result = iamClient.createPolicy(request);
        System.out.println("result: " + result.getSdkHttpMetadata().getHttpStatusCode());
    }

    @Test
    public void createUserAndAttachPolicy() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUserName("sdk-user");
        CreateUserResult createUserResult = iamClient.createUser(createUserRequest);
        System.out.println("createUserResult: " + createUserResult.getUser().getUserName());

        AttachUserPolicyRequest userPolicyRequest = new AttachUserPolicyRequest()
                .withPolicyArn("arn:aws:iam::160770579176:policy/s3user")
                .withUserName("sdk-user");
        AttachUserPolicyResult attachUserPolicyResult = iamClient.attachUserPolicy(userPolicyRequest);
        System.out.println("attachUserPolicyResult: " + attachUserPolicyResult.getSdkHttpMetadata().getHttpStatusCode());
    }

    @Test
    public void detachUserPolicy() {
        DetachUserPolicyRequest detachUserPolicyRequest = new DetachUserPolicyRequest()
                .withUserName("sdk-user")
                .withPolicyArn("arn:aws:iam::160770579176:policy/s3user")
                ;

        iamClient.detachUserPolicy(detachUserPolicyRequest);
    }
    @Test
    public void deleteUser() {
        DeleteUserRequest deleteUserRequest = new DeleteUserRequest()
                .withUserName("sdk-user");
        DeleteUserResult deleteUserResult = iamClient.deleteUser(deleteUserRequest);
        System.out.println(deleteUserResult.toString());
        System.out.println(deleteUserResult.getSdkHttpMetadata().getHttpStatusCode());

    }

    @Test
    public void createAccessKey() {
        CreateAccessKeyRequest createAccessKeyRequest = new CreateAccessKeyRequest()
                .withUserName("sdk-user")
                ;
        AccessKey accessKey = iamClient.createAccessKey(createAccessKeyRequest).getAccessKey();
        System.out.println("Finished to create the access key. " + accessKey.getUserName());
        System.out.println(accessKey.getAccessKeyId() + " / " + accessKey.getSecretAccessKey());
    }

    private String getS3PolicyDocument() {
        return "{\n" +
                "    \"Version\": \"2012-10-17\",\n" +
                "    \"Statement\": [\n" +
                "        {\n" +
                "            \"Sid\": \"VisualEditor0\",\n" +
                "            \"Effect\": \"Allow\",\n" +
                "            \"Action\": [\n" +
                "                \"s3:ListBucketMultipartUploads\",\n" +
                "                \"s3:CreateBucket\",\n" +
                "                \"s3:ListBucket\",\n" +
                "                \"s3:PutBucketCORS\",\n" +
                "                \"s3:DeleteBucket\"\n" +
                "            ],\n" +
                "            \"Resource\": \"arn:aws:s3:::*\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"Sid\": \"VisualEditor1\",\n" +
                "            \"Effect\": \"Allow\",\n" +
                "            \"Action\": [\n" +
                "                \"s3:PutObject\",\n" +
                "                \"s3:GetObject\",\n" +
                "                \"s3:DeleteObject\",\n" +
                "                \"s3:ListMultipartUploadParts\"\n" +
                "            ],\n" +
                "            \"Resource\": \"arn:aws:s3:::*/*\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"Sid\": \"VisualEditor2\",\n" +
                "            \"Effect\": \"Allow\",\n" +
                "            \"Action\": \"s3:ListAllMyBuckets\",\n" +
                "            \"Resource\": \"*\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
    }
}

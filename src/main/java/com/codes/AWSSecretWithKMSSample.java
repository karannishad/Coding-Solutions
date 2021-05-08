package com.codes;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.BucketLifecycleConfiguration;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.DecryptionFailureException;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.amazonaws.services.secretsmanager.model.InternalServiceErrorException;
import com.amazonaws.services.secretsmanager.model.InvalidRequestException;
import com.amazonaws.services.secretsmanager.model.ResourceNotFoundException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class AWSSecretWithKMSSample {

    public static void getSecret() {

        String secretName = "test-secret";
        String region = "ap-south-1";

        AWSSecretsManager client = AWSSecretsManagerClientBuilder.standard()
                .withRegion(region)
                .build();

        String secret, decodedBinarySecret;
        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest()
                .withSecretId(secretName);
        GetSecretValueResult getSecretValueResult = null;

        try {
            getSecretValueResult = client.getSecretValue(getSecretValueRequest);
        } catch (DecryptionFailureException | InternalServiceErrorException | InvalidParameterException | InvalidRequestException | ResourceNotFoundException e) {

            throw e;
        }
        if (getSecretValueResult.getSecretString() != null) {
            secret = getSecretValueResult.getSecretString();
            System.out.println(secret);
        } else {
            decodedBinarySecret = new String(Base64.getDecoder().decode(getSecretValueResult.getSecretBinary()).array());
            System.out.println(decodedBinarySecret);

        }


    }

    public static void main(String[] args) {
        BucketLifecycleConfiguration.Rule rule;
        AmazonS3 amazonS3 = AmazonS3ClientBuilder.defaultClient();
        BucketLifecycleConfiguration bucketLifecycleConfiguration = amazonS3.getBucketLifecycleConfiguration("parqubuck");
        rule = bucketLifecycleConfiguration.getRules().stream().filter(rule1 -> rule1.getId().equals("Expiry with  cmon")).findFirst()
                .get();
        rule.withExpirationInDays(1);
        amazonS3.setBucketLifecycleConfiguration("parqubuck", bucketLifecycleConfiguration);
    }

    public void anothermethod() throws InvalidKeySpecException, NoSuchAlgorithmException {
        String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvXc7m5bhKroFdrYTCiTEICCDuzEw9kfq/UeQ4ydEdEJfiXBZCB8hFNclUqA6XjVnJv89DjSeqf7XC/ia9cIAD4atoRwFGD2wweK2kJy9DnITuneec2kOtNPX+/gvm6nGpiXbbOYT8H/F6B80lpPWvr/D2BECjbcacQQ+nbU/rU6iIGo2eqb78XVgshTEMKfdWOaG4v4vx8+/Ppo3poFpWeVxAmlMGVNimDTYboxetwo2KV0RNNNmdLsb6WS8llfj3wD1PG3MctQJQ/6fX0We+0KLoqFbreRFqUCGv2LUQUFzf+9vGErYAj079v6XZUVwiPxOr2/keaUQqk9DgC8PCwIDAQAB";
        String privateKeyPlain = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC9dzubluEqugV2thMKJMQgIIO7MTD2R+r9R5DjJ0R0Ql+JcFkIHyEU1yVSoDpeNWcm/z0ONJ6p/tcL+Jr1wgAPhq2hHAUYPbDB4raQnL0OchO6d55zaQ6009f7+C+bqcamJdts5hPwf8XoHzSWk9a+v8PYEQKNtxpxBD6dtT+tTqIgajZ6pvvxdWCyFMQwp91Y5obi/i/Hz78+mjemgWlZ5XECaUwZU2KYNNhujF63CjYpXRE002Z0uxvpZLyWV+PfAPU8bcxy1AlD/p9fRZ77QouioVut5EWpQIa/YtRBQXN/728YStgCPTv2/pdlRXCI/E6vb+R5pRCqT0OALw8LAgMBAAECggEAMhxWhiTGQgw4RLkMWRr3Sn3/CWQNzJ2h38JtZj7tAojJhurRiW3eTIpIw01AkskyEqTgS6EMM+8EhpSGu6hHZtPamWl1SX+LgARL//yMnkT6xc88d87aBmafUfPG97dUJH6PxgImVO4edHHIcWkBm6rRjBSXpc6/bhPO0dxtzpKwgoR6O9EOT+extyVZdAXby2HqHVxvMDdO5xnDP2qGYjmeqqhsJO0FrbZ7rx2X6Q5bof5c0z08xTG9lxna3tuhz1Mpnr9dU0HglFULmBMm+bDvxStnJrtlEAsE3tE5yIJBDGCWN+WB+nWV4SSpEH+3MJxjfse+Fr3gJ/choIUwgQKBgQDliN1LX73tRnt6zmQ6e0Nf2ZvbeXqCKvEv64RxrYrmvFjxc1gilGOHCHH9MWTHktMAOPLTZHD1zjCKfpV45yLigP111zCk+p9v8+k4kvw/HBSLoZf+0VCe92V7Bmu8fT4qIj7oohE+EVZZD/VL/Hg2RgFJLS7T+O5sIHFQ+5lDgQKBgQDTT6mAD9JoMPf4BzkbMX/+MurqCzaH9tIQPso1o5JIYnUoRt317t+HZX1Y4qzUUBVR+v1fsVqPgvZe7I6PujAVspEtpl7sMSfgsjUkM9B1kAD/yc/5A2HBvsjnOwNT2ans5Q+fOtXNEnTR1VtsIqT09dNfUTAPToVvAqTzugtoiwKBgHrc79dQwFEp0R7DK1NCd6XuzUaFl9XlNoT4BET1J8yqNNHJc/vOghfdpWZp8hABv3/2+8O7l9t7KvB3IXs9/aZQdVuGfDtTC9TZMpVd2DfPyA+Vx85WLFlrA57+qNyUDZpwCkxrs98NWLMDn8G0uoQSpM2Oqeo87SJjm0RF6JwBAoGAFuvsQgT1WPH+4/u8mROMg8F4M/0VZDhE3xhAjQL6SjnL5ZuSViwcOS6sIykrgDhTp7nnJCsz3J9jJDba3AJhVukw0Ffcshg+8CPEmfG46he91AFLOwK/5ulQF60SFlVlFlpekHIsYPtgiqZvLVKwvBWyYQOIiBCHbtxFkl6bADMCgYEAqQ2n0+s3SYigr5UexH+fNIeLIyAWZYAPaiINCarY1iUuC9krwkU6l0T6eVgs9oojLvqyvxFubQkCDjTxUZStGkx32DElv26Gfhg0lOqXNjOr5s5xrZ1kpWnQ7XJUFbSAL4jHjrCg6QwTpkJ0on6yxLtkUPImelsSYEymUyou4FU=";
        String privateKeyCiphered = "AQIDAHiZj6ASVwfM5zdt4W/wOTDQRiepM/ULBYxZ5tXFuacsLwGW42DRBA0qUpc6vuStM6H7AAAFKTCCBSUGCSqGSIb3DQEHBqCCBRYwggUSAgEAMIIFCwYJKoZIhvcNAQcBMB4GCWCGSAFlAwQBLjARBAzGn0cQMgyFucz6vesCARCAggTcsmsTuUhpZh+8qEjze7FEGIS0vJ6CM4KDJ/rxozdim9hkq9Z3P/UxIEKeYGDNQ4a99AOedbJnO7QyZKr5NcIElCndkh+CdJ98htXvG9NH760KZfQyLbqntwaS08rIC/AnwLE4t0N7YKxb/f30SOyhlWNUKvjANpt+SPATLDaGhRfyvSJ28WcoH+UmfgolCcNdcLQ3a3D61IyJ20WyMsk2tRflzQpRBxkpBWvg033C3FnXuYlsGyfIIKJl6Vy5B8i6JCcI/HmlJ3ZPugVYl+DK1wRYsnK8egvLp8ZnikviqC/ecCLv5evJEn+djYLl5e5EwMaapTR0Ohn8aI4CZIdAdSmQNayoK+qYtGABloO/OY/B+PXWBso6HdhpANj8KwKDoWM+gAKllrxd62bG/ixCITP3glW5KbgxWk+PQYiKeNsTMFDEmKRqOsvVFao7/eRJ5iW9mv2JBQvriySUxUA07YZBRQrPqS9LZpuMJoiA8wrf7iScjMvidqs4LJ6XoB99x1uMjq4liSb4DBDR/GWrofqlnpT9alW6DscjiAmfLhubjEmHksDglAKmYdXzOkqT0aWULVCQ0QUXCNVPh7BFhB10enM7GWLuMKQFeDLGofG/PKoEfQZ42CiLa3fITwnte1FanvOqIDKiYQimVXAbJqtrVVPPDYCb+hV4tJ3Tkkle78wkeFDF9dLiOXPAeLfcGBSrY24A+FFHNk3wyYzX/raY70jt/uTPrDfm4qSGQoSKIdUR4jmH3iTruoPaopR85+FMeyOGv20WFDpK1JEg/SiE3qmDoAFSj4gJCRndrTyXjsLwyvcLOF99xGquo+rDTmpPK0My57gImaXWLm2lowwoHaDS2ueect940Pe6L9u5cXTlNMrQxvR32bzjd8XluuilkUmokzCaTz0g5PAS5T6DrOMcP6LLtP5Vkgv3gPREjEMNZSaAB+tXphysfUzg2z+K4xZcBWHFfRfuX8AN1kZcQPZTMV3SLTTud3/uAs2Ad9UKainkE2+PkcIy7ImFgI8gA+7gogcAp8NWAc9X96GaDw2aKcJKlWEs4lRwIxoUDXfUMAhrhFcxXNgymmSMvfU80ONhlVZ5E2Xb0oUNePncwVcGwDxFj4AiUuptcDM2IB8ZGJcSV6I4vgi6qbJG2VmBFcOJRPFffVCqDidaRWiE66goUbw57lrp55jJRuOl80POVPQM3UQj53+rtHQ7rj4k+o19y0IRpOspXt0eAhpKfAJdKgQSESqfIL8TOR664oDKcrXP2/d4pmP8iaKTTdgorM35ESZQcJYNRX2ovh+43MMQQb7EDDSjFF3HBOv7ufzeekAh3zx4Wlw4v891uqUy+JK1xkTJC/rwqpfEKZV6Vi64Ht5Z+HVeX7Rzp9qth76IsnimMTlsd6i3mbjXoYNjBrofVhj0bfdOGeEECPKSxiZ2XVRKtwWJ6oh3zXEjY92scQ0IdyO7PGQjc1hCi1FW+pI5gMQlTqPnH7qI33YGlmyuS1hSXbfi72PPVREzTXyMFgJSZbUb3KqTmikAGKYTm+N1FsotC0aPUJ4r/5rzKc+uYrwWFKe/nSmk70NdMHKkynk/9pySkmRQl01CUNYZfavZ3fpHUX6Ce1bgpM3VTALl1li60v8WgUmho9U9WfP7cGtdctj/qXo=";


        X509EncodedKeySpec spec =
                new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey));
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PublicKey publicKey1 = kf.generatePublic(spec);

        PKCS8EncodedKeySpec spec1 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyPlain));
        KeyFactory kf1 = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = kf1.generatePrivate(spec1);


        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey1);


            Cipher cipherdecode = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipherdecode.init(Cipher.DECRYPT_MODE, privateKey);

            String toEncode = "WELCOME TO DATA";

            String enod = Base64.getEncoder().encodeToString(cipher.doFinal(toEncode.getBytes()));

            System.out.println("Encoded " + enod);


            System.out.println("Decoded " + new String(cipherdecode.doFinal(Base64.getDecoder().decode(enod))));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
    }
}

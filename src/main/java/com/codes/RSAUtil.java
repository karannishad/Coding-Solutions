package com.codes;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAUtil {

    //private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCgFGVfrY4jQSoZQWWygZ83roKXWD4YeT2x2p41dGkPixe73rT2IW04glagN2vgoZoHuOPqa5and6kAmK2ujmCHu6D1auJhE2tXP+yLkpSiYMQucDKmCsWMnW9XlC5K7OSL77TXXcfvTvyZcjObEz6LIBRzs6+FqpFbUO9SJEfh6wIDAQAB";
    private static final String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvXc7m5bhKroFdrYTCiTEICCDuzEw9kfq/UeQ4ydEdEJfiXBZCB8hFNclUqA6XjVnJv89DjSeqf7XC/ia9cIAD4atoRwFGD2wweK2kJy9DnITuneec2kOtNPX+/gvm6nGpiXbbOYT8H/F6B80lpPWvr/D2BECjbcacQQ+nbU/rU6iIGo2eqb78XVgshTEMKfdWOaG4v4vx8+/Ppo3poFpWeVxAmlMGVNimDTYboxetwo2KV0RNNNmdLsb6WS8llfj3wD1PG3MctQJQ/6fX0We+0KLoqFbreRFqUCGv2LUQUFzf+9vGErYAj079v6XZUVwiPxOr2/keaUQqk9DgC8PCwIDAQAB";
    //private static String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAKAUZV+tjiNBKhlBZbKBnzeugpdYPhh5PbHanjV0aQ+LF7vetPYhbTiCVqA3a+Chmge44+prlqd3qQCYra6OYIe7oPVq4mETa1c/7IuSlKJgxC5wMqYKxYydb1eULkrs5IvvtNddx+9O/JlyM5sTPosgFHOzr4WqkVtQ71IkR+HrAgMBAAECgYAkQLo8kteP0GAyXAcmCAkA2Tql/8wASuTX9ITD4lsws/VqDKO64hMUKyBnJGX/91kkypCDNF5oCsdxZSJgV8owViYWZPnbvEcNqLtqgs7nj1UHuX9S5yYIPGN/mHL6OJJ7sosOd6rqdpg6JRRkAKUV+tmN/7Gh0+GFXM+ug6mgwQJBAO9/+CWpCAVoGxCA+YsTMb82fTOmGYMkZOAfQsvIV2v6DC8eJrSa+c0yCOTa3tirlCkhBfB08f8U2iEPS+Gu3bECQQCrG7O0gYmFL2RX1O+37ovyyHTbst4s4xbLW4jLzbSoimL235lCdIC+fllEEP96wPAiqo6dzmdH8KsGmVozsVRbAkB0ME8AZjp/9Pt8TDXD5LHzo8mlruUdnCBcIo5TMoRG2+3hRe1dHPonNCjgbdZCoyqjsWOiPfnQ2Brigvs7J4xhAkBGRiZUKC92x7QKbqXVgN9xYuq7oIanIM0nz/wq190uq0dh5Qtow7hshC/dSK3kmIEHe8z++tpoLWvQVgM538apAkBoSNfaTkDZhFavuiVl6L8cWCoDcJBItip8wKQhXwHp0O3HLg10OEd14M58ooNfpgt+8D8/8/2OOFaR0HzA+2Dm";
    private static final String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC9dzubluEqugV2thMKJMQgIIO7MTD2R+r9R5DjJ0R0Ql+JcFkIHyEU1yVSoDpeNWcm/z0ONJ6p/tcL+Jr1wgAPhq2hHAUYPbDB4raQnL0OchO6d55zaQ6009f7+C+bqcamJdts5hPwf8XoHzSWk9a+v8PYEQKNtxpxBD6dtT+tTqIgajZ6pvvxdWCyFMQwp91Y5obi/i/Hz78+mjemgWlZ5XECaUwZU2KYNNhujF63CjYpXRE002Z0uxvpZLyWV+PfAPU8bcxy1AlD/p9fRZ77QouioVut5EWpQIa/YtRBQXN/728YStgCPTv2/pdlRXCI/E6vb+R5pRCqT0OALw8LAgMBAAECggEAMhxWhiTGQgw4RLkMWRr3Sn3/CWQNzJ2h38JtZj7tAojJhurRiW3eTIpIw01AkskyEqTgS6EMM+8EhpSGu6hHZtPamWl1SX+LgARL//yMnkT6xc88d87aBmafUfPG97dUJH6PxgImVO4edHHIcWkBm6rRjBSXpc6/bhPO0dxtzpKwgoR6O9EOT+extyVZdAXby2HqHVxvMDdO5xnDP2qGYjmeqqhsJO0FrbZ7rx2X6Q5bof5c0z08xTG9lxna3tuhz1Mpnr9dU0HglFULmBMm+bDvxStnJrtlEAsE3tE5yIJBDGCWN+WB+nWV4SSpEH+3MJxjfse+Fr3gJ/choIUwgQKBgQDliN1LX73tRnt6zmQ6e0Nf2ZvbeXqCKvEv64RxrYrmvFjxc1gilGOHCHH9MWTHktMAOPLTZHD1zjCKfpV45yLigP111zCk+p9v8+k4kvw/HBSLoZf+0VCe92V7Bmu8fT4qIj7oohE+EVZZD/VL/Hg2RgFJLS7T+O5sIHFQ+5lDgQKBgQDTT6mAD9JoMPf4BzkbMX/+MurqCzaH9tIQPso1o5JIYnUoRt317t+HZX1Y4qzUUBVR+v1fsVqPgvZe7I6PujAVspEtpl7sMSfgsjUkM9B1kAD/yc/5A2HBvsjnOwNT2ans5Q+fOtXNEnTR1VtsIqT09dNfUTAPToVvAqTzugtoiwKBgHrc79dQwFEp0R7DK1NCd6XuzUaFl9XlNoT4BET1J8yqNNHJc/vOghfdpWZp8hABv3/2+8O7l9t7KvB3IXs9/aZQdVuGfDtTC9TZMpVd2DfPyA+Vx85WLFlrA57+qNyUDZpwCkxrs98NWLMDn8G0uoQSpM2Oqeo87SJjm0RF6JwBAoGAFuvsQgT1WPH+4/u8mROMg8F4M/0VZDhE3xhAjQL6SjnL5ZuSViwcOS6sIykrgDhTp7nnJCsz3J9jJDba3AJhVukw0Ffcshg+8CPEmfG46he91AFLOwK/5ulQF60SFlVlFlpekHIsYPtgiqZvLVKwvBWyYQOIiBCHbtxFkl6bADMCgYEAqQ2n0+s3SYigr5UexH+fNIeLIyAWZYAPaiINCarY1iUuC9krwkU6l0T6eVgs9oojLvqyvxFubQkCDjTxUZStGkx32DElv26Gfhg0lOqXNjOr5s5xrZ1kpWnQ7XJUFbSAL4jHjrCg6QwTpkJ0on6yxLtkUPImelsSYEymUyou4FU=";


    public static PublicKey getPublicKey(String base64PublicKey) {
        PublicKey publicKey = null;
        try {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(base64PublicKey.getBytes()));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(keySpec);
            return publicKey;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return publicKey;
    }

    public static PrivateKey getPrivateKey(String base64PrivateKey) {
        PrivateKey privateKey = null;
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(base64PrivateKey.getBytes()));
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            privateKey = keyFactory.generatePrivate(keySpec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return privateKey;
    }

    public static byte[] encrypt(String data, String publicKey) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
        return cipher.doFinal(data.getBytes());
    }

    public static String decrypt(byte[] data, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(data));
    }

    public static String decrypt(String data, String base64PrivateKey) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        return decrypt(Base64.getDecoder().decode(data.getBytes()), getPrivateKey(base64PrivateKey));
    }

    public static void main(String[] args) throws IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, BadPaddingException {
        try {
            String encryptedString = Base64.getEncoder().encodeToString(encrypt("Dhiraj is the author", publicKey));
            System.out.println(encryptedString);
            String decryptedString = RSAUtil.decrypt(encryptedString, privateKey);
            System.out.println(decryptedString);
        } catch (NoSuchAlgorithmException e) {
            System.err.println(e.getMessage());
        }

    }
}


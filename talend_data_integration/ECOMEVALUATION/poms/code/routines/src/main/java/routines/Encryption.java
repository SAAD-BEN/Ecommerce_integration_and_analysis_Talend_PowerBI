package routines;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Encryption {

    public static String do_encryption(String text,String passwd)throws Exception {
        String password = passwd;
        byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec secretKeySpec = new SecretKeySpec(passwordBytes, "AES");
        // Create a cipher for encryption
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);


        //Code generated according to input schema and output schema
        return Base64.getEncoder().encodeToString(cipher.doFinal(text.getBytes(StandardCharsets.UTF_8)));
}

public static String do_decencryption(String text,String passwd)throws Exception {
        String password = passwd;
        byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec secretKeySpec = new SecretKeySpec(passwordBytes, "AES");
        // Create a cipher for encryption
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(text));
        //Code generated according to input schema and output schema
          String decryptedMessage = new String(decryptedBytes, StandardCharsets.UTF_8);
          return decryptedMessage;
}
}

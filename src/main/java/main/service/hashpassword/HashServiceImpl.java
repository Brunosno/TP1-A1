package main.service.hashpassword;

import java.util.Base64;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HashServiceImpl implements HashService{
    
    private String salt = "qwedsa";
    private Integer interationCount = 403;
    private Integer keyLength = 512;

    @Override
    public String getHashSenha(String senha) throws Exception {
        byte[] result;

        try {
            result = SecretKeyFactory
                    .getInstance("PBKDF2WithHmacSHA512")
                    .generateSecret(new PBEKeySpec(senha.toCharArray(),
                            salt.getBytes(),
                            interationCount,
                            keyLength)).getEncoded();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            throw new Exception("Problema ao gerar o hash");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new Exception("Problema ao gerar o hash");
        }
        
        return Base64.getEncoder().encodeToString(result);
    }
}

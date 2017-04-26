package Uteis;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author jthan
 */
public class MD5 {

    /**
     * Recebe uma String e converte para MD5.
     * @param senha String que será convertida.
     * @return A String convertida para MD5.
     */
    public String gerarMD5(String senha) {

        MessageDigest m;

        try {
            m = MessageDigest.getInstance("MD5");
            m.update(senha.getBytes(), 0, senha.length());
            BigInteger i = new BigInteger(1, m.digest());

            //Formatando o resuldado em uma cadeia de 32 caracteres, completando com 0 caso falte 
            senha = String.format("%1$032X", i);
            return senha;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        
        return null;
    }
}

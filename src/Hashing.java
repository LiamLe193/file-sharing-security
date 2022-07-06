import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hashing {
    //formula of ID_pass = SHA-512 (11) times + SHA-256 (19) times
    //formula of public_key = random_times from 10 to 99 then put at the end of String SHA-1
    public String hash_public_key(String key, int random_number)
    {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] messageDigest = md.digest(key.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hash_text = no.toString(16);
            while (hash_text.length() < 32) {
                hash_text = "0" + hash_text;
            }
            while(random_number > 1)
            {
                hash_text = hash_public_key(hash_text,random_number--);
            }
            return hash_text;
        }

        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public void hash_name_pass()
    {

    }
}

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Keys {
    String path = "C:\\Users\\thien\\IdeaProjects\\File_Share_security\\Files\\";
    Hashing hash = new Hashing();
    private String real_key;
    private int random_times;
    private String temp_path;
    public void set_temp_path(String path)
    {
        temp_path = path;
    }
    public String getTemp_path()
    {
        return temp_path;
    }
    public void set_times(int random_numbers)
    {
        random_times = random_numbers;
    }
    public void set_key(String real_key)
    {
        this.real_key = real_key;
    }
    public String get_real_key()
    {
        return real_key;
    }
    public int get_random()
    {
        return random_times;
    }
    boolean isValid_key(String key)
    {
        if(key.length() != 8)
        {
            return false;
        }
        int number = Integer.parseInt(key.substring(key.length() - 2));
        if(number < 10)
        {
            return false;
        }
        String k = key.substring(0, key.length() - 2);
        String regex = "[A-Z0-9]";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(k);
        boolean result = m.matches();
        if(result)
        {
            set_times(number);
            set_key(k);
            return true;
        }
        return false;
    }
    boolean public_key_existed(String key)
    {
        if(isValid_key(key))
        {
            String real_key = get_real_key();
            int times = get_random();
            String temp_path = hash.hash_public_key(real_key, times);
            File temp_Dir = new File(path + temp_path);
            set_temp_path(path + temp_path);
            return temp_Dir.exists();
        }
        return false;
    }

}

import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//create new users
public class Users
{
    Hashing hash = new Hashing();
    private String ID;
    private String password;
    public Users()
    {
        ID = "";
        password = "";
    }
    public void set_ID(String ID)
    {
        this.ID = ID;
    }
    public void set_password(String password)
    {
        this.password = password;
    }
    public String getID()
    {
        return ID;
    }
    public String getPassword()
    {
        return password;
    }
    String file_name = "ID_PASSWORD_list.csv";
    String path = "C:\\Users\\thien\\IdeaProjects\\File_Share_security\\list\\" + file_name;
    boolean search_users_only(String users_id) throws FileNotFoundException
    {
        Scanner sc = new Scanner(new File(path));
        sc.useDelimiter(",");
        sc.nextLine();
        while(sc.hasNext())
        {
            String data = sc.next();
            if(data.equals(users_id))
            {
                return true;
            }
            sc.nextLine();
        }
        sc.close();
        return false;
    }
    boolean search_users(String users_id, String password) throws FileNotFoundException
    {
        Scanner sc = new Scanner(new File(path));
        sc.useDelimiter(",");
        sc.nextLine();
        while(sc.hasNext())
        {
            String data = sc.next();
            if(data.equals(users_id))
            {
                data = sc.next();
                data = data.replaceAll("(\\r|\\n)", "");
                return data.equals(password);
            }
            sc.nextLine();
        }
        sc.close();
        return false;
    }
    /*
    Need rework for this function
     */
    boolean change_password(String users_id, String new_password, String old_password) throws IOException
    {
        File current_file = new File(path);
        File temp_file = new File("C:\\Users\\thien\\IdeaProjects\\File_Share_security\\list\\temp.csv");

        Scanner sc = new Scanner(current_file);
        sc.useDelimiter(",");
        sc.nextLine();
        BufferedWriter writer = new BufferedWriter(new FileWriter(temp_file));
        while(sc.hasNext())
        {
            String data = sc.next();
            String data_2 = sc.next();
            if(!data.equals(users_id))
            {
                writer.write(data + "," + data_2 + ",");
            }
        }
        writer.close();
        sc.close();
        current_file.delete();
        return temp_file.renameTo(current_file);
    }
    void create_users(DataOutputStream dos, DataInputStream dis) throws IOException {
        String Username = dis.readUTF();
        Username = hash.hash_combine(Username);
        if(search_users_only(Username))
        {
            dos.writeUTF("Existed");
        }
        else
        {
            dos.writeUTF("No");
            set_ID(Username);
            String password = dis.readUTF();
            password = hash.hash_combine(password);
            set_password(password);
            FileWriter fw = new FileWriter(path,true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(getID() + "," + getPassword() + ",");
            bw.newLine();
            bw.close();
        }
    }
}

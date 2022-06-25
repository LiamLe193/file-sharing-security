import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//create new users
public class Users
{
    private String ID;
    private String password;
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
    String path = "C:\\Users\\thien\\IdeaProjects\\File_Share_security\\list\\ID_PASSWORD_list.csv";
    boolean search_users(String users_id) throws FileNotFoundException
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
    void change_password(String users_id)
    {

    }
    boolean check_id_rules(String users_id)
    {
        String regex = "^(?=.*[a-z])(?=.*[0-9])(?=\\S+$).{6,14}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(users_id);
        return m.matches();
    }

    boolean check_passwords_rules(String password)
    {
        String regex = "^(?=.*[a-z])(?=.*[0-9])(?=\\S+$).{6,20}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);
        return m.matches();
    }
    void create_users() throws IOException {
        Scanner input = new Scanner(System.in);
        while(true)
        {
            System.out.print("Please enter your username: ");
            String Username = input.nextLine();
            if (!search_users(Username)) {
                if (!check_id_rules(Username)) {
                    System.out.println("\nUsername must contain: \nLowercase letters. \nNumbers. \nFrom 6 to 14 characters. \n");
                } else if (check_id_rules(Username)) {
                    set_ID(Username);
                    break;
                }
            } else System.out.println("\nYour username is already existed. \n");
        }
        while(true)
        {
            System.out.print("\nPlease enter your password: ");
            String password = input.nextLine();
            if(check_passwords_rules(password))
            {
                System.out.println("\nYour account has successfully created");
                set_password(password);
                break;
            }
            else System.out.println("\nYour password must contain: \nLowercase letters. \nNumbers. \nFrom 6 to 20 characters.\n");
        }
        FileWriter fw = new FileWriter(path,true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(getID() + "," + getPassword());
        bw.newLine();
        bw.close();
    }
}

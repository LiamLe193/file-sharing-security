import java.io.*;
import java.util.Scanner;
import static java.lang.System.exit;

public class Users_Interface
{
    Keys keys = new Keys();
    String path = "C:\\Users\\thien\\IdeaProjects\\File_Share_security\\Files";
    Users user = new Users();

    public boolean is_member(DataOutputStream dos, DataInputStream dis) throws IOException {
        int attempt = 1;
        while(true)
        {
            String answer = dis.readUTF();
            if(answer.equals("Yes"))
            {
                for (int count = 3; count >= 0; count--)
                {
                    dos.writeUTF("Please sign in. \n" + "Username: ");
                    String input_user = dis.readUTF();
                    dos.writeUTF("Password: ");
                    String password = dis.readUTF();

                    if (user.search_users(input_user, password))
                    {
                        dos.writeUTF("Log in successful");
                        return true;
                    }
                    if (count == 0)
                    {
                        dos.writeUTF("You have reached the attempted limit. Please contact the support!");
                        exit(1);
                    }
                }
            }
            else if(answer.equals("No"))
            {
                return false;
            }
            else
            {
                dos.writeUTF("invalid input.");
                attempt--;
                if(attempt < 0)
                {
                    dos.writeUTF("Attempt existed limit. Please reopen the program");
                    exit(1);
                }
            }
        }
    }
    public boolean is_create_users(DataOutputStream dos, DataInputStream dis) throws IOException
    {
        user.create_users(dos, dis);
        return true;
    }
    public void users_pick(DataOutputStream dos, DataInputStream dis) throws IOException
    {
        while(true)
        {
            dos.writeUTF("""
                    1. Upload files
                    2. Download files
                    3. Change password
                    4. Quit
                    
                    Please select your option:
                    """);
            String users_option = dis.readUTF();
            switch (users_option) {
                case "1" -> Senders_options(dos, dis);
                case "2" -> Receivers_options();
                case "3" -> Change_pass_interface();
                case "4" -> System.exit(1);
                default -> dos.writeUTF("\nInvalid input.");
            }
        }
    }
    public void Senders_options(DataOutputStream dos, DataInputStream dis) throws IOException
    {
        dos.writeUTF("""
                    1. Upload
                    2. Back
                    3. Exit
                    
                    Please select your option:
                    """);
        String users_option = dis.readUTF();
        switch (users_option)
        {
            case "1":
                break;
            case "2": users_pick(dos, dis);
                break;
            case "3":
                System.exit(1);
        }
        //will have upload
        //will have done (then receive the public key)
    }
    public void Receivers_options()
    {
        System.out.println("Please enter your public key: ");
        Scanner input = new Scanner(System.in);
        String key = input.nextLine();
        if(keys.public_key_existed(key))
        {
            String[] files;
            File folder = new File(keys.getTemp_path());
            files = folder.list();
            assert files != null;
            for(String file : files)
            {
                System.out.println(file);
            }
            //show list of uploaded files
            //download all option
            //download single items options
        }
    }
    public void Change_pass_interface()
    {

    }
}

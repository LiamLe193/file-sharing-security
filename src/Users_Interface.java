import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import static java.lang.System.exit;

public class Users_Interface
{
    Keys keys = new Keys();
    String path = "C:\\Users\\thien\\IdeaProjects\\File_Share_security\\Files";
    Users user = new Users();
    public void Welcome_message()
    {
        System.out.println("Welcome to secure file transfer!");
        System.out.println("\nAbout us:");
        System.out.println("We help users share important files each others.");

    }
    public boolean is_member() throws FileNotFoundException {
        Scanner input = new Scanner(System.in);
        int attempt = 1;
        while(true)
        {
            System.out.println("Are you a member? Yes/No ");
            String answer = input.nextLine();
            if(answer.equals("Yes"))
            {
                for (int count = 3; count >= 0; count--)
                {
                    System.out.println("Please sign in.");
                    System.out.print("Username: ");
                    String input_user = input.nextLine();
                    System.out.print("Password: ");
                    String password = input.nextLine();
                    if (user.search_users(input_user, password))
                    {
                        System.out.println("Log in successful");
                        return true;
                    }
                    if (count == 0)
                    {
                        System.out.println("You have reached the attempted limit. Please contact the support!");
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
                System.out.println("invalid input.");
                attempt--;
                if(attempt < 0)
                {
                    System.out.println("Attempt existed limit. Please reopen the program");
                    exit(1);
                }
            }
        }
    }
    public boolean is_create_users() throws IOException
    {
        System.out.println("type 1 to exit.");
        user.create_users();
        return true;
    }
    public void users_pick()
    {
        while(true)
        {
            System.out.println("1. Upload files");
            System.out.println("2. Download files");
            System.out.println("3. Change password");
            System.out.println("4. Quit");
            System.out.print("\n Please select your option: ");
            Scanner input = new Scanner(System.in);
            String users_option = input.nextLine();
            switch (users_option)
            {
                case "1": Senders_options();
                case "2": Receivers_options();
                case "3": Change_pass_interface();
                case "4": System.exit(1);
                default: System.out.println("\nInvalid input.");
            }
        }

    }
    public void Senders_options()
    {

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

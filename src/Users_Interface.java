import java.util.Scanner;

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
            //show list of uploaded files
            //download all option
            //download single items options
        }
    }
    public void Change_pass_interface()
    {

    }
}

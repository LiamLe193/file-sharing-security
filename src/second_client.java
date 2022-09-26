import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class second_client extends JFrame{
    // driver function

    private final JLabel Username = new JLabel("Enter username: ");
    private final JLabel Password = new JLabel("Enter password: ");
    private final JLabel desire_Username = new JLabel("Enter your desire username: ");
    private final JLabel Your_Current_Username = new JLabel("Enter your current username: ");
    private final JLabel Your_current_password = new JLabel("Enter your current password: ");
    private final JLabel Your_new_password = new JLabel("Enter your new password: ");
    private final JLabel desire_password = new JLabel("Enter your desire password: ");
    private final JLabel re_typed_password = new JLabel("Re-type your password: ");
    private final JLabel Success_Register = new JLabel("User Created Successfully!");

    private final JLabel Not_Done_1 = new JLabel("Not Done");
    private final JLabel Not_Done_2 = new JLabel("Not Done");
    private final JLabel Not_Done_3 = new JLabel("Not Done");
    private final JLabel On_Progress = new JLabel("On_Progress");

    private final JTextField text_SU_Username = new JTextField(20);
    private final JPasswordField field_SU_Password = new JPasswordField(20);
    private final JPasswordField field_re_typed_Password = new JPasswordField(20);
    private final JTextField field_Current_username = new JTextField(20);
    private final JPasswordField field_Current_password = new JPasswordField(20);
    private final JPasswordField field_new_password = new JPasswordField(20);
    private final JTextField text_Username = new JTextField(20);
    private final JPasswordField field_Password = new JPasswordField(20);
    private final JButton button_Login = new JButton("Login");
    private final JButton button_Back_1 = new JButton("Back");
    private final JButton button_Back_2 = new JButton("Back");
    private final JButton button_Back_3 = new JButton("Back");
    private final JButton button_Back_5 = new JButton("Back");
    private final JButton button_Back_4 = new JButton("Great!");
    private JButton buttonRegister, buttonSign_in, button_About, buttonExit, Submit;
    private JButton button_Upload_files, button_Download_files, button_Look_Up, button_Change_Password, button_Exit_2, change;
    JSeparator sep = new JSeparator(JSeparator.VERTICAL);
    BufferedImage Image = ImageIO.read(new File("C:\\Users\\thien\\IdeaProjects\\Testing\\src\\Img.png"));
    JPanel main_Panel;
    String current_ID;
    private final Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;
    public second_client() throws IOException {
        super("Secure Client");

        socket = new Socket("192.168.1.141", 80);
        dos = new DataOutputStream(socket.getOutputStream());
        dis = new DataInputStream(socket.getInputStream());

        CardLayout main_card = new CardLayout();
        main_Panel = new JPanel(main_card);
        //main_Panel.setLayout(main_card);

        JPanel main_Menu = main_menu();
        JPanel Sign_in_Panel = Sign_in_screen();
        JPanel About_Panel = About_Panel();
        JPanel Sign_up_Panel = Sign_up_screen();
        JPanel Success_Register = Register_Success();
        JPanel Success_Log_in = Success_Log_In();
        JPanel Change_password = Change_password();

        main_Panel.add(main_Menu, "Menu");
        main_Panel.add(Sign_up_Panel, "Sign_up");
        main_Panel.add(Sign_in_Panel,"Log_in");
        main_Panel.add(About_Panel, "About us");
        main_Panel.add(Success_Register,"Success");
        main_Panel.add(Success_Log_in, "Logged_in");
        main_Panel.add(Change_password,"Change_pass");

        //Set up button back end
        buttonRegister.addActionListener(e -> main_card.show(main_Panel, "Sign_up"));
        button_Upload_files.addActionListener(e -> {});
        button_Change_Password.addActionListener(e-> main_card.show(main_Panel,"Change_pass"));
        change.addActionListener(e -> {
            String type = "Change";
            String ID = field_Current_username.getText();
            String password = new String(field_Current_password.getPassword());
            String new_password = new String(field_new_password.getPassword());
            if(check_passwords_rules(new_password))
            {
                try {
                    dos = new DataOutputStream(socket.getOutputStream());
                    dis = new DataInputStream(socket.getInputStream());
                    dos.writeUTF(type);
                    dos.writeUTF(ID);
                    dos.writeUTF(password);
                    dos.writeUTF(new_password);
                    String respond = dis.readUTF();
                    if(respond.equals("Done"))
                    {
                        JOptionPane.showMessageDialog(null, "Succeed! Please sign in again!");
                        main_card.show(main_Panel, "Menu");
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, respond);
                        main_card.show(main_Panel, "Logged_in");
                    }
                    field_Current_username.setText("");
                    field_Current_password.setText("");
                    field_new_password.setText("");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            else{
                JOptionPane.showMessageDialog(null, """
                            Your password must contain:
                            Uppercase letters.
                            Lowercase letters.
                            Numbers.
                            From 6 to 20 characters.
                            """);

            }
        });
        button_Download_files.addActionListener(e->{});
        button_Look_Up.addActionListener(e -> {});
        button_Exit_2.addActionListener(e -> {
                    try {
                        dos.close();
                        dis.close();
                        socket.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    System.exit(0);
                });
        button_Login.addActionListener(e -> {
            String type = "Log_In";
            String ID = text_Username.getText();
            String password = new String(field_Password.getPassword());
            try {
                dos = new DataOutputStream(socket.getOutputStream());
                dis = new DataInputStream(socket.getInputStream());
                dos.writeUTF(type);
                dos.writeUTF(ID);
                dos.writeUTF(password);
                String respond = dis.readUTF();
                if(respond.equals("Existed"))
                {
                    main_card.show(main_Panel, "Logged_in");
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Your username is not existed!");
                }
                text_Username.setText("");
                field_Password.setText("");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        buttonSign_in.addActionListener(e -> main_card.show(main_Panel, "Log_in"));
        button_About.addActionListener(e -> main_card.show(main_Panel,"About us"));
        button_Back_1.addActionListener(e -> main_card.show(main_Panel,"Menu"));
        button_Back_2.addActionListener(e -> main_card.show(main_Panel,"Menu"));
        button_Back_3.addActionListener(e -> main_card.show(main_Panel,"Menu"));
        button_Back_4.addActionListener(e -> main_card.show(main_Panel,"Menu"));
        button_Back_5.addActionListener(e -> main_card.show(main_Panel,"Logged_in"));
        buttonExit.addActionListener(e -> {
            try {
                dos.close();
                dis.close();
                socket.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            System.exit(0);
        });
        Submit.addActionListener(e -> {
            if(check_id_rules(text_SU_Username.getText()))
            {
                String password = new String(field_SU_Password.getPassword());
                if(check_passwords_rules(password))
                {
                    String re_typed = new String(field_re_typed_Password.getPassword());
                    if(password.equals(re_typed))
                    {
                        String type = "Register";
                        current_ID = text_SU_Username.getText();
                        try {
                            dos = new DataOutputStream(socket.getOutputStream());
                            dis = new DataInputStream(socket.getInputStream());
                            dos.writeUTF(type);
                            dos.writeUTF(current_ID);
                            dos.writeUTF(password);
                            if(dis.readUTF().equals("Existed"))
                            {
                                JOptionPane.showMessageDialog(null, "Your username is already existed!");
                            }
                            else main_card.show(main_Panel,"Success");
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"The password does not match");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, """
                            Your password must contain:
                            Uppercase letters.
                            Lowercase letters.
                            Numbers.
                            From 6 to 20 characters.
                            """);

                }
            }
            else
            {
                JOptionPane.showMessageDialog(null,"""
                        Username must contain at least:
                        Uppercase letters.
                        Lowercase letters.
                        Numbers.
                        From 6 to 14 characters.
                        """);
            }
            text_SU_Username.setText("");
            field_SU_Password.setText("");
            field_re_typed_Password.setText("");
        });

        //put everything into main_Panel
        getContentPane().add(main_Panel);
    }

    private JPanel main_menu()
    {
        JPanel Menu_Panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        constraints.fill = GridBagConstraints.WEST;
        constraints.gridx = 0;
        constraints.gridy = 0;
        buttonRegister = new JButton("Sign up");
        Menu_Panel.add(buttonRegister, constraints);

        constraints.fill = GridBagConstraints.WEST;
        constraints.gridx = 0;
        constraints.gridy = 1;
        buttonSign_in = new JButton("Sign in");
        Menu_Panel.add(buttonSign_in, constraints);

        constraints.fill = GridBagConstraints.WEST;
        constraints.gridx = 0;
        constraints.gridy = 2;
        button_About = new JButton("About");
        Menu_Panel.add(button_About, constraints);

        constraints.fill = GridBagConstraints.WEST;
        constraints.gridx = 0;
        constraints.gridy = 3;
        buttonExit = new JButton("Exit");
        Menu_Panel.add(buttonExit, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridheight = 4;
        sep.setPreferredSize(new Dimension(1,200));
        Menu_Panel.add(sep, constraints);

        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.gridheight = 4;
        JLabel intro_Image = new JLabel(new ImageIcon(Image));
        Menu_Panel.add(intro_Image, constraints);


        String title = "Welcome to Secure File Transfer";
        TitledBorder titledBorder = BorderFactory.createTitledBorder(title);
        titledBorder.setTitleJustification(TitledBorder.CENTER);
        Menu_Panel.setBorder(titledBorder);

        return Menu_Panel;
    }
    private JPanel Sign_up_screen()
    {
        JPanel Sign_up_Panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(11, 11, 11, 11);
        constraints.gridx = 0;
        constraints.gridy = 0;
        Sign_up_Panel.add(desire_Username, constraints);

        constraints.gridx = 1;
        Sign_up_Panel.add(text_SU_Username, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        Sign_up_Panel.add(desire_password, constraints);

        constraints.gridx = 1;
        Sign_up_Panel.add(field_SU_Password, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        Sign_up_Panel.add(re_typed_password, constraints);

        constraints.gridx = 1;
        Sign_up_Panel.add(field_re_typed_Password, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.anchor = GridBagConstraints.CENTER;
        Sign_up_Panel.add(button_Back_3, constraints);

        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.anchor = GridBagConstraints.CENTER;
        Submit = new JButton("Submit");
        Sign_up_Panel.add(Submit, constraints);

        String title = "Register Panel";
        TitledBorder titledBorder = BorderFactory.createTitledBorder(title);
        titledBorder.setTitleJustification(TitledBorder.LEFT);
        Sign_up_Panel.setBorder(titledBorder);

        return Sign_up_Panel;
    }
    private JPanel Sign_in_screen()
    {
        JPanel Sign_in_Panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(8, 8, 8, 8);

        constraints.gridx = 0;
        constraints.gridy = 0;
        Sign_in_Panel.add(Username, constraints);

        constraints.gridx = 1;
        Sign_in_Panel.add(text_Username, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        Sign_in_Panel.add(Password, constraints);

        constraints.gridx = 1;
        Sign_in_Panel.add(field_Password, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        Sign_in_Panel.add(button_Login, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.anchor = GridBagConstraints.CENTER;
        Sign_in_Panel.add(button_Back_1, constraints);

        Sign_in_Panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Log_in Panel"));

        return Sign_in_Panel;
    }
    private JPanel About_Panel()
    {
        JPanel about_Panel = new JPanel();
        String text = """
                        My name is Liam Le.

                        This is my personal project.

                        The purpose of this program is
                        to prevent private data leaks by other parties.

                        This program help users exchange files through a
                        security server.

                        The data of the users is kept by server and encrypted.

                      """;
        JTextPane pane = new JTextPane();
        pane.setText(text);
        about_Panel.add(pane);
        about_Panel.add(button_Back_2);
        String title = "About Panel";
        TitledBorder titledBorder = BorderFactory.createTitledBorder(title);
        titledBorder.setTitleJustification(TitledBorder.LEFT);
        about_Panel.setBorder(titledBorder);

        return about_Panel;
    }
    private JPanel Register_Success()
    {
        JPanel Success_Panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(11, 11, 11, 11);

        constraints.gridx = 0;
        constraints.gridy = 0;
        Success_Panel.add(Success_Register, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        Success_Panel.add(button_Back_4,constraints);
        return Success_Panel;
    }
    private JPanel Success_Log_In() {
        JPanel Success_Log_in = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(8, 8, 8, 8);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.CENTER;
        button_Upload_files = new JButton("Upload");
        Success_Log_in.add(button_Upload_files, constraints);

        constraints.gridx = 1;
        Success_Log_in.add(Not_Done_1, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        button_Download_files = new JButton("Download");
        Success_Log_in.add(button_Download_files, constraints);

        constraints.gridx = 1;
        Success_Log_in.add(Not_Done_2, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        button_Change_Password = new JButton("Change password");
        Success_Log_in.add(button_Change_Password, constraints);

        constraints.gridx = 1;
        Success_Log_in.add(On_Progress, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.anchor = GridBagConstraints.CENTER;
        button_Look_Up = new JButton("Look Up");
        Success_Log_in.add(button_Look_Up, constraints);

        constraints.gridx = 1;
        Success_Log_in.add(Not_Done_3, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.anchor = GridBagConstraints.CENTER;
        button_Exit_2 = new JButton("Exit");
        Success_Log_in.add(button_Exit_2, constraints);

        String title = "Hello, Client";
        TitledBorder titledBorder = BorderFactory.createTitledBorder(title);
        titledBorder.setTitleJustification(TitledBorder.LEFT);
        Success_Log_in.setBorder(titledBorder);
        return Success_Log_in;
    }
    private JPanel Change_password()
    {
        JPanel change_password_Panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(11, 11, 11, 11);
        constraints.gridx = 0;
        constraints.gridy = 0;
        change_password_Panel.add(Your_Current_Username, constraints);

        constraints.gridx = 1;
        change_password_Panel.add(field_Current_username, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        change_password_Panel.add(Your_current_password, constraints);

        constraints.gridx = 1;
        change_password_Panel.add(field_Current_password, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        change_password_Panel.add(Your_new_password, constraints);

        constraints.gridx = 1;
        change_password_Panel.add(field_new_password, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.anchor = GridBagConstraints.CENTER;
        change_password_Panel.add(button_Back_5, constraints);

        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.anchor = GridBagConstraints.CENTER;
        change = new JButton("Change");
        change_password_Panel.add(change, constraints);

        String title = "Change Password Panel";
        TitledBorder titledBorder = BorderFactory.createTitledBorder(title);
        titledBorder.setTitleJustification(TitledBorder.LEFT);
        change_password_Panel.setBorder(titledBorder);

        return change_password_Panel;
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
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=\\S+$).{6,20}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);
        return m.matches();
    }

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            try {
                client client = new client();
                client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                client.pack();
                client.setLocationRelativeTo(null);
                client.setVisible(true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
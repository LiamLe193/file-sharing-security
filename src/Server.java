import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.System.exit;

public class Server {
    public final static String file_path = "c:/temp/source.pdf";  // change this

    public static void main(String[] args) throws IOException {
        //classes variable
        Users user = new Users();
        Hashing hash = new Hashing();
        //end
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        OutputStream os = null;
        Socket sock = null;


        System.out.println("Server Start...");
        while(true)
        {
            try (ServerSocket server_sock = new ServerSocket(80))
            {
                sock = server_sock.accept();
                System.out.println("Client connected...");
                DataInputStream dis = new DataInputStream(sock.getInputStream());
                DataOutputStream dos = new DataOutputStream(sock.getOutputStream());
                Thread t = new ClientHandler(sock, dis, dos, user, hash);
                t.start();
            }
        }
    }
}
    class ClientHandler extends Thread {
        final DataInputStream dis;
        final DataOutputStream dos;
        final Socket sock;
        final Users user;
        final Hashing hash;
        public ClientHandler(Socket sock, DataInputStream dis, DataOutputStream dos, Users user, Hashing hash) {
            this.sock = sock;
            this.dis = dis;
            this.dos = dos;
            this.user = user;
            this.hash = hash;
        }

        @Override
        public void run() {
            while(sock.isConnected())
            {
                try {
                    String type = dis.readUTF();
                    if (type.equals("Register"))
                    {
                        user.create_users(dos, dis);
                    } else if (type.equals("Log_In"))
                    {
                        String ID = dis.readUTF();
                        String password = dis.readUTF();
                        ID = hash.hash_combine(ID);
                        password = hash.hash_combine(password);
                        if (user.search_users(ID, password))
                        {
                            dos.writeUTF("Existed");
                            int run = 1;
                            while(run == 1)
                            {
                                String option = dis.readUTF();
                                switch(option)
                                {
                                    case "Change":
                                        String input_ID = dis.readUTF();
                                        String input_password = dis.readUTF();
                                        String input_new_password = dis.readUTF();
                                        input_ID = hash.hash_combine(input_ID);
                                        input_password = hash.hash_combine(input_password);
                                        input_new_password = hash.hash_combine(input_new_password);
                                        if(user.change_password(input_ID,input_new_password,input_password))
                                        {
                                            dos.writeUTF("Done");
                                        }
                                        else {
                                            dos.writeUTF("Wrong username or password!");
                                            run = 2;
                                        }
                                        break;
                                    case "Upload":
                                    case "Download":
                                    case "LookUp":
                                        break;
                                }
                            }
                            //break;
                        } else dos.writeUTF("No");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
            try {
                // closing resources
                dos.close();
                dis.close();
                sock.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
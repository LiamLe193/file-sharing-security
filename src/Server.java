import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.System.exit;

public class Server {
    public final static String file_path = "c:/temp/source.pdf";  // change this

    public static void main(String[] args) throws IOException {
        //classes variable
        Users_Interface ui = new Users_Interface();
        Users user = new Users();

        //end
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        OutputStream os = null;
        Socket sock = null;

        //should let the client connect first then appear the UI
        System.out.println("Server Start...");
        try (ServerSocket server_sock = new ServerSocket(80)) {
            sock = server_sock.accept();
            System.out.println("Client connected...");
            DataInputStream dis = new DataInputStream(sock.getInputStream());
            DataOutputStream dos = new DataOutputStream(sock.getOutputStream());
            while (true) {
                try {
                    //check if member then sign in
                    int attempt = 1;
                    String type = dis.readUTF();
                    if (type.equals("Register"))
                    {
                        user.create_users(dos, dis);
                    }
                    else if (type.equals("Log_In"))
                    {
                        String ID = dis.readUTF();
                        String password = dis.readUTF();
                        if (user.search_users(ID, password))
                        {
                            dos.writeUTF("Exist");
                        } else dos.writeUTF("No");
                    }
                    System.out.println("Accepted connection : " + sock);
                    //user interface picks

                    ui.users_pick(dos, dis);
                    // send file
                    File myFile = new File(file_path);
                    byte[] mybytearray = new byte[(int) myFile.length()];
                    fis = new FileInputStream(myFile);
                    bis = new BufferedInputStream(fis);
                    bis.read(mybytearray, 0, mybytearray.length);
                    os = sock.getOutputStream();
                    System.out.println("Sending " + file_path + "(" + mybytearray.length + " bytes)");
                    os.write(mybytearray, 0, mybytearray.length);
                    os.flush();
                    System.out.println("Done.");
                } finally {
                    if (bis != null) bis.close();
                    if (os != null) os.close();
                    sock.close();
                }

            }
        }
    }
}
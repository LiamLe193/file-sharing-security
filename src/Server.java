import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.System.exit;

public class Server
{
    public final static int SOCKET_PORT = 1234;  // can be changed
    public final static String file_path = "c:/temp/source.pdf";  // you may change this

    public static void main (String [] args ) throws IOException
    {
        //classes variable
        Users_Interface ui = new Users_Interface();
        Users user = new Users();

        //end
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        OutputStream os = null;
        Socket sock = null;

        //should let the client connect first then appear the UI
        try (ServerSocket server_sock = new ServerSocket(SOCKET_PORT)) {

            while (true) {
                ui.Welcome_message();
                try {
                    //check if member then sign in
                    int attempt = 1;
                    while(!ui.is_member())
                    {
                        {
                            if(ui.is_create_users())
                            {
                                System.out.println("Please Sign in again");
                                attempt--;
                                if(attempt < 0)
                                {
                                    System.out.println("Attempts exist limits. Terminate the program now");
                                    exit(1);
                                }
                            }
                            else {
                                System.out.println("unexpected error! Terminate the program now");
                                exit(1);
                            }
                        }
                    }
                    System.out.println("Accepted connection : " + sock);
                    //user interface picks
                    ui.users_pick();
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
                    if (sock != null) sock.close();
                }
            }
        }
    }
}

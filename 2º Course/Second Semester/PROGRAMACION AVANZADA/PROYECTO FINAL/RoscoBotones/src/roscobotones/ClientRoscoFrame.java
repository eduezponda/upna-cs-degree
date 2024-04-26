package roscobotones;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ClientRoscoFrame {
    private static Socket socket;
    private static DataInputStream in;
    private static DataOutputStream out;
    
    public static void main(String[] args) throws IOException {
        final String hostAddr = "127.0.0.1";
        final int port = 12000;
        socket = new Socket(hostAddr, port);

        System.out.println("Socket connected to " + socket.getInetAddress() + ":" + socket.getPort());

        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());

        if(!receiveMessage("PLAY")){
            System.out.println("The servidor cannot start the game\n");
            System.exit(0);
        }
        
        RoscoFrame roscoFrame = new RoscoFrame();
        roscoFrame.addWindowListener(new WindowAdapter() {
        @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("El programa se ha cerrado.");
                try {
                    out.writeUTF("GAME NOT FINISHED");
                } catch (IOException ex) {
                    Logger.getLogger(ClientRoscoFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    socket.close();
                } catch (IOException ex) {
                    Logger.getLogger(ClientRoscoFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    public static boolean receiveMessage(String message) throws IOException {
        String recv = in.readUTF();
        return recv.equals(message);
    }

    public static void sendMessage (String message) throws IOException{
        out.writeUTF(message);
    }
    public static void receiveMessageFinal() throws IOException, InterruptedException {
        String gameResult = in.readUTF();
        String correctOponent = in.readUTF();
        System.out.println(gameResult);
        System.out.println("Your opponent has answered " + correctOponent + " correct questions");
        Thread.sleep(5000);
        System.exit(0);
    }
    
}

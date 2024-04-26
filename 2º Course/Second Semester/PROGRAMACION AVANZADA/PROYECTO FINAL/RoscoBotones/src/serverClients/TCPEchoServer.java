package serverClients;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class TCPEchoServer {
    
    private static final List<ClientInfo> clientes = new ArrayList<>();

    public static void main(String[] args) throws IOException{
        final int port = 12000;
        final ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Started server on " + port);

        for(;;){
            try( Socket clientSocket = serverSocket.accept()){ 
                System.out.println("Accepted connection from "
                        + clientSocket.getInetAddress() + ":" + clientSocket.getPort());

                DataInputStream in = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
                ClientInfo client = new ClientInfo(clientSocket, in, out);
                clientes.add(client);
                
                Socket clientSocket2 = serverSocket.accept();
                System.out.println("Accepted connection from "
                        + clientSocket2.getInetAddress() + ":" + clientSocket2.getPort());
                DataInputStream in2 = new DataInputStream(clientSocket2.getInputStream());
                DataOutputStream out2 = new DataOutputStream(clientSocket2.getOutputStream());
                ClientInfo client2 = new ClientInfo(clientSocket, in2, out2);
                clientes.add(client2);

                for (ClientInfo cliente : clientes){
                    cliente.getOut().writeUTF("PLAY");
                }
                String mensajeCliente1 = "";
                String mensajeCliente2 = "";
                
                while (mensajeCliente1.equals("") || mensajeCliente2.equals("")) {
                    for (ClientInfo cliente : clientes) {
                        
                        if (cliente.getIn().available() > 0) {
                            String mensaje = cliente.getIn().readUTF();
                            System.out.println(mensaje);
                            if (cliente.equals(client)) {
                                mensajeCliente1 = mensaje;
                            } else if (cliente.equals(client2)) {
                                mensajeCliente2 = mensaje;
                            }
                        }
                    }
                }
                if (mensajeCliente1.equals("GAME NOT FINISHED") 
                    && mensajeCliente2.equals("GAME NOT FINISHED")){
                    
                    System.exit(0);    
                    
                }
                else if (mensajeCliente1.equals("GAME NOT FINISHED")){
                    clientes.get(1).getOut().writeUTF("VICTORY");
                }
                else if (mensajeCliente2.equals("GAME NOT FINISHED")){
                    clientes.get(0).getOut().writeUTF("VICTORY");
                }
                else{
                    String numberString1 = mensajeCliente1.substring(mensajeCliente1.indexOf(":") + 1)
                                            .trim();
                    int numero1 = Integer.parseInt(numberString1);
                    String numberString2 = mensajeCliente2.substring(mensajeCliente2.indexOf(":") + 1)
                                            .trim();
                    int numero2 = Integer.parseInt(numberString2);
                    
                    if (numero1 > numero2){
                        clientes.get(0).getOut().writeUTF("VICTORY");
                        clientes.get(1).getOut().writeUTF("DEFEAT");
                    }
                    else if (numero1 < numero2){
                        clientes.get(0).getOut().writeUTF("DEFEAT");
                        clientes.get(1).getOut().writeUTF("VICTORY");
                    }
                    else{
                        clientes.get(0).getOut().writeUTF("TIE");
                        clientes.get(1).getOut().writeUTF("TIE");
                    }
                    clientes.get(0).getOut().writeUTF(Integer.toString(numero2));
                    clientes.get(1).getOut().writeUTF(Integer.toString(numero1));
                }
                                        
                clientes.get(0).getSocket().close();
                clientes.get(1).getSocket().close();
                
                System.out.println("GAME FINISHED");
                clientes.forEach(cliente -> {
                    System.out.println("Connection lost with "
                            + cliente.getSocket().getInetAddress() + ":" 
                            + cliente.getSocket().getPort());
                });
                
                System.exit(0);
                
            }catch (SocketException se){
                System.out.println("Connection lost: " + se.getMessage());
            }
            
        }
    }
    public static void sendMessage(DataOutputStream out, String message) throws IOException{
        out.writeUTF(message);
    }
}
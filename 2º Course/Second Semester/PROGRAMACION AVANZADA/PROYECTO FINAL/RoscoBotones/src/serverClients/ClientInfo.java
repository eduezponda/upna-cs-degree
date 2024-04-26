package serverClients;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;


public class ClientInfo {
    private final Socket socket;
    private final DataInputStream in;
    private final DataOutputStream out;
    
    public ClientInfo(Socket socket, DataInputStream in, DataOutputStream out) {
        this.socket = socket;
        this.in = in;
        this.out = out;
    }

    public DataInputStream getIn() {
        return in;
    }

    public DataOutputStream getOut() {
        return out;
    }
    
    public Socket getSocket() {
        return socket;
    }

}


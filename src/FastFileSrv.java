import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class FastFileSrv {
    private int port;
    private Map<String, Map<String, Handler>> handlers = new HashMap<String, Map<String, Handler>>();
    private String s;

    public FastFileSrv(int port, Map<String, Map<String, Handler>> h, String s){
        this.port = port;
        this.handlers=h;
        this.s = s;
    }

    public void start(String s) throws IOException, InterruptedException {
        ServerSocket socket = new ServerSocket(port);
        System.out.println("Listening on port " + port);
        Socket client = new Socket(InetAddress.getLocalHost(),port);
        client = socket.accept();
        if (client != null)  {
            System.out.println("Received connection from " + client.getRemoteSocketAddress().toString());
            SocketHandler handler = new SocketHandler(client, handlers,s);
            Thread t = new Thread(handler);
            t.start();
            t.join();
        }
        // client.close();
        socket.close();
    }
}

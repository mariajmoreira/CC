import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpGw  {
    private int port;
    private Handler defaultHandler = null;
    // Two level map: first level is HTTP Method (GET, POST, OPTION, etc.), second level is the
    // request paths.
    private Map<String, Map<String, Handler>> handlers = new HashMap<String, Map<String, Handler>>();

    // TODO SSL support
    public HttpGw(int port)  {
        this.port = port;
    }

    /**
     * @param path if this is the special string "/*", this is the default handler if
     *   no other handler matches.
     */
    public void addHandler(String method, String path, Handler handler)  {
        Map<String, Handler> methodHandlers = handlers.get(method);
        if (methodHandlers == null)  {
            methodHandlers = new HashMap<String, Handler>();
            handlers.put(method, methodHandlers);
        }
        methodHandlers.put(path, handler);
    }

    public void run()
    {
        System.out.println("Thread is running...");
    }

    public void start() throws IOException {
        ServerSocket socket = new ServerSocket(port);
        System.out.println("Listening on port " + port);
        Socket client = new Socket(InetAddress.getLocalHost(),port);
        //Socket client = new Socket(InetAddress.getByName("MacBook-Air-de-Maria-2.local"),port);
        //Socket client = new Socket();
        System.out.println(client.toString());
        client = socket.accept();
        if (client != null)  {
            System.out.println("Received connection from " + client.getRemoteSocketAddress().toString());
            SocketHandler handler = new SocketHandler(client, handlers);
            Thread t = new Thread(handler);
            t.start();
        }
    }


    public static void main(String[] args) throws IOException  {
        HttpGw server = new HttpGw(8080);
        server.addHandler("GET", "https://www4.di.uminho.pt/~fln/JavaAPI/java.io.FileInputStream.html", new Handler()  {
            public void handle(Request request, Response response) throws IOException  {
                //String html = "It works, " + request.getParameter("name") + "";
                String html = "Funciona, Zé";
               // System.out.println(html);
                response.setResponseCode(200, "OK");
                System.out.println();
                //System.out.println("resposta criada");

                response.addHeader("Content-Type", "text/html");
                //System.out.println("header adicionado à resposta");

                response.addBody(html);
                //System.out.println("corpo adicionado à resposta");

                System.out.println();
                System.out.println(response.toString());
                System.out.println();
            }
        });
        server.addHandler("GET", "/*", new FileHandler());  // Default handler
        server.start();
    }
}

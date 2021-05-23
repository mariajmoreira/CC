import javax.management.AttributeList;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class HttpGw  {
    private int port;
    private Handler defaultHandler = null;
    // Two level map: first level is HTTP Method (GET, POST, OPTION, etc.), second level is the
    // request paths.
    private Map<String, Map<String, Handler>> handlers = new HashMap<String, Map<String, Handler>>();
    private Request request;

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


    public void start() throws IOException, InterruptedException {
        ServerSocket socket = new ServerSocket(port);
        System.out.println("Listening on port " + port);
        Socket client = new Socket(InetAddress.getLocalHost(),port);
        client = socket.accept();
        if (client != null)  {
            System.out.println("Received connection from " + client.getRemoteSocketAddress().toString());
            SocketHandler handler = new SocketHandler(client, handlers, request);
            Thread t = new Thread(handler);
            t.start();
            t.join();
        }
    }


    /*
    public static void main(String[] args) throws IOException  {
        HttpGw server = new HttpGw(8080);
        String path = "/Users/mariajoao/Desktop/PL/TP1/Final/Atleta93.html";
       //String path = "https://www4.di.uminho.pt/~fln/JavaAPI/java.io.FileInputStream.html";
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        Request pedido = new Request(in);
        server.addHandler("GET", path, new Handler()  {
            public void handle(Request request, Response response) throws IOException  {
               response.readFile(path);
               try {
                    response.URLreader(path);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                response.setResponseCode(200, "OK");
                System.out.println();

                response.addHeader("Content-Type", "text/html");

                System.out.println();
                System.out.println(response.toString());
                System.out.println();
            }
        });
        server.addHandler("GET", "/*", new FileHandler());  // Default handler
        server.start();
    }
    */


    public static void menu() throws IOException, InterruptedException {
        HttpGw server = new HttpGw(80);
        ArrayList<String> opcoes = new ArrayList<>();
        String option;
        option = "Fazer pedido";
        opcoes.add(option);
        int op = 1;
        Menu menu = new Menu(new String[]{"Fazer Pedido"});
        //Scanner is = new Scanner(System.in);
        boolean running = true;
        Scanner input = new Scanner(System.in);
        String line = null;
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        while(op!=0) {
            
            System.out.println(op);
            switch (op) {
                case 1:
                    server.start();
                    System.out.println("pedido realizado");
                    break;
                case 0:
                    System.out.println("programa encerrado");
                    break;
            }
            input.reset();
        }
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        menu();

        //server.start();

        //GET /Users/mariajoao/Desktop/PL/TP1/Final/Atleta93.html HTTP/1.1
    }
}

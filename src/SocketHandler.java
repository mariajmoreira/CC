import org.xml.sax.helpers.DefaultHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.net.Socket;
import java.io.IOException;


public class SocketHandler implements Runnable {
        private Socket socket;
        //private Handler defaultHandler;
        private Map<String, Map<String, Handler>> handlers;
        private Request request;
        //private BufferedReader in = new BufferedReader(new FileReader("/Users/mariajoao/Desktop/GETFILES/input.txt"));
        private String s;

    public SocketHandler(Socket socket,
                             Map<String, Map<String, Handler>> handlers, Request request, String s) throws FileNotFoundException {
            this.socket = socket;
            this.handlers = handlers;
            this.request = request;
            this.s=s;
        }

        /**
         * Simple responses like errors.  Normal reponses come from handlers.
         */
        private void respond(int statusCode, String msg, OutputStream out) throws IOException {
            String responseLine = "HTTP/1.1 " + statusCode + " " + msg + "\r\n\r\n";
            log(responseLine);
            out.write(responseLine.getBytes());
        }


        public void processa_pedido() {
            OutputStream out = null;
            boolean bool = true;
            try {
                StringTokenizer tokenizer = null;
                ArrayList<String> requestToken = new ArrayList<>();
                    //System.out.println("PEDIDO: "+s);
                    tokenizer = new StringTokenizer(s, " ");
                    while (tokenizer.hasMoreTokens()) {
                        String token = tokenizer.nextToken();
                        requestToken.add(token);
                    }

                    //System.out.println("Caminho do Pedido: " + requestToken.get(1));
                    //System.out.println();

                    Request request = new Request(requestToken.get(1));

                    Response response = new Response(out);

                    Handler handler = new FileHandler();
                    handler.handle(request, response);
                    HttpGw server = new HttpGw(8080);
                    server.addHandler("GET", "/*", new FileHandler());
                    server.addHandler("GET", requestToken.get(1), handler);

                    setHandler("GET", requestToken.get(1), handler);
                    setHandler("GET", "/*", new FileHandler());

                    //System.out.println(response.toString());
                    System.out.println();

                    out = socket.getOutputStream();

                    if (request.parse(s) == false) {
                        respond(500, "Unable to parse request", out);
                        System.out.println("unable to parse request");
                        return;
                    } else {
                        System.out.println("able to parse request");
                    }

                    boolean foundHandler = false;
                    Map<String, Handler> methodHandlers = handlers.get(request.getMethod());
                    //System.out.println("handlers: " + handlers);
                    //System.out.println("methodhandlers: " + methodHandlers);
                    if (methodHandlers == null) {
                        respond(405, "Method not supported", out);
                        System.out.println("Method not supported");
                        return;
                    } else {
                        System.out.println("Method supported");
                    }

                    for (String handlerPath : methodHandlers.keySet()) {
                        if (handlerPath != (request.getPath())) {
                            methodHandlers.get(request.getPath()).handle(request, response);
                            response.send();
                            foundHandler = true;
                            break;
                        }
                    }

                    if (foundHandler == false) {
                        if (methodHandlers.get("/*") != null) {
                            methodHandlers.get("/*").handle(request, response);
                            response.send();
                        } else {
                            respond(404, "Not Found", out);
                        }
                    }
            } catch (IOException e) {
                try {
                    e.printStackTrace();
                    if (out != null) {
                        respond(500, e.toString(), out);
                    }
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
            public void menu_pedido() throws IOException {
            String opcao = "0";
            Menu menu = new Menu( new String[] {"Fazer Pedido"} );
            menu.showMenu();
            switch (opcao) {
                case "1":
                    processa_pedido();
                    break;
                case "0":
                    break;
            }
        }

    public void run(){
        processa_pedido();
    }


    private void setHandler(String method, String path, Handler handler) {
        Map<String, Handler> methodHandlers = handlers.get(method);
        if (methodHandlers == null)  {
            methodHandlers = new HashMap<String, Handler>();
            handlers.put(method, methodHandlers);
        }
        methodHandlers.put(path, handler);
    }

    private void log(String msg) {
            System.out.println(msg);
        }
}


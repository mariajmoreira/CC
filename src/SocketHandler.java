import java.io.*;
import java.util.Map;
import java.net.Socket;
import java.io.IOException;


public class SocketHandler implements Runnable {
        private Socket socket;
        private Handler defaultHandler;
        private Map<String, Map<String, Handler>> handlers;

        public SocketHandler(Socket socket,
                             Map<String, Map<String, Handler>> handlers) {
            this.socket = socket;
            this.handlers = handlers;
        }

        /**
         * Simple responses like errors.  Normal reponses come from handlers.
         */
        private void respond(int statusCode, String msg, OutputStream out) throws IOException {
            String responseLine = "HTTP/1.1 " + statusCode + " " + msg + "\r\n\r\n";
            log(responseLine);
            out.write(responseLine.getBytes());
        }

        public void run() {
            BufferedReader in = null;
            //DataInputStream in = null;
            OutputStream out = null;

            try {
                //in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                //in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                in = new BufferedReader(new InputStreamReader(System.in));
                System.out.println();
                System.out.println(in);
                System.out.println(socket.getInputStream());
                System.out.println();
                out = socket.getOutputStream();
                System.out.println(out);

                Request request = new Request(in);
                //System.out.println(request.toString());

                if (request.parse() == false) {
                    respond(500, "Unable to parse request", out);
                    System.out.println("unable to parse request");
                    return;
                }
                else{
                    System.out.println("able to parse request");
                }

                boolean foundHandler = false;
                Response response = new Response(out);
                Map<String, Handler> methodHandlers = handlers.get(request.getMethod());
                System.out.println("handlers: " + handlers);
                System.out.println("methodhandlers: " + methodHandlers);
                if (methodHandlers == null) {
                    respond(405, "Method not supported", out);
                    System.out.println("Method not supported");
                    return;
                }
                else{
                    System.out.println("Method supported");
                }

                for (String handlerPath : methodHandlers.keySet()) {
                    System.out.println();
                    System.out.println(handlerPath);
                    System.out.println(request.getPath());
                    System.out.println();
                    if (handlerPath != (request.getPath())) {
                        methodHandlers.get(request.getPath()).handle(request, response);
                        response.send();
                        foundHandler = true;
                        break;
                    }
                }

                if (foundHandler==false) {
                    if (methodHandlers.get("/*") != null) {
                        System.out.println(methodHandlers.get("/*"));
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
                    // We tried
                }
            } finally {
                try {
                    if (out != null) {
                        //System.out.println("out:" + out);
                        out.close();
                    }
                    if (in != null) {
                        //System.out.println("in:" + in);
                        in.close();
                    }
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void log(String msg) {
            System.out.println(msg);
        }
}


import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.HashMap;

/**
 * Encapsulate an HTTP Response.  Mostly just wrap an output stream and
 * provide some state.
 */
public class Response  {
    private OutputStream out;
    private int statusCode;
    private String statusMessage;
    private Map<String, String> headers = new HashMap<String, String>();
    private String body;

    public Response(OutputStream out)  {
        this.out = out;
    }

    public void setResponseCode(int statusCode, String statusMessage)  {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    public void addHeader(String headerName, String headerValue)  {
        this.headers.put(headerName, headerValue);
    }

    public void addBody(String body)  {
        headers.put("Content-Length", Integer.toString(body.length()));
        this.body = body;
    }

    public void readFile(String path) throws IOException {
        File file = new File(path);
        byte[] bytesArray = new byte[(int) file.length()];
        BufferedReader fis = null;
        Path fileName = Path.of(path);
        String content  = null;
        //Files.writeString(fileName, content);

        String actual = Files.readString(fileName);
        addBody(actual);
    }

    public void URLreader(String path) throws Exception {

        URL url = new URL(path);
        try (var br = new BufferedReader(new InputStreamReader(url.openStream()))) {

            String line;

            var sb = new StringBuilder();

            while ((line = br.readLine()) != null) {

                sb.append(line);
                sb.append(System.lineSeparator());
            }

            addBody(sb.toString());
        }
    }


    public void send() throws IOException  {
        ByteArrayOutputStream o = new ByteArrayOutputStream();
        headers.put("Connection", "Close");
        o.write(("HTTP/1.1 " + statusCode + " " + statusMessage + "\r\n").getBytes());
        //System.out.println(("HTTP/1.1 " + statusCode + " " + statusMessage + "\r\n").getBytes());
        for (String headerName : headers.keySet())  {
            o.write((headerName + ": " + headers.get(headerName) + "\r\n").getBytes());
        }
        o.write("\r\n".getBytes());
        if (body != null)  {
            o.write(body.getBytes());
        }
        String finalString = new String(o.toByteArray());
        System.out.println();
        System.out.println("-> RESPOSTA");
        System.out.println(finalString);
        System.out.println();
        System.out.println("<- RESPOSTA");
        System.out.println();
        out = (OutputStream) o;
        //System.out.println(out);
        addBody(o.toString());
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("-> Resposta:\n");
        s.append("statusCode:" + statusCode + "\n");
        s.append("statusMessage:" + statusMessage + "\n");
        for(String st : headers.keySet()){
            s.append(st + ": " + headers.get(st) + "\n");
        }
        s.append("corpo da mensagem:" + body+ "\n");
        s.append("<-\n");
        return String.valueOf(s);
    }
}

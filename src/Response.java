import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
        System.out.println(finalString);
        out = (OutputStream) o;
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
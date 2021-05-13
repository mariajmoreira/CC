import java.io.DataInputStream;
import java.util.Map;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Request  {
    private String method;
    private String path;
    private String fullUrl;
    private Map<String, String> headers = new HashMap<String, String>();
    private Map<String, String> queryParameters = new HashMap<String, String>();
    private BufferedReader in;
    //private DataInputStream in;

    public Request(BufferedReader in)  {
        this.in = in;
    }

    public String getMethod()  {
        return method;
    }

    public String getPath()  {
        return path;
    }

    public String getFullUrl()  {
        return fullUrl;
    }

    public String getHeader(String headerName)  {
        return headers.get(headerName);
    }

    public String getParameter(String paramName)  {
        return queryParameters.get(paramName);
    }


    private void log(String msg)  {
        System.out.println(msg);
    }

    public String toString()  {
        return method  + " " + path + " " + headers.toString();
    }

    public boolean parse() throws IOException {
        boolean bool = true;

        String initialLine = in.readLine();
        log(initialLine);
        StringTokenizer tok = new StringTokenizer(initialLine," ");
        String[] components = new String[3];
        for (int i = 0; i < 3; i++) {
            if (tok.hasMoreTokens())  {
                components[i] = tok.nextToken();
                System.out.println("InitialLine accepted");
            } else  {
                System.out.println("Illegal initialLine");
                bool = false;
                return bool;
            }
        }
        System.out.println("continua");
        method = components[0];
        fullUrl = components[1];
        // Consume headers
        while (bool == true)  {
            String headerLine = in.readLine();
            log(headerLine);
            if (headerLine.length() == 0) {
                System.out.println("HeaderLine inválida");
                break;
            }

            int separator = headerLine.indexOf(":");
            if (separator == -1)  {
                System.out.println("HeaderLine não tem : ");
                bool = false;
                return bool;
            }
            headers.put(headerLine.substring(0, separator),
                    headerLine.substring(separator + 1));
        }

        if (components[1].indexOf("?") == -1) {
            path = components[1];
            System.out.println("Path:" + path);
        } else  {
            path = components[1].substring(0, components[1].indexOf("?"));
            System.out.println("Path:" + path);
            String subpath = components[1].substring(components[1].indexOf("?") - 1);
            System.out.println(subpath);
            parseQueryParameters(subpath);
        }

        if ("/".equals(path)) {
            path = "/index.html";
            System.out.println("Path:" + path);
        }

        return bool;
    }

    private void parseQueryParameters(String queryString) {
        System.out.println("query:" + queryString);
        for (String parameter : queryString.split("&")) {
            int separator = parameter.indexOf('=');
            if (separator > -1) {
                queryParameters.put(parameter.substring(0, separator),
                        parameter.substring(separator + 1));
            } else  {
                queryParameters.put(parameter, null);
            }
        }
    }
}

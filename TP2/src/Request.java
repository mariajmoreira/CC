import java.nio.ByteBuffer;

public class Request {
    //request line
    private String method;
    private String URL;
    private String version;
    //header lines
    private String header;
    private String value;
    //body
    private String body;

    static byte[] gerarPacoteDados(int numSeq, String body) {
        return ByteBuffer.allocate(body.length()).putInt(numSeq).put(Byte.parseByte(body)).array();
    }
}

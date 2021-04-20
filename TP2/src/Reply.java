import java.nio.ByteBuffer;

public class Reply {
    private static final int headersize = 4;
    //reply line
    private String version;
    private String size;
    private String code;
    //header lines
    private String header;
    private String value;
    //body
    private String body;

    /**
     * Cria o pacote ACK com o numero pedido.
     * @param num O nº do pacote a ser criado.
     * @return O array do pacote já alocado e pronto a ser enviado pelo Socket.
     */
    static byte[] gerarPacoteReply(int num) {
        return ByteBuffer.allocate(headersize).putInt(num).array();
    }

}

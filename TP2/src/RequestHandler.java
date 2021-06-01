import java.net.DatagramSocket;
import java.util.Map;
import java.util.SortedSet;


public class RequestHandler implements Runnable {

     //Contém os sockets UDP para o envio de PDU's.
    private DatagramSocket internal_socket, control_socket;

     //Contém os endereços do peer e do nó que pretende enviar o Request.
    private String peer, nodeadress;

     //Contém o Request a enviar.
    private Request request;

     //Contém as portas para o envio de PDU's.
    private int protected_port, control_port;

     //Contém o boolen que nos permite quebrar a execução.
    private volatile boolean running = true;

     //Contém os fragmentos para enviar.
    private SortedSet<FSChunkProtocol> fragments;

     //Contém os valores do tamanho máximo do payload, o número do Request, e o tamanho máximo do PDU.
    private int max_data_chunk = 20 * 1, requestnumber, size = max_data_chunk + 256;

     //Contém os arrays para guardar os PDU's em bytes.
    private byte[] controlbuffer = new byte[size], pducontrolbuffer = new byte[size], pduBuffer = new byte[size];

     //Contém o map que corresponde o número do fragmento aos bytes crrespondentes.
    private Map<Integer, byte[]> pdufragments;

    public void run() {

    }

}
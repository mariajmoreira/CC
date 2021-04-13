import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;

/**
 * A classe RequestHandler é aquela que é responsável por enviar um Request via udp para
 * um nodo da mesma rede. Esta classe traduz-se numa thread.
 */
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
    private SortedSet<PDU> fragments;

     //Contém os valores do tamanho máximo do payload, o número do Request, e o tamanho máximo do PDU.
    private int max_data_chunk = 20 * 1, requestnumber, pdu_size = max_data_chunk + 256;

     //Contém os arrays para guardar os PDU's em bytes.
    private byte[] controlbuffer = new byte[pdu_size], pducontrolbuffer = new byte[pdu_size], pduBuffer = new byte[pdu_size];

     //Contém o map que corresponde o número do fragmento aos bytes crrespondentes.
    private Map<Integer, byte[]> pdufragments;

    public void run() {

    }

}
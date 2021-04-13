import java.io.Serializable;

/**
 * A classe PDU é aquela que representa um Protocol Data Unit dentro do nosso sistema. Qualquer
 * Request é encapsulado num PDU que garante a sua distribuição. Também contém mecanismos que nos
 * permitem efetuar retransmissões bem como montar diversos fragmentos.
 */
public class PDU implements Serializable {

    /**
     * Contém um identificador único para cada request encapsulado em PDU's.
     */
    private String identifier;
    /**
     * Contém a posição do fragmento, um inteiro de controlo, o total de fragmentos e o tamanho do Request.
     */
    private int position, control, total_fragments, totalSize;
    /**
     * Contém o payload do PDU.
     */
    private byte[] data;
    /**
     * Timestamp relativo à data de criação do PDU.
     */
    private long timestamp;


    /**
     * Contrutor vazio do PDU.
     */
    public PDU() {
    }

}
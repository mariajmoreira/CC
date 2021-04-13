import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A classe Request é aquela que representa um pedido dentro do nosso sistema. Qualquer pedido
 * é transformado num Request. Também contém mecanismos que nos
 * permitem ter uma noção de como o Request se encontra dentro do sistema através do seu estado.
 */
public class Request implements Serializable {

    //Contém a data de criação do Request.
    private long creationTime;

    //Contém o endereço de origem.
    private String origin_address;

    //Contém o endereço do nó que criou o Request.
    private String contact_node_address;

    //Contém a mensagem que a origem quer transmitir ao destino.
    private String message;

    //Contém a resposta do servidor à origem, por linhas.
    private List<String> response;

    /**
     * Contém o estado do Request, sendo que os estados possíveis
     * são: na (não atendido), ad (atendido no destino), sd (servido
     * no destino), to (a ser transmitido à origem), so (servido na
     * origem e tbd (to be deleted).
     */
    private String status;

}
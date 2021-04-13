import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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


    private String status;

}
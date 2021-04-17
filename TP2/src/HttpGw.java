import java.util.Scanner;

public class HttpGw {
    /** Porta UDP para troca dos pacotes em si. **/
    private static final int portaUDP = 7777;

    /** Porta ACK para troca dos Acknowledgement. **/
    private static final int portaEstado = 9999;

    /**
     * Função main para correr o programa.
     */
    public static void main(String[] args) {

        System.out.println("O que pretende fazer?");
        Scanner teclado = new Scanner(System.in);

        String texto = teclado.nextLine();
        String[] argumentos = texto.split(" ");

        switch (argumentos[0]) {
            case "GET":
                // GET c:/users/cocos/....
                Servidor servidor = new Servidor(portaUDP, portaEstado, argumentos[1]);
                break;
            case "PUT":
                // PUT localhost c:/users/cocos/....
                Cliente cliente = new Cliente(portaUDP, portaEstado, argumentos[2], argumentos[1]);
                break;
        }
    }
}

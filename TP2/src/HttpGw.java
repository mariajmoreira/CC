import java.util.Scanner;

public class HttpGw {
    /** Porta UDP para troca dos pacotes em si. **/
    private static final int portaUDP = 80;
    private static final int portaTCP = 80;


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
                Servidor servidor = new Servidor(portaUDP,portaTCP, argumentos[1]);
                break;
            case "PUT":
                // PUT localhost c:/users/cocos/....
                Cliente cliente = new Cliente(portaUDP,portaTCP, argumentos[2], argumentos[1]);
                break;
        }
    }
}

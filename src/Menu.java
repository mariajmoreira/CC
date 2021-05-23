 import java.util.List;
import java.util.Arrays;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.ArrayList;

public class Menu {
    // variÃ¡veis de instÃ¢ncia
    private List<String> opcoes;
    private int op;

    /**
     * Constructor for objects of class Menu
     */
    public Menu(String[] opcoes) {
        this.opcoes = Arrays.asList(opcoes);
        this.op = 0;
    }

    public Menu(List<String> opcoes) {
        this.opcoes = new ArrayList<>(opcoes);
        this.op = 0;
    }

    /**
     * MÃ©todo para apresentar o menu e ler uma opÃ§Ã£o.
     *
     */
    public void executa() {
        do {
            showMenu();
            this.op = lerOpcao();
        } while (this.op == -1);
    }

    /** Apresentar o menu */
    public void showMenu() {
        System.out.println("\n *** Menu *** ");
        for (int i=0; i<this.opcoes.size(); i++) {
            System.out.print(i+1);
            System.out.print(" - ");
            System.out.println(this.opcoes.get(i));
        }
        System.out.println("0 - Sair");
    }

    /** Ler uma opÃ§Ã£o vÃ¡lida */
    public int lerOpcao() {
        int op;
        Scanner is = new Scanner(System.in);

        System.out.print("Opção: ");
        try {
            op = is.nextInt();
        }
        catch (InputMismatchException e) { // NÃ£o foi inscrito um int
            op = -1;
        }
        if (op<0 || op>this.opcoes.size()) {
            System.out.println("Opção Inválida.");
            op = -1;
        }
        return op;
    }


    /**
     * MÃ©todo para obter a Ãºltima opÃ§Ã£o lida
     */
    public int getOpcao() {
        return this.op;
    }
}
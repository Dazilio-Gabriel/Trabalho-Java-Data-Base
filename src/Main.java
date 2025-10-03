

import entidades.Movimentacao;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n=== MENU ===");
            System.out.println("1 - Relatorios 1");
            System.out.println("2 - Inserir Registro 2");
            System.out.println("3 - Remover Registro 3");
            System.out.println("4 - Atualizar Registro 4");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");

            opcao = scanner.nextInt();


            switch (opcao) {
                case 0:
                    System.out.println("Voce esta saindo do programa"
                            + "\nObrigado por usar nosso programar");
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;

                default:
                    System.out.println("Numero invalido, favor inserir um numero dentro das opcoes do sistema");
            }

        }
    }
}
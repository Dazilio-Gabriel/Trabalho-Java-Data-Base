import java.util.Scanner;

import dao.movimentacaoDAO;
import dao.ProdutosDAO;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

// splash screen
        System.out.println("##################################################");
        System.out.println("#                                                #");
        System.out.println("#        SISTEMA DE CONTROLE DE ESTOQUE          #");
        System.out.println("#                                                #");
        System.out.println("#      TOTAL DE REGISTROS EXISTENTES             #");
        System.out.printf("#  1 - PRODUTOS:      %5d                    #%n", //totalProdutos);
// System.out.printf("#  2 - MOVIMENTAÇÕES: %5d                    #%n", totalMovimentacoes);
                System.out.println("#                                                #"); 
        System.out.println("#      CRIADO POR: GABRIEL DAZILIO               #");
        System.out.println("#                  VICTOR CASTRO                 #");
        System.out.println("#                                                #");
        System.out.println("#      DISCIPLINA: BANCO DE DADOS                #");
        System.out.println("#      PROFESSOR: HOWARD ROATTI                  #");
        System.out.println("#                                                #");
        System.out.println("##################################################\n");

        // loop
        int opcao = -1;
        while (opcao != 0) {

            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1 - Relatórios");
            System.out.println("2 - Inserir Registro");
            System.out.println("3 - Remover Registro");
            System.out.println("4 - Atualizar Registro");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    System.out.println("voce escolheu: relatorios");

                    break;
                case 2:
                    System.out.println("voce escolheu: inserir registro");
                    break;
                case 3:
                    System.out.println("voce escolheu: remover registro");

                    break;
                case 4:
                    System.out.println("voce escolheu: atualizar registro");
                    break;
                case 0:
                    System.out.println("saindo do sistema");
                    break;
                default:
                    System.out.println("opção inválida, por favor, escolha uma opção do menu.");
                    break;
            }
        }

        scanner.close();
    }
}
import java.util.List;
import java.util.Scanner;

import dao.MovimentacaoDAO;
import dao.ProdutosDAO;
import entidades.Movimentacao;
import entidades.Produtos;

public class Main {

    private static int estoque;

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

        // criar o objeto das classes
        ProdutosDAO prodDAO = new ProdutosDAO();
        MovimentacaoDAO movDAO = new MovimentacaoDAO();
        Produtos prod = new Produtos();
        Movimentacao mov = new Movimentacao();

        // loop
        int opcao = -1;
        while (opcao != 0) {

            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1 - Relatórios");
            System.out.println("2 - Inserir um novo produto");
            System.out.println("3 - Inserir uma movimentacao");
            System.out.println("4 - Atualizar um Produto");
            System.out.println("5 - Excluir um Produto");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            switch (opcao) {
                case 0:
                    System.out.println("saindo do sistema");
                    break;

                case 1:
                    System.out.println("voce escolheu: relatorios");

                    break;

                case 2:
                    System.out.println("voce escolheu: inserir registro");

                    System.out.println("digite o nome do produto");
                    String nome = scanner.nextLine();
                    System.out.println("digite a descricao do produto");
                    String descricao = scanner.nextLine();
                    System.out.println("digite a quantidade");
                    int estoque = scanner.nextInt();

                    Produtos novoProduto = new Produtos(0, nome, descricao, estoque);
                    prodDAO.inserir(novoProduto);

                    break;

                case 3:
                    System.out.println("voce escolheu: Inserir uma movimentacao");

                    break;

                case 4:

                    System.out.println("voce escolheu: atualizar um Produto");
                    System.out.println("\n--- ATUALIZAR REGISTRO ---");
                    System.out.println("Qual entidade você deseja atualizar?");
                    System.out.println("1 - Produto");
                    System.out.println("2 - Movimentação");
                    System.out.println("0 - Voltar ao Menu Principal");
                    System.out.print("Escolha uma opção: ");

                    int subOpcao = scanner.nextInt();
                    scanner.nextLine();

                    switch (subOpcao) {

                        case 0:
                            System.out.println("Você esta voltando ao menu inicial");
                            break;

                        case 1:
                            List<Produtos> listaDosProdutos = prodDAO.listarTodos();
                            System.out.println("\n--- PRODUTOS CADASTRADOS ---");

                            for (Produtos produtos : listaDosProdutos) {
                                System.out.println("ID: " + produtos.getIdProduto() + " | Nome: " + produtos.getNome() + " | Estoque: " + produtos.getEstoque());
                            }
                            System.out.println("---------------------------------------");

                            System.out.println("Digite o id do produto que voce quer alterar");
                            int novoIdP = scanner.nextInt();
                            scanner.nextLine();

                            System.out.println("Digite o novo nome do produtos");
                            String novoNome = scanner.nextLine();
                            System.out.println("Digite a nova descrição, caso tenha");
                            String novaDescricao = scanner.nextLine();
                            System.out.println("Digite a quantidade atual do estoque");
                            int novoEstoque = scanner.nextInt();

                            Produtos produtosAtualizados = new Produtos(novoIdP, novoNome, novaDescricao, novoEstoque);

                            prodDAO.atualizar(produtosAtualizados);

                            break;

                        case 2:
                            List<Movimentacao> listaDasMovimentacoes = movDAO.listarTodos();
                            System.out.println("\n--- PRODUTOS CADASTRADOS ---");

                            for (Movimentacao movimentacao : listaDasMovimentacoes) {
                                System.out.println("ID: " + movimentacao.getIdMovimentacao() + " | Tipo da movimentacao: " + movimentacao.getTipoMovimentacao() + " | Estoque: " + movimentacao.getQuantidade());

                                System.out.println("Digite o id do produto que voce quer alterar");
                                int novoIdM = scanner.nextInt();
                                scanner.nextLine();

                                System.out.println("Digite o tipo da movimentacao");
                                char tipoNovo = scanner.nextInt();
                                System.out.println("Digite o estoque atual");
                                int estoqueNovo = scanner.nextInt();


                                Movimentacao movimentacaoAtualizada = new Movimentacao(novoIdM, tipoNovo, estoqueNovo);


                            }

                            break;

                        default:
                            System.out.println("opção inválida, por favor, escolha uma opção do menu.");
                            break;
                    }

                default:
                    System.out.println("opção inválida, por favor, escolha uma opção do menu.");
                    break;
            }
        }
    }
}
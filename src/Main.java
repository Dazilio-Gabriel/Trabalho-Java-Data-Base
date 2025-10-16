import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import dao.MovimentacaoDAO;
import dao.ProdutosDAO;
import entidades.Movimentacao;
import entidades.Produtos;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ProdutosDAO produtosDAO = new ProdutosDAO();
        MovimentacaoDAO movimentacaoDAO = new MovimentacaoDAO();

        /*
        // --- SPLASH SCREEN (agora funcional) ---
        int totalProdutos = produtosDAO.contarRegistros();
        int totalMovimentacoes = movimentacaoDAO.contarRegistros();

        System.out.println("##################################################");
        System.out.println("#                                                #");
        System.out.println("#        SISTEMA DE CONTROLE DE ESTOQUE          #");
        System.out.println("#                                                #");
        System.out.println("#      TOTAL DE REGISTROS EXISTENTES             #");
        System.out.printf("#  1 - PRODUTOS:      %5d                    #%n", totalProdutos);
        System.out.printf("#  2 - MOVIMENTAÇÕES: %5d                    #%n", totalMovimentacoes);
        System.out.println("#                                                #");
        System.out.println("#      CRIADO POR: GABRIEL DAZILIO               #");
        System.out.println("#                  VICTOR CASTRO                 #");
        System.out.println("#                                                #");
        System.out.println("#      DISCIPLINA: BANCO DE DADOS                #");
        System.out.println("#      PROFESSOR: HOWARD ROATTI                  #");
        System.out.println("#                                                #");
        System.out.println("##################################################\n");
*/
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1 - Relatórios");
            System.out.println("2 - Inserir um novo produto");
            System.out.println("3 - Inserir uma movimentacao");
            System.out.println("4 - Atualizar um Registro");
            System.out.println("5 - Excluir um Produto");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Erro: Por favor, digite apenas números.");
                scanner.next();
                opcao = -1;
                continue;
            }
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("Você escolheu: Relatórios (Não implementado)");

                    break;

                case 2:
                    System.out.println("\n--- INSERIR NOVO PRODUTO ---");
                    System.out.print("Digite o nome do produto: ");
                    String nome = scanner.nextLine();
                    System.out.print("Digite a descrição: ");
                    String descricao = scanner.nextLine();
                    System.out.print("Digite a quantidade em estoque: ");
                    int estoque = scanner.nextInt();
                    scanner.nextLine();

                    Produtos novoProduto = new Produtos(0, nome, descricao, estoque);
                    produtosDAO.inserir(novoProduto);

                    break;

                case 3:
                    System.out.println("Você escolheu: Inserir uma movimentação (Não implementado)");

                    break;

                case 4:
                    System.out.println("\n--- ATUALIZAR REGISTRO ---");
                    System.out.println("1 - Atualizar Produto");
                    System.out.println("2 - Atualizar Movimentação");
                    System.out.println("0 - Voltar");
                    System.out.print("Escolha uma opção: ");
                    int subOpcao = scanner.nextInt();
                    scanner.nextLine();

                    switch (subOpcao) {
                        case 1:
                            System.out.println("\n--- ATUALIZAR PRODUTO ---");
                            List<Produtos> listaDosProdutos = produtosDAO.listarTodos();
                            System.out.println("\n--- PRODUTOS CADASTRADOS ---");
                            for (Produtos produto : listaDosProdutos) {
                                System.out.println("ID: " + produto.getIdProduto() + " | Nome: " + produto.getNome());
                            }
                            System.out.println("---------------------------------------");

                            System.out.print("Digite o ID do produto que deseja alterar: ");
                            int idParaAtualizar = scanner.nextInt();
                            scanner.nextLine();
                            System.out.print("Digite o NOVO nome do produto: ");
                            String novoNome = scanner.nextLine();
                            System.out.print("Digite a NOVA descrição: ");
                            String novaDescricao = scanner.nextLine();
                            System.out.print("Digite a NOVA quantidade em estoque: ");
                            int novoEstoque = scanner.nextInt();
                            scanner.nextLine();

                            Produtos produtoAtualizado = new Produtos(idParaAtualizar, novoNome, novaDescricao, novoEstoque);
                            produtosDAO.atualizar(produtoAtualizado);

                            break;

                        case 2:
                            System.out.println("\n--- ATUALIZAR MOVIMENTAÇÃO ---");
                            List<Movimentacao> listaDeMovimentacoes = movimentacaoDAO.listarTodos();
                            System.out.println("\n--- MOVIMENTAÇÕES CADASTRADAS ---");
                            for (Movimentacao mov : listaDeMovimentacoes) {
                                System.out.println("ID: " + mov.getIdMovimentacao() + " | Produto: " + mov.getProdutos().getNome());
                            }
                            System.out.println("-------------------------------------------------");

                            System.out.print("Digite o ID da movimentação que você quer alterar: ");
                            int idMovimentacaoParaAtualizar = scanner.nextInt();
                            scanner.nextLine();

                            List<Produtos> produtosDisponiveis = produtosDAO.listarTodos();
                            System.out.println("\n--- PRODUTOS DISPONÍVEIS ---");
                            // CORREÇÃO: Usando a variável correta ('prodDaLista')
                            for (Produtos prodDaLista : produtosDisponiveis) {
                                System.out.println("ID: " + prodDaLista.getIdProduto() + " | Nome: " + prodDaLista.getNome());
                            }
                            System.out.println("---------------------------------------");

                            System.out.print("Digite o ID do NOVO produto para esta movimentação: ");
                            int novoIdProduto = scanner.nextInt();
                            scanner.nextLine();
                            System.out.print("Digite o NOVO tipo da movimentação (ENTRADA/SAIDA): ");
                            String novoTipo = scanner.nextLine();
                            System.out.print("Digite a NOVA quantidade: ");
                            int novaQuantidade = scanner.nextInt();
                            scanner.nextLine();
                            LocalDate novaData = LocalDate.now();
                            Produtos produtoSelecionado = produtosDAO.buscarPorId(novoIdProduto);

                            if (produtoSelecionado != null) {
                                Movimentacao movimentacaoAtualizada = new Movimentacao(idMovimentacaoParaAtualizar, produtoSelecionado, novoTipo, novaQuantidade, novaData);
                                movimentacaoDAO.atualizar(movimentacaoAtualizada);
                            } else {
                                System.out.println("Erro: Produto com o ID informado não foi encontrado.");
                            }

                            break;

                        case 0:

                            break;

                        default:
                            System.out.println("Opção do sub-menu inválida.");

                            break;
                    }

                    break;

                case 5:
                    System.out.println("Você escolheu: Excluir um Produto (Não implementado)");

                    break;

                case 0:
                    System.out.println("Saindo do sistema... Até logo!");

                    break;

                default:
                    System.out.println("Opção inválida! Por favor, escolha uma opção do menu.");

                    break;
            }
        }
        scanner.close();
    }
}
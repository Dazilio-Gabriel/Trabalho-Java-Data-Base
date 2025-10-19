import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import dao.MovimentacaoDAO;
import dao.ProdutosDAO;
import entidades.Movimentacao;
import entidades.Produtos;
import entidades.RelatorioAgrupado;

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
        System.out.println("#        SISTEMA DE CONTROLE DE ESTQUE          #");
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
            System.out.println("2 - Inserir Registro");
            System.out.println("3 - Remover Registro");
            System.out.println("4 - Atualizar Registro");
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
                case 1: // Relatórios
                    System.out.println("\n--- RELATORIOS ---");
                    System.out.println("1 - Listar Todas as Movimentações (com Nome do Produto)");
                    System.out.println("2 - Total de Itens Movimentados por Tipo");
                    System.out.println("0 - Voltar");
                    System.out.print("Escolha uma opção: ");
                    int subOpcaoRelatorios = scanner.nextInt();
                    scanner.nextLine();

                    switch (subOpcaoRelatorios) {
                        case 1:
                            System.out.println("\n--- TODAS AS MOVIMENTACOES REGISTRADAS ---");
                            List<Movimentacao> listaMovimentacoesRelatorio = movimentacaoDAO.listarTodos();

                            if (listaMovimentacoesRelatorio == null || listaMovimentacoesRelatorio.isEmpty()) {
                                System.out.println("Nenhuma movimentação encontrada.");
                            } else {
                                for (Movimentacao mov : listaMovimentacoesRelatorio) {
                                    System.out.println("ID: " + mov.getIdMovimentacao() + " | Produto: [" + mov.getProdutos().getIdProduto() + "] " + mov.getProdutos().getNome() + " | Tipo: " + mov.getTipoMovimentacao() + " | Qtd: " + mov.getQuantidade() + " | Data: " + mov.getDataMov());
                                }
                            }
                            System.out.println("---------------------------------------------------------------------");
                            break;
                        case 2:
                            System.out.println("\n--- RELATÓRIO: TOTAL POR TIPO ---");
                            List<RelatorioAgrupado> resultadoAgrupado = movimentacaoDAO.gerarRelatorioSomaPorTipo();

                            if (resultadoAgrupado == null || resultadoAgrupado.isEmpty()) {
                                System.out.println("Nenhum dado encontrado para agrupar.");
                            } else {
                                for (RelatorioAgrupado item : resultadoAgrupado) {
                                    System.out.println("Tipo: " + item.getTipoMovimentacao() + " | Total de Itens: " + item.getTotalQuantidade());
                                }
                            }
                            break;
                        case 0:
                            System.out.println("Voltando ao menu principal");
                            break;
                        default:
                            System.out.println("Opção de relatório inválida.");
                            break;
                    }
                    break;

                case 2: // Inserir Registro
                    String continuarInsercao;
                    do {
                        System.out.println("\n--- INSERIR REGISTRO ---");
                        System.out.println("1 - Inserir Produto");
                        System.out.println("2 - Inserir Movimentação");
                        System.out.println("0 - Voltar ao Menu Principal");
                        System.out.print("Escolha uma opção: ");
                        int subOpcaoInserir = scanner.nextInt();
                        scanner.nextLine();

                        switch (subOpcaoInserir) {
                            case 1:
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
                            case 2:
                                System.out.println("\n--- INSERIR NOVA MOVIMENTACAO ---");
                                List<Produtos> listaDosProdutosMov = produtosDAO.listarTodos();
                                if (listaDosProdutosMov == null || listaDosProdutosMov.isEmpty()) {
                                    System.out.println("ERRO: Cadastre produtos antes de inserir movimentações.");
                                    break;
                                }
                                System.out.println("\n--- PRODUTOS DISPONÍVEIS ---");
                                for (Produtos produto : listaDosProdutosMov) {
                                    System.out.println("ID: " + produto.getIdProduto() + " | Nome: " + produto.getNome());
                                }
                                System.out.println("---------------------------------------");

                                System.out.print("Digite o ID do produto que você irá movimentar: ");
                                int idProdMov = scanner.nextInt();
                                scanner.nextLine();
                                System.out.print("Digite o tipo da movimentação (ENTRADA/SAIDA): ");
                                String tipoMov = scanner.nextLine();
                                System.out.print("Digite a quantidade: ");
                                int quantidadeMov = scanner.nextInt();
                                scanner.nextLine();

                                Produtos produtoSelecionado = produtosDAO.buscarPorId(idProdMov);
                                LocalDate dataAtual = LocalDate.now();

                                if (produtoSelecionado != null) {
                                    Movimentacao novaMovimentacao = new Movimentacao(0, produtoSelecionado, tipoMov, quantidadeMov, dataAtual);
                                    movimentacaoDAO.inserir(novaMovimentacao);
                                } else {
                                    System.out.println("Erro: Produto com o ID informado não foi encontrado.");
                                }
                                break;
                            case 0:
                                System.out.println("\nVoltando ao menu principal...");
                                break;
                            default:
                                System.out.println("Opção do sub-menu inválida.");
                                break;
                        }

                        if (subOpcaoInserir != 0) {
                            System.out.print("\nDeseja inserir outro registro? (S/N): ");
                            continuarInsercao = scanner.nextLine();
                        } else {
                            continuarInsercao = "N";
                        }

                    } while (continuarInsercao.equalsIgnoreCase("S"));
                    break;

                case 3: // Remover Registro
                    String continuarRemocao;
                    do {
                        System.out.println("\n--- REMOVER REGISTRO ---");
                        System.out.println("1 - Remover Produto");
                        System.out.println("2 - Remover Movimentação");
                        System.out.println("0 - Voltar ao Menu Principal");
                        System.out.print("Escolha uma opção: ");
                        int subOpcaoRemover = scanner.nextInt();
                        scanner.nextLine();

                        switch (subOpcaoRemover) {
                            case 1:
                                System.out.println("\n--- REMOVER PRODUTO ---");
                                List<Produtos> listaDosProdutos = produtosDAO.listarTodos();
                                if (listaDosProdutos == null || listaDosProdutos.isEmpty()) {
                                    System.out.println("Nenhum produto cadastrado para remover.");
                                    break;
                                }
                                System.out.println("\n--- PRODUTOS CADASTRADOS ---");
                                for (Produtos produto : listaDosProdutos) {
                                    System.out.println("ID: " + produto.getIdProduto() + " | Nome: " + produto.getNome());
                                }
                                System.out.println("---------------------------------------");
                                System.out.print("Digite o ID do produto que deseja remover: ");
                                int idProdDel = scanner.nextInt();
                                scanner.nextLine();
                                int contagem = movimentacaoDAO.contarMovPorId(idProdDel);

                                if (contagem > 0) {
                                    System.out.println("\nERRO: Este produto não pode ser removido, pois possui " + contagem + " movimentacao(ões) associada(s).");
                                } else {
                                    Produtos produtoParaRemover = produtosDAO.buscarPorId(idProdDel);
                                    if (produtoParaRemover != null) {
                                        System.out.print("Tem certeza que deseja remover o produto '" + produtoParaRemover.getNome() + "'? (S/N): ");
                                        String confirmacao = scanner.nextLine();
                                        if (confirmacao.equalsIgnoreCase("S")) {
                                            produtosDAO.deletar(idProdDel);
                                            System.out.println("Produto removido com sucesso!");
                                        } else {
                                            System.out.println("Operação cancelada.");
                                        }
                                    } else {
                                        System.out.println("Erro: Produto com o ID informado não foi encontrado.");
                                    }
                                }
                                break;

                            case 2:
                                System.out.println("\n--- REMOVER MOVIMENTAÇÃO ---");
                                List<Movimentacao> listaDeMovimentacoes = movimentacaoDAO.listarTodos();
                                if (listaDeMovimentacoes == null || listaDeMovimentacoes.isEmpty()) {
                                    System.out.println("Nenhuma movimentação cadastrada para remover.");
                                    break;
                                }
                                System.out.println("\n--- MOVIMENTAÇÕES CADASTRADAS ---");
                                for (Movimentacao mov : listaDeMovimentacoes) {
                                    System.out.println("ID: " + mov.getIdMovimentacao() + " | Produto: " + mov.getProdutos().getNome() + " | Tipo: " + mov.getTipoMovimentacao());
                                }
                                System.out.println("-------------------------------------------------");
                                System.out.print("Digite o ID da movimentação que você deseja excluir: ");
                                int idMovDel = scanner.nextInt();
                                scanner.nextLine();

                                Movimentacao movimentacaoParaRemover = movimentacaoDAO.buscarPorId(idMovDel);
                                if (movimentacaoParaRemover != null) {
                                    System.out.print("Tem certeza que deseja remover a movimentação de '" + movimentacaoParaRemover.getTipoMovimentacao() + "' do produto '" + movimentacaoParaRemover.getProdutos().getNome() + "'? (S/N): ");
                                    String confirmacao = scanner.nextLine();
                                    if (confirmacao.equalsIgnoreCase("S")) {
                                        movimentacaoDAO.deletar(idMovDel);
                                        System.out.println("Movimentação removida com sucesso!");
                                    } else {
                                        System.out.println("Operação cancelada.");
                                    }
                                } else {
                                    System.out.println("Erro: Movimentação com o ID informado não foi encontrada.");
                                }
                                break;

                            case 0:
                                System.out.println("\nVoltando ao menu principal...");
                                break;

                            default:
                                System.out.println("Opção do sub-menu inválida.");
                                break;
                        }

                        if (subOpcaoRemover != 0) {
                            System.out.print("\nDeseja remover outro registro? (S/N): ");
                            continuarRemocao = scanner.nextLine();
                        } else {
                            continuarRemocao = "N";
                        }

                    } while (continuarRemocao.equalsIgnoreCase("S"));
                    break;

                case 4: // Atualizar Registro - AGORA COM O LOOP E EXIBIÇÃO
                    String continuarAtualizacao;
                    do {
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
                                if (listaDosProdutos == null || listaDosProdutos.isEmpty()) {
                                    System.out.println("Nenhum produto cadastrado para atualizar.");
                                    break;
                                }
                                System.out.println("\n--- PRODUTOS CADASTRADOS ---");
                                for (Produtos produto : listaDosProdutos) {
                                    System.out.println("ID: " + produto.getIdProduto() + " | Nome: " + produto.getNome());
                                }
                                System.out.println("---------------------------------------");

                                System.out.print("Digite o ID do produto que deseja alterar: ");
                                int idParaAtualizar = scanner.nextInt();
                                scanner.nextLine();

                                Produtos produtoOriginal = produtosDAO.buscarPorId(idParaAtualizar);
                                if (produtoOriginal == null) {
                                    System.out.println("Erro: Produto com ID informado não encontrado.");
                                    break;
                                }

                                System.out.print("Digite o NOVO nome do produto: ");
                                String novoNome = scanner.nextLine();
                                System.out.print("Digite a NOVA descrição: ");
                                String novaDescricao = scanner.nextLine();
                                System.out.print("Digite a NOVA quantidade em estoque: ");
                                int novoEstoque = scanner.nextInt();
                                scanner.nextLine();

                                Produtos produtoAtualizado = new Produtos(idParaAtualizar, novoNome, novaDescricao, novoEstoque);
                                produtosDAO.atualizar(produtoAtualizado);

                                System.out.println("\n--- Registro Atualizado ---");
                                Produtos produtoAposUpdate = produtosDAO.buscarPorId(idParaAtualizar);
                                if (produtoAposUpdate != null) {
                                    System.out.println(produtoAposUpdate.toString());
                                }
                                break;

                            case 2:
                                System.out.println("\n--- ATUALIZAR MOVIMENTAÇÃO ---");
                                List<Movimentacao> listaDeMovimentacoes = movimentacaoDAO.listarTodos();
                                if (listaDeMovimentacoes == null || listaDeMovimentacoes.isEmpty()) {
                                    System.out.println("Nenhuma movimentação cadastrada para atualizar.");
                                    break;
                                }
                                System.out.println("\n--- MOVIMENTAÇÕES CADASTRADAS ---");
                                for (Movimentacao mov : listaDeMovimentacoes) {
                                    System.out.println("ID: " + mov.getIdMovimentacao() + " | Produto: " + mov.getProdutos().getNome());
                                }
                                System.out.println("-------------------------------------------------");

                                System.out.print("Digite o ID da movimentação que você quer alterar: ");
                                int idMovimentacaoParaAtualizar = scanner.nextInt();
                                scanner.nextLine();

                                Movimentacao movOriginal = movimentacaoDAO.buscarPorId(idMovimentacaoParaAtualizar);
                                if (movOriginal == null) {
                                    System.out.println("Erro: Movimentação com ID informado não encontrada.");
                                    break;
                                }

                                List<Produtos> produtosDisponiveis = produtosDAO.listarTodos();
                                System.out.println("\n--- PRODUTOS DISPONÍVEIS ---");

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

                                    System.out.println("\n--- Registro Atualizado ---");
                                    Movimentacao movAposUpdate = movimentacaoDAO.buscarPorId(idMovimentacaoParaAtualizar);
                                    if (movAposUpdate != null) {
                                        System.out.println(movAposUpdate.toString());
                                    }
                                } else {
                                    System.out.println("Erro: Produto com o ID informado não foi encontrado.");
                                }
                                break;

                            case 0:
                                System.out.println("\nVoltando ao menu principal");
                                break;

                            default:
                                System.out.println("Opção do sub-menu inválida.");
                                break;
                        }

                        if (subOpcao != 0) {
                            System.out.print("\nDeseja atualizar outro registro? (S/N): ");
                            continuarAtualizacao = scanner.nextLine();
                        } else {
                            continuarAtualizacao = "N";
                        }

                    } while (continuarAtualizacao.equalsIgnoreCase("S"));
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
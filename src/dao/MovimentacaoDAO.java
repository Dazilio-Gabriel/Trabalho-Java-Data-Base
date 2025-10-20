package dao;

import entidades.Produtos;
import entidades.Movimentacao;
import utils.conexaoDB;
import entidades.RelatorioAgrupado;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.sql.Connection;

public class MovimentacaoDAO {
    private ProdutosDAO produtosDAO;

    public MovimentacaoDAO() {
        this.produtosDAO = new ProdutosDAO();

    }

    public void inserir(Movimentacao movimentacao) {
        String sqlInsert = "INSERT INTO movimentacao (id_produto, tipo_movimentacao, quantidade, data_movimentacao) VALUES (?, ?, ?, ?)";
        String sqlUpdateProduto = "";

        int idProduto = movimentacao.getProdutos().getIdProduto();
        int quantidade = movimentacao.getQuantidade();
        String tipoMov = movimentacao.getTipoMovimentacao();


        if ("ENTRADA".equalsIgnoreCase(tipoMov)) {
            sqlUpdateProduto = "UPDATE produtos SET quantidade_estoque = quantidade_estoque + ? WHERE id_produto = ?";
        } else if ("SAIDA".equalsIgnoreCase(tipoMov)) {
            sqlUpdateProduto = "UPDATE produtos SET quantidade_estoque = quantidade_estoque - ? WHERE id_produto = ?";
        } else {
            Logger.getLogger(MovimentacaoDAO.class.getName()).log(Level.WARNING, "Tipo de movimentação inválido: " + tipoMov);
            System.err.println("Erro: Tipo de movimentação inválido '" + tipoMov + "'. Apenas 'ENTRADA' ou 'SAIDA' são permitidos.");
            return;
        }

        Connection conn = null;
        PreparedStatement stmtInsert = null;
        PreparedStatement stmtUpdate = null;

        try {
            conn = conexaoDB.getConexao();
            if (conn == null) {
                System.err.println("Falha na conexao");
                return;
            }

            stmtInsert = conn.prepareStatement(sqlInsert);
            stmtInsert.setInt(1, idProduto);
            stmtInsert.setString(2, tipoMov);
            stmtInsert.setInt(3, quantidade);
            stmtInsert.setDate(4, java.sql.Date.valueOf(movimentacao.getDataMov()));
            stmtInsert.executeUpdate();
            System.out.println("Movimentação do produto '" + movimentacao.getProdutos().getNome() + "' inserida com sucesso!");

            stmtUpdate = conn.prepareStatement(sqlUpdateProduto);
            stmtUpdate.setInt(1, quantidade);
            stmtUpdate.setInt(2, idProduto);
            stmtUpdate.executeUpdate();
            System.out.println("Estoque do produto ID " + idProduto + " atualizado.");

        } catch (SQLException e) {
            Logger.getLogger(MovimentacaoDAO.class.getName()).log(Level.SEVERE, "Erro ao inserir movimentacao e atualizar estoque", e);
            System.err.println("Erro ao processar movimentacao, tente novamente mais tarde");
        } finally {
            try {
                if (stmtInsert != null) stmtInsert.close();
                if (stmtUpdate != null) stmtUpdate.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(MovimentacaoDAO.class.getName()).log(Level.SEVERE, "Erro ao fechar recursos JDBC", ex);
            }
        }
    }


    public void atualizar(Movimentacao movimentacao) {
        String sql = "UPDATE movimentacao SET id_produto = ?, tipo_movimentacao = ?, quantidade = ? where id_movimentacao = ?";

        try (Connection conn = conexaoDB.getConexao()) {

            if (conn == null) {
                System.out.println("falha na conexao");
                return;
            }
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, movimentacao.getProdutos().getIdProduto());
            stmt.setString(2, movimentacao.getTipoMovimentacao());
            stmt.setInt(3, movimentacao.getQuantidade());
            stmt.setInt(4, movimentacao.getIdMovimentacao());

            stmt.executeUpdate();


        } catch (SQLException e) {
            Logger.getLogger(MovimentacaoDAO.class.getName()).log(Level.SEVERE, "Falha para atualizar movimentacao", e);
        }
    }

    public void deletar(int id) {
        String sql = "UPDATE movimentacao SET sr_deleted = 't' where id_movimentacao = ?";

        try (Connection conn = conexaoDB.getConexao()) {

            if (conn == null) {
                System.out.println("falha na conexao");
                return;
            }

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            Logger.getLogger(MovimentacaoDAO.class.getName()).log(Level.SEVERE, "falha para deletar a movimentacao", e);
        }

    }

    public List<Movimentacao> listarTodos() {
        List<Movimentacao> movimentacaos = new ArrayList<>();

        String sql = "SELECT * FROM movimentacao where sr_deleted = 'F'";

        try (Connection conn = conexaoDB.getConexao()) {

            if (conn == null) {
                System.out.println("falha na conexao");
                return null;
            }
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                int idProduto = rs.getInt("id_produto");
                Produtos produtoDaMovimentacao = this.produtosDAO.buscarPorId(idProduto);

                if (produtoDaMovimentacao != null) {

                    int idMov = rs.getInt("id_movimentacao");
                    String tipoMov = rs.getString("tipo_movimentacao");
                    int quantidade = rs.getInt("quantidade");
                    java.sql.Date dataDoBanco = rs.getDate("data_movimentacao");
                    LocalDate dataParaObjeto = dataDoBanco.toLocalDate();
                    char srDeleted = rs.getString("sr_deleted").charAt(0);

                    Movimentacao mov = new Movimentacao(idMov, produtoDaMovimentacao, tipoMov, quantidade, dataParaObjeto);
                    mov.setSr_deleted(srDeleted);
                    movimentacaos.add(mov);
                }
            }


        } catch (SQLException e) {
            Logger.getLogger(MovimentacaoDAO.class.getName()).log(Level.SEVERE, "erro para puxar todas as movimentacoes");
        }

        return movimentacaos;
    }

    public int contarRegistros() {
        String sql = "SELECT COUNT(*) FROM movimentacao";

        try (Connection conn = conexaoDB.getConexao()) {

            if (conn == null) {
                System.out.println("falha na conexao");
                return 0;
            }

            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            Logger.getLogger(MovimentacaoDAO.class.getName()).log(Level.SEVERE, "falha ao conferir a quantidade de movimentacoes", e);
        }
        return 0;
    }

    public int contarMovPorId(int idProduto) {
        String sql = "SELECT COUNT(*) from movimentacao where id_produto = ? ";

        try (Connection conn = conexaoDB.getConexao()) {

            if (conn == null) {
                System.out.println("falha na conexao");
                return 0;
            }

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, idProduto);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            Logger.getLogger(MovimentacaoDAO.class.getName()).log(Level.SEVERE, "Falha para verificar produtos deletados", e);
        }
        return 0;
    }

    public Movimentacao buscarPorId(int id) {
        String sql = "SELECT * FROM movimentacao WHERE id_movimentacao = ?";

        try (Connection conn = conexaoDB.getConexao()) {
            if (conn == null) {
                System.err.println("Falha na conexao.");
                return null;
            }

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int idProduto = rs.getInt("id_produto");
                Produtos produtoDaMovimentacao = this.produtosDAO.buscarPorId(idProduto);

                if (produtoDaMovimentacao != null) {
                    String tipoMov = rs.getString("tipo_movimentacao");
                    int quantidade = rs.getInt("quantidade");
                    LocalDate dataParaObjeto = rs.getDate("data_movimentacao").toLocalDate();
                    char srDeleted = rs.getString("sr_deleted").charAt(0);

                    Movimentacao movimentacaoEncontrada = new Movimentacao(id, produtoDaMovimentacao, tipoMov, quantidade, dataParaObjeto);
                    movimentacaoEncontrada.setSr_deleted(srDeleted);

                    return movimentacaoEncontrada;
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(MovimentacaoDAO.class.getName()).log(Level.SEVERE, "Falha para buscar a movimentacao com ID: " + id, e);
        }

        return null;
    }

    public List<RelatorioAgrupado> gerarRelatorioSomaPorTipo() {
        List<RelatorioAgrupado> relatoriosAgrupados = new ArrayList<>();

        String sql = "SELECT tipo_movimentacao, SUM(quantidade) AS total FROM movimentacao GROUP BY tipo_movimentacao";

        try (Connection conn = conexaoDB.getConexao()) {
            if (conn == null) {
                System.out.println("falha na conexao");
                return null;
            }

            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                String tipo = rs.getString("tipo_movimentacao");
                int total = rs.getInt("total");

                RelatorioAgrupado itemRelatorios = new RelatorioAgrupado(tipo, total);
                relatoriosAgrupados.add(itemRelatorios);
            }


        } catch (Exception e) {
            Logger.getLogger(MovimentacaoDAO.class.getName()).log(Level.SEVERE, "falha para gerar o relaotiro agrupado", e);
        }

        return relatoriosAgrupados;
    }
}
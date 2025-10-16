package dao;

import entidades.Produtos;
import entidades.Movimentacao;
import utils.conexaoDB;

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

        String sql = "INSERT INTO movimentacao (id_produto, tipo_movimentacao, quantidade, data_movimentacao) values (?, ?, ?, ?)";

        try (Connection conn = conexaoDB.getConexao()) {

            if (conn == null) {
                System.err.println("falha na conexao");
                return;
            }

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, movimentacao.getProdutos().getIdProduto());
            stmt.setString(2, movimentacao.getTipoMovimentacao());
            stmt.setInt(3, movimentacao.getQuantidade());
            stmt.setDate(4, java.sql.Date.valueOf(movimentacao.getDataMov()));

            stmt.executeUpdate();
            System.out.println("Movimentação do produto '" + movimentacao.getProdutos().getNome() + "movimentacao inserida com sucesso");


        } catch (SQLException e) {
            Logger.getLogger(MovimentacaoDAO.class.getName()).log(Level.SEVERE, "erro para inserir a movimentacao", e);
            System.err.println("erro para inserir a movimentacao, tente novamente mais tarde");
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


    public List<Movimentacao> listarTodos() {
        List<Movimentacao> movimentacaos = new ArrayList<>();

        String sql = "SELECT * FROM movimetacao where sr_deleted = 'F'";

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
}
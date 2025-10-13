package dao;

import entidades.Movimentacao;
import utils.conexaoDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
package dao;

import entidades.Produtos;
import utils.conexaoDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;
import java.util.logging.Level;

public class ProdutosDAO {

    public void inserir(Produtos produtos) {

        String sql = "INSERT INTO produtos (nome, descricao, quantidade_estoque) VALUES (?, ?, ?)";

        try (Connection conn = conexaoDB.getConexao()) {

            if (conn == null) {
                System.err.println("falha na conexao.");
                return;
            }

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, produtos.getNome());
            stmt.setString(2, produtos.getDescricao());
            stmt.setInt(3, produtos.getEstoque());

            stmt.executeUpdate();


        } catch (SQLException e) {

            Logger.getLogger(ProdutosDAO.class.getName()).log(Level.SEVERE, "erro para inserir um produto", e);
            System.err.println("erro para inserir o produto, tente novamente.");
        }
    }

    public Produtos buscarPorId(int id) {

        String sql = "SELECT * FROM PRODUTOS WHERE id_produto = ?";

        try (Connection conn = conexaoDB.getConexao()) {

            if (conn == null) {
                System.err.println("falha na conexao.");
                return null;
            }

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                String nome = rs.getString("nome");
                String descricao = rs.getString("descricao");
                int estoque = rs.getInt("quantidade_estoque");
                char srDeleted = rs.getString("sr_deleted").charAt(0);

                Produtos produtosEncontrados = new Produtos(id, nome, descricao, estoque);
                produtosEncontrados.setSr_deleted(srDeleted);

                return produtosEncontrados;

            }

        } catch (SQLException e) {
            Logger.getLogger(ProdutosDAO.class.getName()).log(Level.SEVERE, "erro para buscar o produto pelo id");
            System.out.println("erro para buscar o produto pelo id, tente novamente");

        }
        return null;
    }
}
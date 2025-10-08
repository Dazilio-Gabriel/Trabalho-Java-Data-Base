package dao;

import entidades.Produtos;
import utils.conexaoDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
}
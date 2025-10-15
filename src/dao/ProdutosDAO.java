package dao;

import entidades.Produtos;
import utils.conexaoDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

    public void atualizar(Produtos produtos) {

        String sql = "UPDATE produtos SET nome = ?, descricao = ?, quantidade_estoque = ? where id_produto = ?";

        try (Connection conn = conexaoDB.getConexao()) {

            if (conn == null) {
                System.err.println("falha na conexao");
                return;
            }

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, produtos.getNome());
            stmt.setString(2, produtos.getDescricao());
            stmt.setInt(3, produtos.getEstoque());
            stmt.setInt(4, produtos.getIdProduto());

            stmt.executeUpdate();


        } catch (SQLException e) {
            Logger.getLogger(ProdutosDAO.class.getName()).log(Level.SEVERE, "erro ao atualizar o produto", e);
            System.err.println("erro para atualziar o produto, tente novamente.");

        }
    }

    public void deletar(int id) {

        String sql = "UPDATE produtos SET sr_deleted = 'T' where id_produto = ? ";

        try (Connection conn = conexaoDB.getConexao()) {

            if (conn == null) {
                System.err.println("falha na conexao");
                return;
            }

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            stmt.executeUpdate();


        } catch (SQLException e) {
            Logger.getLogger(ProdutosDAO.class.getName()).log(Level.SEVERE, "falha para deletar um produto", e);
        }
    }

    public Produtos buscarPorId(int id) {

        String sql = "SELECT * FROM produtos WHERE id_produto = ?";

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
            Logger.getLogger(ProdutosDAO.class.getName()).log(Level.SEVERE, "erro para buscar o produto pelo id", e);
        }
        return null;
    }

    public List<Produtos> listarTodos() {
        List<Produtos> produtos = new ArrayList<>();

        String sql = "SELECT * FROM produtos where sr_deleted = 'F' ";

        try (Connection conn = conexaoDB.getConexao()) {

            if (conn == null) {
                System.err.println("falha na conexao");
                return null;
            }

            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                int id_Produto = rs.getInt("id_produto");
                String nome = rs.getString("nome");
                String descricao = rs.getString("descricao");
                int estoque = rs.getInt("quantidade_estoque");
                char srDeleted = rs.getString("sr_deleted").charAt(0);

                Produtos itens = new Produtos(id_Produto, nome, descricao, estoque);
                itens.setSr_deleted(srDeleted);

                produtos.add(itens);

            }


        } catch (SQLException e) {

            Logger.getLogger(ProdutosDAO.class.getName()).log(Level.SEVERE, "erro para listar todos", e);
        }


        return produtos;
    }
}
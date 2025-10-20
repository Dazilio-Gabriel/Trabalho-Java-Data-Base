package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class conexaoDB {

    private static final String URL = "jdbc:mysql://localhost:3306/controle_estoque";
    private static final String USER = "root";
    private static final String PASSWORD = "trabalhobanco";
    private static final Logger logger = Logger.getLogger(conexaoDB.class.getName());

    public static Connection getConexao() {

        System.out.println("tentando conectar ao banco de dados...");

        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            logger.info("a conexao com o banco foi feita com sucesso");

            return conn;

        } catch (SQLException e) {
            System.err.println("--- FALHA NA CONEXÃO ---");
            logger.log(Level.SEVERE, "verifique se o servidor MySQL ou se as crendencias estao corretas");

            return null;

            
        }
    }
}
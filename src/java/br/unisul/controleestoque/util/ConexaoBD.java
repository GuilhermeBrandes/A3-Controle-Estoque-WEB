package br.unisul.controleestoque.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {

    // --- CONFIGURAÇÃO DO BANCO ---
    // Coloque aqui o NOME EXATO do banco de dados que você criou com o script
    private static final String DATABASE_NAME = "controle_estoque_db"; 
    
    private static final String URL = "jdbc:mysql://localhost:3306/" + DATABASE_NAME;
    
    // Coloque seu usuário do MySQL (geralmente "root")
    private static final String USER = "root"; 
    
    // Coloque sua senha do MySQL (a senha que você usa no MySQL Workbench, etc)
    private static final String PASSWORD = "1234"; 
    // --------------------------------

    // IMPORTANTE: Este é o nome da classe do driver moderno do MySQL.
    // Como você adicionou o .jar, o Java saberá onde encontrá-lo.
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver"; 

    /**
     * Método que estabelece a conexão com o banco de dados.
     * @return Uma conexão (Connection) com o banco.
     */
    public static Connection getConexao() {
        Connection conexao = null;
        try {
            // Carrega o driver
            Class.forName(DRIVER); 
            
            // Tenta estabelecer a conexão
            conexao = DriverManager.getConnection(URL, USER, PASSWORD);
            
            return conexao;

        } catch (ClassNotFoundException e) {
            System.err.println("ERRO: Driver do banco de dados não encontrado.");
            System.err.println("Verifique se o .jar foi adicionado corretamente em 'Referenced Libraries'.");
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            System.err.println("ERRO: Falha ao conectar com o banco de dados.");
            System.err.println("Verifique se o 'USER', 'PASSWORD' e 'DATABASE_NAME' estão corretos e se o MySQL está rodando.");
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println("Tentando conectar ao banco de dados...");
        
        Connection testeConexao = ConexaoBD.getConexao();

        if (testeConexao != null) {
            System.out.println("------------------------------------");
            System.out.println("CONEXÃO BEM SUCEDIDA!");
            System.out.println("------------------------------------");
            try {
                // É importante fechar a conexão de teste
                testeConexao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("------------------------------------");
            System.err.println("FALHA NA CONEXÃO.");
            System.err.println("------------------------------------");
        }
    }
}
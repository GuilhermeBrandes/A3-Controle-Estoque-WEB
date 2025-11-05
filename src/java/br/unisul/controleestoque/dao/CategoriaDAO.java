package br.unisul.controleestoque.dao;

// Importa nossa classe de modelo
import br.unisul.controleestoque.model.Categoria; 
// Importa nossa classe de conexão
import br.unisul.controleestoque.util.ConexaoBD; 

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CategoriaDAO {

    /**
     * Método para salvar uma nova categoria no banco de dados.
     */
    public boolean salvar(Categoria categoria) {
        // String SQL para o INSERT
        String sql = "INSERT INTO Categoria (nome, tamanho, embalagem) VALUES (?, ?, ?)";
        
        Connection conexao = null;
        PreparedStatement ps = null; // Usamos PreparedStatement para evitar SQL Injection

        try {
            // 1. Obter a conexão com o banco
            conexao = ConexaoBD.getConexao();
            
            // 2. Preparar o comando SQL
            ps = conexao.prepareStatement(sql);
            
            // 3. Definir os valores dos parâmetros (?)
            ps.setString(1, categoria.getNome());
            ps.setString(2, categoria.getTamanho());
            ps.setString(3, categoria.getEmbalagem());
            
            // 4. Executar o comando
            ps.executeUpdate();
            
            System.out.println("Categoria '" + categoria.getNome() + "' salva com sucesso!");
            return true;

        } catch (SQLException e) {
            System.err.println("ERRO ao salvar categoria:");
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("--- Teste de Salvar Categoria ---");
        
        // 1. Criar um objeto CategoriaDAO
        CategoriaDAO dao = new CategoriaDAO();
        
        // 2. Criar um objeto Categoria (o que queremos salvar)
        Categoria catParaSalvar = new Categoria();
        catParaSalvar.setNome("Enlatados");
        catParaSalvar.setTamanho("Pequeno");
        catParaSalvar.setEmbalagem("Lata");
        
        // 3. Tentar salvar
        boolean sucesso = dao.salvar(catParaSalvar);
        
        if (sucesso) {
            System.out.println("TESTE BEM SUCEDIDO: Categoria salva!");
        } else {
            System.err.println("TESTE FALHOU: Categoria não foi salva.");
        }
        
        // Você pode adicionar outro para testar
        Categoria cat2 = new Categoria();
        cat2.setNome("Bebidas");
        cat2.setTamanho("Médio");
        cat2.setEmbalagem("Vidro");
        dao.salvar(cat2);
    }
}
package br.unisul.controleestoque.dao;

// Importa nossa classe de modelo
import br.unisul.controleestoque.model.Categoria; 
// Importa nossa classe de conexão
import br.unisul.controleestoque.util.ConexaoBD; 
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.Statement;
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

    /**
     * Método para listar todas as categorias do banco de dados.
     * @return Uma Lista (List) de objetos Categoria.
     */
    public List<Categoria> listar() {
        // String SQL para o SELECT
        String sql = "SELECT * FROM Categoria";
        
        // Lista que armazenará as categorias
        List<Categoria> categorias = new ArrayList<>();
        
        Connection conexao = null;
        Statement st = null; // Usaremos Statement simples, pois não há parâmetros
        ResultSet rs = null; // ResultSet armazena o resultado da consulta

        try {
            // 1. Obter a conexão
            conexao = ConexaoBD.getConexao();
            
            // 2. Criar o statement
            st = conexao.createStatement();
            
            // 3. Executar a consulta e obter o resultado
            rs = st.executeQuery(sql);
            
            // 4. Iterar sobre o resultado (enquanto houver próximas linhas)
            while (rs.next()) {
                // Criar um objeto Categoria para cada linha
                Categoria categoria = new Categoria();
                
                // Definir os atributos do objeto com os dados da linha
                categoria.setIdCategoria(rs.getInt("id_categoria"));
                categoria.setNome(rs.getString("nome"));
                categoria.setTamanho(rs.getString("tamanho"));
                categoria.setEmbalagem(rs.getString("embalagem"));
                
                // Adicionar o objeto à lista
                categorias.add(categoria);
            }
            
        } catch (SQLException e) {
            System.err.println("ERRO ao listar categorias:");
            e.printStackTrace();
        } finally {
            // 5. Fechar todos os recursos (em ordem)
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        // Retorna a lista de categorias (pode estar vazia se nada for encontrado)
        return categorias;    
    }

    /**
     * Método para atualizar uma categoria existente no banco de dados.
     * @param categoria O objeto Categoria com os dados atualizados (o ID é usado para saber qual atualizar).
     * @return true se a atualização foi bem-sucedida, false caso contrário.
     */
    public boolean atualizar(Categoria categoria) {
        // SQL para o UPDATE
        String sql = "UPDATE Categoria SET nome = ?, tamanho = ?, embalagem = ? WHERE id_categoria = ?";
        
        Connection conexao = null;
        PreparedStatement ps = null;

        try {
            // 1. Obter a conexão
            conexao = ConexaoBD.getConexao();
            
            // 2. Preparar o statement
            ps = conexao.prepareStatement(sql);
            
            // 3. Definir os valores dos parâmetros (?)
            ps.setString(1, categoria.getNome());
            ps.setString(2, categoria.getTamanho());
            ps.setString(3, categoria.getEmbalagem());
            ps.setInt(4, categoria.getIdCategoria()); // O ID é o último parâmetro (WHERE)
            
            // 4. Executar o update
            // executeUpdate() retorna o número de linhas afetadas
            int linhasAfetadas = ps.executeUpdate();
            
            // Retorna true se pelo menos 1 linha foi alterada
            return linhasAfetadas > 0; 

        } catch (SQLException e) {
            System.err.println("ERRO ao atualizar categoria:");
            e.printStackTrace();
            return false;
        } finally {
            // 5. Fechar os recursos
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

    /**
     * Método para excluir uma categoria do banco de dados pelo seu ID.
     * @param id O id_categoria da categoria a ser excluída.
     * @return true se a exclusão foi bem-sucedida, false caso contrário.
     */
    public boolean excluir(int id) {
        // SQL para o DELETE
        String sql = "DELETE FROM Categoria WHERE id_categoria = ?";
        
        Connection conexao = null;
        PreparedStatement ps = null;

        try {
            // 1. Obter a conexão
            conexao = ConexaoBD.getConexao();
            
            // 2. Preparar o statement
            ps = conexao.prepareStatement(sql);
            
            // 3. Definir o parâmetro (?)
            ps.setInt(1, id);
            
            // 4. Executar o delete
            int linhasAfetadas = ps.executeUpdate();
            
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.err.println("ERRO ao excluir categoria:");
            // Se der erro de chave estrangeira (FK), vai cair aqui
            e.printStackTrace();
            return false;
        } finally {
            // 5. Fechar os recursos
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
    

    /**
     * =================================================================
     * MÉTODO DE TESTE (Atualizado para testar CRUD completo)
     * =================================================================
     */
    public static void main(String[] args) {
        
        CategoriaDAO dao = new CategoriaDAO();

        // --- 1. LISTAR (Para ver o que temos) ---
        System.out.println("--- 1. LISTANDO CATEGORIAS (Estado Inicial) ---");
        List<Categoria> lista = dao.listar();
        for (Categoria cat : lista) {
            System.out.println("ID: " + cat.getIdCategoria() + ", Nome: " + cat.getNome());
        }
        
        // --- 2. ATUALIZAR (Vamos atualizar 'Frios e Laticínios') ---
        System.out.println("\n--- 2. ATUALIZANDO CATEGORIA 'Frios e Laticínios' ---");
        
        Categoria categoriaParaAtualizar = null;
        // Pega a lista mais recente
        lista = dao.listar(); 
        for (Categoria cat : lista) {
            if (cat.getNome().equals("Frios e Laticínios")) {
                categoriaParaAtualizar = cat;
                break;
            }
        }

        if (categoriaParaAtualizar != null) {
            // Mudamos o nome
            categoriaParaAtualizar.setNome("Frios e Laticínios - OFERTA");
            
            if (dao.atualizar(categoriaParaAtualizar)) {
                System.out.println("Atualização bem-sucedida!");
            } else {
                System.err.println("Falha na atualização.");
            }
        } else {
            System.out.println("Não encontrou 'Frios e Laticínios' para atualizar (Talvez já foi atualizado ou excluído).");
        }

        // --- 3. SALVAR (Vamos adicionar um item para ser excluído) ---
        System.out.println("\n--- 3. SALVANDO CATEGORIA 'Para Excluir' ---");
        Categoria catParaExcluir = new Categoria();
        catParaExcluir.setNome("Para Excluir");
        catParaExcluir.setTamanho("Pequeno");
        catParaExcluir.setEmbalagem("Lata");
        
        // Vamos evitar o erro de duplicata
        if (dao.salvar(catParaExcluir)) {
             System.out.println("Categoria 'Para Excluir' salva.");
        } else {
             System.out.println("Categoria 'Para Excluir' já existe.");
        }


        // --- 4. EXCLUIR (Vamos excluir o item que acabamos de criar) ---
        System.out.println("\n--- 4. EXCLUINDO CATEGORIA 'Para Excluir' ---");
        
        Integer idParaExcluir = null;
        lista = dao.listar(); // Pega a lista atualizada
        for (Categoria cat : lista) {
             if (cat.getNome().equals("Para Excluir")) {
                idParaExcluir = cat.getIdCategoria();
                break;
            }
        }
        
        if (idParaExcluir != null) {
            if (dao.excluir(idParaExcluir)) {
                System.out.println("Exclusão bem-sucedida!");
            } else {
                System.err.println("Falha na exclusão.");
            }
        } else {
            System.out.println("Não encontrou 'Para Excluir'.");
        }

        // --- 5. LISTAR DE NOVO (Para ver o resultado final) ---
        System.out.println("\n--- 5. LISTANDO CATEGORIAS (Resultado Final) ---");
        lista = dao.listar();
        for (Categoria cat : lista) {
            System.out.println("ID: " + cat.getIdCategoria() + ", Nome: " + cat.getNome());
        }
    }
}
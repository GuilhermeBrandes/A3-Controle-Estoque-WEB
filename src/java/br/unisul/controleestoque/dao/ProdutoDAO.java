package br.unisul.controleestoque.dao;

import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.Statement;
import br.unisul.controleestoque.model.Categoria;
import br.unisul.controleestoque.model.Produto;
import br.unisul.controleestoque.util.ConexaoBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProdutoDAO {

    /**
     * Método para salvar um novo produto no banco de dados.
     */
    public boolean salvar(Produto produto) {
        // SQL para o INSERT
        String sql = "INSERT INTO Produto (nome, preco_unitario, unidade, qtd_estoque, " + 
                     "qtd_minima_estoque, qtd_maxima_estoque, id_categoria) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        Connection conexao = null;
        PreparedStatement ps = null;

        try {
            // 1. Obter a conexão
            conexao = ConexaoBD.getConexao();
            
            // 2. Preparar o statement
            ps = conexao.prepareStatement(sql);
            
            // 3. Definir os valores dos parâmetros (?)
            ps.setString(1, produto.getNome());
            ps.setDouble(2, produto.getPrecoUnitario());
            ps.setString(3, produto.getUnidade());
            ps.setInt(4, produto.getQtdEstoque());
            ps.setInt(5, produto.getQtdMinimaEstoque());
            ps.setInt(6, produto.getQtdMaximaEstoque());
            
            // Ponto-chave: Pegamos o ID de dentro do objeto Categoria
            ps.setInt(7, produto.getCategoria().getIdCategoria()); 
            
            // 4. Executar o comando
            ps.executeUpdate();
            
            System.out.println("Produto '" + produto.getNome() + "' salvo com sucesso!");
            return true;

        } catch (SQLException e) {
            System.err.println("ERRO ao salvar produto:");
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
     * Método para listar todos os produtos do banco de dados,
     * incluindo os dados da Categoria associada (usando JOIN).
     * AN
     * @return Uma Lista (List) de objetos Produto.
     */
    public List<Produto> listar() {
        // SQL para o SELECT com JOIN
        // p.* significa "todas as colunas da tabela Produto"
        // c.nome, c.tamanho, c.embalagem são as colunas da Categoria
        String sql = "SELECT p.*, c.nome as cat_nome, c.tamanho, c.embalagem " +
                     "FROM Produto p " +
                     "INNER JOIN Categoria c ON p.id_categoria = c.id_categoria";
        
        List<Produto> produtos = new ArrayList<>();
        
        Connection conexao = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            // 1. Obter a conexão
            conexao = ConexaoBD.getConexao();
            
            // 2. Criar o statement
            st = conexao.createStatement();
            
            // 3. Executar a consulta
            rs = st.executeQuery(sql);
            
            // 4. Iterar sobre o resultado
            while (rs.next()) {
                // Primeiro, criamos o objeto Categoria com os dados do JOIN
                Categoria categoria = new Categoria();
                categoria.setIdCategoria(rs.getInt("id_categoria"));
                categoria.setNome(rs.getString("cat_nome")); // Usamos o alias "cat_nome"
                categoria.setTamanho(rs.getString("tamanho"));
                categoria.setEmbalagem(rs.getString("embalagem"));
                
                // Agora, criamos o objeto Produto
                Produto produto = new Produto();
                produto.setIdProduto(rs.getInt("id_produto"));
                produto.setNome(rs.getString("nome"));
                produto.setPrecoUnitario(rs.getDouble("preco_unitario"));
                produto.setUnidade(rs.getString("unidade"));
                produto.setQtdEstoque(rs.getInt("qtd_estoque"));
                produto.setQtdMinimaEstoque(rs.getInt("qtd_minima_estoque"));
                produto.setQtdMaximaEstoque(rs.getInt("qtd_maxima_estoque"));
                
                // Associamos a Categoria ao Produto
                produto.setCategoria(categoria);
                
                // Adicionamos o produto completo à lista
                produtos.add(produto);
            }
            
        } catch (SQLException e) {
            System.err.println("ERRO ao listar produtos:");
            e.printStackTrace();
        } finally {
            // 5. Fechar todos os recursos
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
        
        return produtos;
    }

    /**
     * Método para atualizar um produto existente no banco de dados.
     * @param produto O objeto Produto com os dados atualizados (o ID é usado para saber qual atualizar).
     * @return true se a atualização foi bem-sucedida, false caso contrário.
     */
    public boolean atualizar(Produto produto) {
        // SQL para o UPDATE
        String sql = "UPDATE Produto SET nome = ?, preco_unitario = ?, unidade = ?, " +
                     "qtd_estoque = ?, qtd_minima_estoque = ?, qtd_maxima_estoque = ?, " +
                     "id_categoria = ? WHERE id_produto = ?";
        
        Connection conexao = null;
        PreparedStatement ps = null;

        try {
            // 1. Obter a conexão
            conexao = ConexaoBD.getConexao();
            
            // 2. Preparar o statement
            ps = conexao.prepareStatement(sql);
            
            // 3. Definir os valores dos parâmetros (?)
            ps.setString(1, produto.getNome());
            ps.setDouble(2, produto.getPrecoUnitario());
            ps.setString(3, produto.getUnidade());
            ps.setInt(4, produto.getQtdEstoque());
            ps.setInt(5, produto.getQtdMinimaEstoque());
            ps.setInt(6, produto.getQtdMaximaEstoque());
            ps.setInt(7, produto.getCategoria().getIdCategoria());
            ps.setInt(8, produto.getIdProduto()); // O ID do produto é o último (WHERE)
            
            // 4. Executar o update
            int linhasAfetadas = ps.executeUpdate();
            
            return linhasAfetadas > 0; // Retorna true se 1 (ou mais) linha foi alterada

        } catch (SQLException e) {
            System.err.println("ERRO ao atualizar produto:");
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
     * Método para excluir um produto do banco de dados pelo seu ID.
     * @param id O id_produto do produto a ser excluído.
     * @return true se a exclusão foi bem-sucedida, false caso contrário.
     */
    public boolean excluir(int id) {
        // SQL para o DELETE
        String sql = "DELETE FROM Produto WHERE id_produto = ?";
        
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
            System.err.println("ERRO ao excluir produto:");
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
     * MÉTODO DE TESTE (Atualizado para testar CRUD completo de Produto)
     * =================================================================
     */
    public static void main(String[] args) {
        
        ProdutoDAO dao = new ProdutoDAO();

        // --- 1. LISTAR (Para ver o que temos) ---
        System.out.println("--- 1. LISTANDO PRODUTOS (Estado Inicial) ---");
        List<Produto> lista = dao.listar();
        for (Produto p : lista) {
            System.out.println("ID: " + p.getIdProduto() + ", Nome: " + p.getNome() + 
                               ", Cat: " + p.getCategoria().getNome());
        }
        
        // --- 2. ATUALIZAR (Vamos atualizar o 'Leite Integral') ---
        System.out.println("\n--- 2. ATUALIZANDO PRODUTO 'Leite Integral' ---");
        
        Produto produtoParaAtualizar = null;
        for (Produto p : lista) {
            if (p.getNome().equals("Leite Integral")) {
                produtoParaAtualizar = p;
                break;
            }
        }

        if (produtoParaAtualizar != null) {
            // Mudamos o preço
            produtoParaAtualizar.setPrecoUnitario(6.50);
            
            if (dao.atualizar(produtoParaAtualizar)) {
                System.out.println("Atualização bem-sucedida! (Novo preço: 6.50)");
            } else {
                System.err.println("Falha na atualização.");
            }
        } else {
            System.out.println("Não encontrou 'Leite Integral' para atualizar.");
        }

        // --- 3. EXCLUIR (Vamos excluir 'Iogurte Grego') ---
        System.out.println("\n--- 3. EXCLUINDO PRODUTO 'Iogurte Grego' ---");
        
        Integer idParaExcluir = null;
        lista = dao.listar(); // Pega a lista atualizada
        for (Produto p : lista) {
             if (p.getNome().equals("Iogurte Grego")) {
                idParaExcluir = p.getIdProduto();
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
            System.out.println("Não encontrou 'Iogurte Grego' para excluir (talvez já foi excluído).");
        }

        // --- 4. LISTAR DE NOVO (Para ver o resultado final) ---
        System.out.println("\n--- 4. LISTANDO PRODUTOS (Resultado Final) ---");
        lista = dao.listar();
        for (Produto p : lista) {
            System.out.println("ID: " + p.getIdProduto() + ", Nome: " + p.getNome() + 
                               ", Preço: " + p.getPrecoUnitario()); // Mostrando o preço novo
        }
    }
}
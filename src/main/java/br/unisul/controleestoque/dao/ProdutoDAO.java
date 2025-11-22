package br.unisul.controleestoque.dao;

import br.unisul.controleestoque.model.Categoria;
import br.unisul.controleestoque.model.Produto;
import br.unisul.controleestoque.util.ConexaoBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    public boolean salvar(Produto produto) {
        String sql = "INSERT INTO Produto (nome, preco_unitario, unidade, qtd_estoque, " + 
                     "qtd_minima_estoque, qtd_maxima_estoque, id_categoria) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            
            ps.setString(1, produto.getNome());
            ps.setDouble(2, produto.getPrecoUnitario());
            ps.setString(3, produto.getUnidade());
            ps.setInt(4, produto.getQtdEstoque());
            ps.setInt(5, produto.getQtdMinimaEstoque());
            ps.setInt(6, produto.getQtdMaximaEstoque());
            ps.setInt(7, produto.getCategoria().getIdCategoria());
            
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Produto> listar() {
        String sql = "SELECT p.*, c.nome as cat_nome, c.tamanho, c.embalagem " +
                     "FROM Produto p " +
                     "INNER JOIN Categoria c ON p.id_categoria = c.id_categoria";
        
        List<Produto> produtos = new ArrayList<>();
        
        try (Connection conexao = ConexaoBD.getConexao();
             Statement st = conexao.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            
            while (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setIdCategoria(rs.getInt("id_categoria"));
                categoria.setNome(rs.getString("cat_nome"));
                categoria.setTamanho(rs.getString("tamanho"));
                categoria.setEmbalagem(rs.getString("embalagem"));
                
                Produto produto = new Produto();
                produto.setIdProduto(rs.getInt("id_produto"));
                produto.setNome(rs.getString("nome"));
                produto.setPrecoUnitario(rs.getDouble("preco_unitario"));
                produto.setUnidade(rs.getString("unidade"));
                produto.setQtdEstoque(rs.getInt("qtd_estoque"));
                produto.setQtdMinimaEstoque(rs.getInt("qtd_minima_estoque"));
                produto.setQtdMaximaEstoque(rs.getInt("qtd_maxima_estoque"));
                produto.setCategoria(categoria);
                
                produtos.add(produto);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produtos;
    }

    public boolean atualizar(Produto produto) {
        String sql = "UPDATE Produto SET nome = ?, preco_unitario = ?, unidade = ?, " +
                     "qtd_estoque = ?, qtd_minima_estoque = ?, qtd_maxima_estoque = ?, " +
                     "id_categoria = ? WHERE id_produto = ?";
        
        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            
            ps.setString(1, produto.getNome());
            ps.setDouble(2, produto.getPrecoUnitario());
            ps.setString(3, produto.getUnidade());
            ps.setInt(4, produto.getQtdEstoque());
            ps.setInt(5, produto.getQtdMinimaEstoque());
            ps.setInt(6, produto.getQtdMaximaEstoque());
            ps.setInt(7, produto.getCategoria().getIdCategoria());
            ps.setInt(8, produto.getIdProduto());
            
            int linhasAfetadas = ps.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM Produto WHERE id_produto = ?";
        
        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            int linhasAfetadas = ps.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Produto buscarPorId(int id) {
        String sql = "SELECT * FROM Produto WHERE id_produto = ?";
        Produto produto = null;
        
        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                produto = new Produto();
                produto.setIdProduto(rs.getInt("id_produto"));
                produto.setNome(rs.getString("nome"));
                produto.setPrecoUnitario(rs.getDouble("preco_unitario"));
                produto.setUnidade(rs.getString("unidade"));
                produto.setQtdEstoque(rs.getInt("qtd_estoque"));
                produto.setQtdMinimaEstoque(rs.getInt("qtd_minima_estoque"));
                produto.setQtdMaximaEstoque(rs.getInt("qtd_maxima_estoque"));
                
                // --- CORREÇÃO DE SEGURANÇA ---
                // Criamos o objeto categoria apenas com o ID para evitar erros
                Categoria cat = new Categoria();
                int idCategoriaDoBanco = rs.getInt("id_categoria");
                cat.setIdCategoria(idCategoriaDoBanco); 
                produto.setCategoria(cat);
                // -----------------------------
            }
            rs.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produto;
    }

    public List<Produto> listarRelatorioPrecos() {
        String sql = "SELECT p.nome, p.preco_unitario, p.unidade, c.nome as cat_nome " +
                     "FROM Produto p " +
                     "INNER JOIN Categoria c ON p.id_categoria = c.id_categoria " +
                     "ORDER BY p.nome ASC";
        
        List<Produto> relatorio = new ArrayList<>();
        
        try (Connection conexao = ConexaoBD.getConexao();
             Statement st = conexao.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            
            while (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setNome(rs.getString("cat_nome"));
                
                Produto produto = new Produto();
                produto.setNome(rs.getString("nome"));
                produto.setPrecoUnitario(rs.getDouble("preco_unitario"));
                produto.setUnidade(rs.getString("unidade"));
                produto.setCategoria(categoria);
                
                relatorio.add(produto);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return relatorio;
    }
    
    public List<Produto> listarAbaixoDoMinimo() {
        String sql = "SELECT p.*, c.nome as cat_nome, c.tamanho, c.embalagem " +
                     "FROM Produto p " +
                     "INNER JOIN Categoria c ON p.id_categoria = c.id_categoria " +
                     "WHERE p.qtd_estoque < p.qtd_minima_estoque";
        
        List<Produto> lista = new ArrayList<>();
        
        try (Connection conexao = ConexaoBD.getConexao();
             Statement st = conexao.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            
            while (rs.next()) {
                Categoria cat = new Categoria();
                cat.setNome(rs.getString("cat_nome"));
                
                Produto p = new Produto();
                p.setIdProduto(rs.getInt("id_produto"));
                p.setNome(rs.getString("nome"));
                p.setPrecoUnitario(rs.getDouble("preco_unitario"));
                p.setUnidade(rs.getString("unidade"));
                p.setQtdEstoque(rs.getInt("qtd_estoque"));
                p.setQtdMinimaEstoque(rs.getInt("qtd_minima_estoque"));
                p.setCategoria(cat);
                
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
package br.unisul.controleestoque.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.unisul.controleestoque.model.Categoria;
import br.unisul.controleestoque.util.ConexaoBD;

public class CategoriaDAO {

    public boolean salvar(Categoria categoria) {
        String sql = "INSERT INTO Categoria (nome, tamanho, embalagem) VALUES (?, ?, ?)";
        
        Connection conexao = null;
        PreparedStatement ps = null;

        try {
            conexao = ConexaoBD.getConexao();
            ps = conexao.prepareStatement(sql);
            
            ps.setString(1, categoria.getNome());
            ps.setString(2, categoria.getTamanho());
            ps.setString(3, categoria.getEmbalagem());
            
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (conexao != null) conexao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Categoria> listar() {
        String sql = "SELECT * FROM Categoria";
        List<Categoria> categorias = new ArrayList<>();
        
        Connection conexao = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            conexao = ConexaoBD.getConexao();
            st = conexao.createStatement();
            rs = st.executeQuery(sql);
            
            while (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setIdCategoria(rs.getInt("id_categoria"));
                categoria.setNome(rs.getString("nome"));
                categoria.setTamanho(rs.getString("tamanho"));
                categoria.setEmbalagem(rs.getString("embalagem"));
                categorias.add(categoria);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (conexao != null) conexao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return categorias;
    }

    public boolean atualizar(Categoria categoria) {
        String sql = "UPDATE Categoria SET nome = ?, tamanho = ?, embalagem = ? WHERE id_categoria = ?";
        
        Connection conexao = null;
        PreparedStatement ps = null;

        try {
            conexao = ConexaoBD.getConexao();
            ps = conexao.prepareStatement(sql);
            
            ps.setString(1, categoria.getNome());
            ps.setString(2, categoria.getTamanho());
            ps.setString(3, categoria.getEmbalagem());
            ps.setInt(4, categoria.getIdCategoria());
            
            int linhasAfetadas = ps.executeUpdate();
            return linhasAfetadas > 0; 

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (conexao != null) conexao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM Categoria WHERE id_categoria = ?";
        
        Connection conexao = null;
        PreparedStatement ps = null;

        try {
            conexao = ConexaoBD.getConexao();
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            
            int linhasAfetadas = ps.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (conexao != null) conexao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Categoria buscarPorId(int id) {
        String sql = "SELECT * FROM Categoria WHERE id_categoria = ?";
        Categoria categoria = null;
        
        Connection conexao = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conexao = ConexaoBD.getConexao();
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                categoria = new Categoria();
                categoria.setIdCategoria(rs.getInt("id_categoria"));
                categoria.setNome(rs.getString("nome"));
                categoria.setTamanho(rs.getString("tamanho"));
                categoria.setEmbalagem(rs.getString("embalagem"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conexao != null) conexao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return categoria;
    }
}
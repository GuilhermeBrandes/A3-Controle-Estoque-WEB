package br.unisul.controleestoque.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.unisul.controleestoque.dao.CategoriaDAO;
import br.unisul.controleestoque.dao.ProdutoDAO;
import br.unisul.controleestoque.model.Categoria;
import br.unisul.controleestoque.model.Produto;

@WebServlet("/ProdutoServlet")
public class ProdutoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private ProdutoDAO produtoDAO;
    private CategoriaDAO categoriaDAO;

    @Override
    public void init() {
        produtoDAO = new ProdutoDAO();
        categoriaDAO = new CategoriaDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");

        String idStr = request.getParameter("id");
        String nome = request.getParameter("nome");
        String precoStr = request.getParameter("preco");
        String unidade = request.getParameter("unidade");
        String qtdEstoqueStr = request.getParameter("qtdEstoque");
        String qtdMinStr = request.getParameter("qtdMin");
        String qtdMaxStr = request.getParameter("qtdMax");
        String idCategoriaStr = request.getParameter("idCategoria");
        
        Produto produto = new Produto();
        produto.setNome(nome);
        produto.setPrecoUnitario(Double.parseDouble(precoStr));
        produto.setUnidade(unidade);
        produto.setQtdEstoque(Integer.parseInt(qtdEstoqueStr));
        produto.setQtdMinimaEstoque(Integer.parseInt(qtdMinStr));
        produto.setQtdMaximaEstoque(Integer.parseInt(qtdMaxStr));
        
        Categoria cat = new Categoria();
        cat.setIdCategoria(Integer.parseInt(idCategoriaStr));
        produto.setCategoria(cat);
        
        try {
            if (idStr != null && !idStr.isEmpty()) {
                produto.setIdProduto(Integer.parseInt(idStr));
                produtoDAO.atualizar(produto);
            } else {
                produtoDAO.salvar(produto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("ProdutoServlet");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String acao = request.getParameter("acao");
        String idStr = request.getParameter("id");
        
        // --- LOGS DE ESPIÃO ---
        System.out.println("=======================================");
        System.out.println("ProdutoServlet: doGet chamado!");
        System.out.println("Ação: " + acao);
        System.out.println("ID: " + idStr);
        // ----------------------
        
        if ("delete".equals(acao)) {
            if (idStr != null) {
                produtoDAO.excluir(Integer.parseInt(idStr));
            }
            response.sendRedirect("ProdutoServlet");
            return;
        }
        
        if ("editar".equals(acao)) {
            System.out.println("-> Entrou no IF 'editar'"); // LOG
            if (idStr != null) {
                int id = Integer.parseInt(idStr);
                Produto prodEdicao = produtoDAO.buscarPorId(id);
                
                if (prodEdicao != null) {
                    System.out.println("-> Produto encontrado: " + prodEdicao.getNome()); // LOG
                    request.setAttribute("produtoEdicao", prodEdicao);
                } else {
                    System.out.println("-> ERRO: Produto veio NULO do Banco para o ID: " + id); // LOG
                }
            }
        } else {
             System.out.println("-> NÃO entrou no IF 'editar'"); // LOG
        }

        List<Produto> listaProdutos = produtoDAO.listar();
        request.setAttribute("listaProdutos", listaProdutos);
        
        List<Categoria> listaCategorias = categoriaDAO.listar();
        request.setAttribute("listaCategorias", listaCategorias);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/produto.jsp");
        dispatcher.forward(request, response);
    }
}
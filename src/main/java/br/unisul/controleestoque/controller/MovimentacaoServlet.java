package br.unisul.controleestoque.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.unisul.controleestoque.dao.MovimentacaoDAO;
import br.unisul.controleestoque.dao.ProdutoDAO;
import br.unisul.controleestoque.model.Movimentacao;
import br.unisul.controleestoque.model.Produto;

@WebServlet("/MovimentacaoServlet")
public class MovimentacaoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private MovimentacaoDAO movimentacaoDAO;
    private ProdutoDAO produtoDAO;

    @Override
    public void init() {
        movimentacaoDAO = new MovimentacaoDAO();
        produtoDAO = new ProdutoDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");

        String idProdutoStr = request.getParameter("idProduto");
        String tipo = request.getParameter("tipo");
        String quantidadeStr = request.getParameter("quantidade");
        
        Produto produto = new Produto();
        produto.setIdProduto(Integer.parseInt(idProdutoStr));
        
        Movimentacao mov = new Movimentacao();
        mov.setProduto(produto);
        mov.setTipoMovimentacao(tipo);
        mov.setQuantidade(Integer.parseInt(quantidadeStr));
        
        try {
            movimentacaoDAO.salvar(mov);
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("MovimentacaoServlet");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        List<Produto> listaProdutos = produtoDAO.listar();
        request.setAttribute("listaProdutos", listaProdutos);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/movimentacao.jsp");
        dispatcher.forward(request, response);
    }
}
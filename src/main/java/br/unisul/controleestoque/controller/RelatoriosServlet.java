package br.unisul.controleestoque.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.unisul.controleestoque.dao.ProdutoDAO;
import br.unisul.controleestoque.model.Produto;

@WebServlet("/RelatoriosServlet")
public class RelatoriosServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private ProdutoDAO produtoDAO;

    @Override
    public void init() {
        produtoDAO = new ProdutoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String tipo = request.getParameter("tipo");
        List<Produto> listaResultados = null;
        
        // Lógica de Seleção do Relatório
        if ("precos".equals(tipo)) {
            // Relatório 1: Lista de Preços (Ordem Alfabética)
            listaResultados = produtoDAO.listarRelatorioPrecos();
            request.setAttribute("tituloRelatorio", "Lista de Preços");
            
        } else if ("balanco".equals(tipo)) {
            // Relatório 2: Balanço Físico/Financeiro (Todos os produtos + Cálculos)
            listaResultados = produtoDAO.listar(); // O listar normal já serve
            request.setAttribute("tituloRelatorio", "Balanço Físico/Financeiro");
            
        } else if ("minimo".equals(tipo)) {
            // Relatório 3: Abaixo do Mínimo
            listaResultados = produtoDAO.listarAbaixoDoMinimo();
            request.setAttribute("tituloRelatorio", "Produtos Abaixo do Estoque Mínimo");
            
        } else {
            request.setAttribute("tituloRelatorio", "Selecione um Relatório");
        }

        request.setAttribute("listaResultados", listaResultados);
        request.setAttribute("tipoRelatorio", tipo);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/relatorios.jsp");
        dispatcher.forward(request, response);
    }
}
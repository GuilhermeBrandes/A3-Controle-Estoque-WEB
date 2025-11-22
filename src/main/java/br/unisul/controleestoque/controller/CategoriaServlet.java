package br.unisul.controleestoque.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.unisul.controleestoque.dao.CategoriaDAO;
import br.unisul.controleestoque.model.Categoria;

import java.io.IOException;
import java.util.List;

@WebServlet("/CategoriaServlet")
public class CategoriaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private CategoriaDAO categoriaDAO;

    @Override
    public void init() {
        categoriaDAO = new CategoriaDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        
        System.out.println("Servlet: POST recebido.");

        String nome = request.getParameter("nome");
        String tamanho = request.getParameter("tamanho");
        String embalagem = request.getParameter("embalagem");
        
        Categoria novaCategoria = new Categoria();
        novaCategoria.setNome(nome);
        novaCategoria.setTamanho(tamanho);
        novaCategoria.setEmbalagem(embalagem);
        
        try {
            categoriaDAO.salvar(novaCategoria);
            System.out.println("Servlet: Categoria salva via DAO.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("CategoriaServlet");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String acao = request.getParameter("acao");
        
        if ("delete".equals(acao)) {
            String idStr = request.getParameter("id");
            if (idStr != null) {
                try {
                    int id = Integer.parseInt(idStr);
                    categoriaDAO.excluir(id);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            response.sendRedirect("CategoriaServlet");
            return;
        }

        System.out.println("Servlet: GET recebido. Buscando lista...");

        List<Categoria> listaCategorias = categoriaDAO.listar();
        
        request.setAttribute("listaCategorias", listaCategorias);
        
        String jsp = "/pages/categoria.jsp"; 
        RequestDispatcher dispatcher = request.getRequestDispatcher(jsp);
        dispatcher.forward(request, response);
        
        System.out.println("Servlet: Lista enviada para " + jsp);
    }
}
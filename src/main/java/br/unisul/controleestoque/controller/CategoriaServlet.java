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
import br.unisul.controleestoque.model.Categoria;

@WebServlet("/CategoriaServlet")
public class CategoriaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private CategoriaDAO categoriaDAO;

    @Override
    public void init() {
        categoriaDAO = new CategoriaDAO();
    }

    // --- RECEBE DADOS DO FORMULÁRIO (SALVAR/ATUALIZAR) ---
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");

        String idStr = request.getParameter("id");
        String nome = request.getParameter("nome");
        String tamanho = request.getParameter("tamanho");
        String embalagem = request.getParameter("embalagem");
        
        Categoria categoria = new Categoria();
        categoria.setNome(nome);
        categoria.setTamanho(tamanho);
        categoria.setEmbalagem(embalagem);
        
        try {
            // Se tem ID, é EDIÇÃO
            if (idStr != null && !idStr.isEmpty()) {
                categoria.setIdCategoria(Integer.parseInt(idStr));
                categoriaDAO.atualizar(categoria);
            } 
            // Se não tem ID, é NOVO CADASTRO
            else {
                categoriaDAO.salvar(categoria);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("CategoriaServlet");
    }

    // --- CONTROLA A TELA (LISTAR, EXCLUIR, PREPARAR EDIÇÃO) ---
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String acao = request.getParameter("acao");
        String idStr = request.getParameter("id");
        
        // Lógica de Exclusão
        if ("delete".equals(acao) && idStr != null) {
            categoriaDAO.excluir(Integer.parseInt(idStr));
            response.sendRedirect("CategoriaServlet");
            return;
        }
        
        // Lógica de Edição (Carrega os dados para o formulário)
        if ("editar".equals(acao) && idStr != null) {
            Categoria catEdicao = categoriaDAO.buscarPorId(Integer.parseInt(idStr));
            request.setAttribute("categoriaEdicao", catEdicao);
        }

        // Sempre lista as categorias no final
        List<Categoria> listaCategorias = categoriaDAO.listar();
        request.setAttribute("listaCategorias", listaCategorias);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/categoria.jsp");
        dispatcher.forward(request, response);
    }
}
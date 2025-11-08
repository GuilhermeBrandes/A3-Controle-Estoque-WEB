package br.unisul.controleestoque.controller;

// Importações do Servlet
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet; // Importante!
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Importações do projeto
import br.unisul.controleestoque.dao.CategoriaDAO;
import br.unisul.controleestoque.model.Categoria;

// Outras importações
import java.io.IOException;
import java.util.List;

/**
 * Servlet (Controller) que gerencia todas as requisições
 * relacionadas a Categoria.
 * * @WebServlet define a URL de acesso a este servlet.
 * "/CategoriaServlet" será o 'action' dos nossos formulários JSP.
 */
//@WebServlet("/CategoriaServlet")
public class CategoriaServlet extends HttpServlet {
//    private static final long serialVersionUID = 1L;
    
    private CategoriaDAO categoriaDAO;

    // init() é chamado uma vez quando o servlet é carregado
    @Override
    public void init() {
        // Instanciamos o DAO uma vez
        categoriaDAO = new CategoriaDAO();
    }

    /**
     * O método doPost é usado para requisições que enviam dados
     * (ex: formulários de Salvar, Atualizar, Excluir).
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("Servlet: POST recebido.");

        // 1. Pegar os dados do formulário JSP
        // O "request.getParameter" pega o valor do campo "name" do input
        String nome = request.getParameter("nome");
        String tamanho = request.getParameter("tamanho");
        String embalagem = request.getParameter("embalagem");
        
        // 2. Criar o objeto Categoria
        Categoria novaCategoria = new Categoria();
        novaCategoria.setNome(nome);
        novaCategoria.setTamanho(tamanho);
        novaCategoria.setEmbalagem(embalagem);
        
        // 3. Chamar o DAO para salvar
        try {
            categoriaDAO.salvar(novaCategoria);
            System.out.println("Servlet: Categoria salva via DAO.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 4. Redirecionar o usuário de volta para a lista
        // Isso força o doGet() a ser chamado, atualizando a lista.
        response.sendRedirect("CategoriaServlet");
    }

    /**
     * O método doGet é usado para requisições que buscam dados
     * (ex: carregar a página de lista).
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("Servlet: GET recebido. Buscando lista...");

        // 1. Chamar o DAO para buscar a lista de categorias
        List<Categoria> listaCategorias = categoriaDAO.listar();
        
        // 2. "Pendurar" a lista na requisição
        // Isso torna a "listaCategorias" disponível para o JSP
        request.setAttribute("listaCategorias", listaCategorias);
        
        // 3. Encaminhar a requisição para a página JSP (a View)
        // (Ainda vamos criar este arquivo)
        String jsp = "/pages/categoria.jsp"; 
        RequestDispatcher dispatcher = request.getRequestDispatcher(jsp);
        dispatcher.forward(request, response);
        
        System.out.println("Servlet: Lista enviada para " + jsp);
    }
}
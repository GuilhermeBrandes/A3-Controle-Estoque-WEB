<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="br.unisul.controleestoque.model.Categoria" %>

<%
    // Captura os dados se estivermos em modo de edição
    Categoria catEdicao = (Categoria) request.getAttribute("categoriaEdicao");
    
    String idVal = (catEdicao != null) ? String.valueOf(catEdicao.getIdCategoria()) : "";
    String nomeVal = (catEdicao != null) ? catEdicao.getNome() : "";
    String tamVal = (catEdicao != null) ? catEdicao.getTamanho() : "";
    String embVal = (catEdicao != null) ? catEdicao.getEmbalagem() : "";
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gerenciar Categorias</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        h1, h2 { color: #333; }
        form { background: #f4f4f4; padding: 20px; border-radius: 8px; width: 50%; min-width: 300px; }
        label { display: block; margin-top: 10px; font-weight: bold; }
        input, select { padding: 8px; width: 100%; margin-top: 5px; box-sizing: border-box; }
        button { margin-top: 20px; padding: 10px 20px; background-color: #007bff; color: white; border: none; cursor: pointer; font-size: 16px; border-radius: 4px; }
        button:hover { background-color: #0056b3; }
        table { width: 100%; border-collapse: collapse; margin-top: 30px; }
        th, td { border: 1px solid #ddd; padding: 10px; text-align: left; }
        th { background-color: #f2f2f2; }
        a { text-decoration: none; color: #007bff; margin-right: 10px; }
        a:hover { text-decoration: underline; }
        .btn-cancelar { background-color: #6c757d; margin-left: 10px; text-decoration: none; color: white; padding: 10px 20px; border-radius: 4px; font-size: 13px; }
        .btn-cancelar:hover { background-color: #5a6268; text-decoration: none; }
    </style>
</head>
<body>

    <h1>Gestão de Categorias</h1>

    <h2><%= (catEdicao != null) ? "Editar Categoria" : "Nova Categoria" %></h2>
    
    <form action="CategoriaServlet" method="POST">
        <input type="hidden" name="id" value="<%= idVal %>">
        
        <label for="nome">Nome:</label>
        <input type="text" id="nome" name="nome" value="<%= nomeVal %>" required>
        
        <label for="tamanho">Tamanho:</label>
        <select id="tamanho" name="tamanho">
            <option value="Pequeno" <%= "Pequeno".equals(tamVal) ? "selected" : "" %>>Pequeno</option>
            <option value="Médio" <%= "Médio".equals(tamVal) ? "selected" : "" %>>Médio</option>
            <option value="Grande" <%= "Grande".equals(tamVal) ? "selected" : "" %>>Grande</option>
        </select>
        
        <label for="embalagem">Embalagem:</label>
        <select id="embalagem" name="embalagem">
            <option value="Lata" <%= "Lata".equals(embVal) ? "selected" : "" %>>Lata</option>
            <option value="Vidro" <%= "Vidro".equals(embVal) ? "selected" : "" %>>Vidro</option>
            <option value="Plástico" <%= "Plástico".equals(embVal) ? "selected" : "" %>>Plástico</option>
        </select>
        
        <div style="margin-top: 20px;">
            <button type="submit"><%= (catEdicao != null) ? "Atualizar" : "Salvar" %></button>
            
            <% if(catEdicao != null) { %>
                <a href="CategoriaServlet" class="btn-cancelar">Cancelar</a>
            <% } %>
        </div>
    </form>

    <hr>

    <h2>Categorias Cadastradas</h2>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Tamanho</th>
                <th>Embalagem</th>
                <th>Ações</th>
            </tr>
        </thead>
        <tbody>
            <%
                List<Categoria> listaCategorias = (List<Categoria>) request.getAttribute("listaCategorias");
                if (listaCategorias != null && !listaCategorias.isEmpty()) {
                    for (Categoria cat : listaCategorias) {
            %>
                        <tr>
                            <td><%= cat.getIdCategoria() %></td>
                            <td><%= cat.getNome() %></td>
                            <td><%= cat.getTamanho() %></td>
                            <td><%= cat.getEmbalagem() %></td>
                            <td>
                                <a href="CategoriaServlet?acao=editar&id=<%= cat.getIdCategoria() %>">Editar</a>
                                <a href="CategoriaServlet?acao=delete&id=<%= cat.getIdCategoria() %>" 
                                   onclick="return confirm('Tem certeza que deseja excluir?');" style="color: red;">
                                   Excluir
                                </a>
                            </td>
                        </tr>
            <%
                    } 
                } else {
            %>
                <tr><td colspan="5">Nenhuma categoria cadastrada.</td></tr>
            <% } %>
        </tbody>
    </table>
    
    <br>
    <a href="../A3-Controle-Estoque-WEB" style="font-size: 1.2em;">Voltar ao Menu Principal</a>

</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="br.unisul.controleestoque.model.Categoria" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gerenciar Categorias</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        h1, h2 { color: #333; }
        form { background: #f4f4f4; padding: 15px; border-radius: 8px; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        input[type="text"] { width: 250px; padding: 5px; }
        select { padding: 5px; }
        button { padding: 8px 12px; background-color: #007bff; color: white; border: none; cursor: pointer; }
    </style>
</head>
<body>

    <h1>Gestão de Categorias</h1>

    <h2>Nova Categoria</h2>
    <form action="CategoriaServlet" method="POST">
        <div>
            <label for="nome">Nome:</label>
            <input type="text" id="nome" name="nome" required>
        </div>
        <br>
        <div>
            <label for="tamanho">Tamanho:</label>
            <select id="tamanho" name="tamanho">
                <option value="Pequeno">Pequeno</option>
                <option value="Médio">Médio</option>
                <option value="Grande">Grande</option>
            </select>
        </div>
        <br>
        <div>
            <label for="embalagem">Embalagem:</label>
            <select id="embalagem" name="embalagem">
                <option value="Lata">Lata</option>
                <option value="Vidro">Vidro</option>
                <option value="Plástico">Plástico</option>
            </select>
        </div>
        <br>
        <button type="submit">Salvar Categoria</button>
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
                </tr>
        </thead>
        <tbody>
            <%
                // Pega a lista que o Servlet "pendurou" na requisição
                List<Categoria> listaCategorias = (List<Categoria>) request.getAttribute("listaCategorias");
                
                // Se a lista não for nula e não estiver vazia, faz o loop
                if (listaCategorias != null && !listaCategorias.isEmpty()) {
                    for (Categoria cat : listaCategorias) {
            %>
                        <tr>
                            <td><%= cat.getIdCategoria() %></td>
                            <td><%= cat.getNome() %></td>
                            <td><%= cat.getTamanho() %></td>
                            <td><%= cat.getEmbalagem() %></td>
                        </tr>
            <%
                    } // Fim do loop
                } else {
            %>
                <tr>
                    <td colspan="4">Nenhuma categoria cadastrada.</td>
                </tr>
            <%
                } // Fim do else
            %>
        </tbody>
    </table>

</body>
</html>
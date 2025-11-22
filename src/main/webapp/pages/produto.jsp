<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="br.unisul.controleestoque.model.Produto" %>
<%@ page import="br.unisul.controleestoque.model.Categoria" %>

<%
    // Bloco de segurança para evitar erro se a categoria for nula
    Produto prodEdicao = (Produto) request.getAttribute("produtoEdicao");
    
    String idVal = (prodEdicao != null) ? String.valueOf(prodEdicao.getIdProduto()) : "";
    String nomeVal = (prodEdicao != null) ? prodEdicao.getNome() : "";
    String precoVal = (prodEdicao != null) ? String.valueOf(prodEdicao.getPrecoUnitario()) : "";
    String unidadeVal = (prodEdicao != null) ? prodEdicao.getUnidade() : "";
    String qtdEstVal = (prodEdicao != null) ? String.valueOf(prodEdicao.getQtdEstoque()) : "0";
    String qtdMinVal = (prodEdicao != null) ? String.valueOf(prodEdicao.getQtdMinimaEstoque()) : "0";
    String qtdMaxVal = (prodEdicao != null) ? String.valueOf(prodEdicao.getQtdMaximaEstoque()) : "0";
    
    int idCatVal = 0;
    if (prodEdicao != null && prodEdicao.getCategoria() != null) {
        idCatVal = prodEdicao.getCategoria().getIdCategoria();
    }

    List<Categoria> listaCats = (List<Categoria>) request.getAttribute("listaCategorias");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gerenciar Produtos</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        h1, h2 { color: #333; }
        form { background: #f4f4f4; padding: 15px; border-radius: 8px; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        input, select { padding: 5px; margin-bottom: 10px; width: 200px; }
        label { display: inline-block; width: 150px; }
        button { padding: 8px 12px; background-color: #28a745; color: white; border: none; cursor: pointer; }
        a { margin-right: 10px; text-decoration: none; color: #007bff; }
    </style>
</head>
<body>

    <h1>Gestão de Produtos</h1>

    <h2><%= (prodEdicao != null) ? "Editar Produto" : "Novo Produto" %></h2>
    
    <form action="ProdutoServlet" method="POST">
        <input type="hidden" name="id" value="<%= idVal %>">
        
        <label>Nome:</label>
        <input type="text" name="nome" value="<%= nomeVal %>" required><br>
        
        <label>Preço Unitário:</label>
        <input type="number" step="0.01" name="preco" value="<%= precoVal %>" required><br>
        
        <label>Unidade:</label>
        <input type="text" name="unidade" value="<%= unidadeVal %>" required><br>
        
        <label>Qtd. Estoque:</label>
        <input type="number" name="qtdEstoque" value="<%= qtdEstVal %>" required><br>
        
        <label>Qtd. Mínima:</label>
        <input type="number" name="qtdMin" value="<%= qtdMinVal %>" required><br>
        
        <label>Qtd. Máxima:</label>
        <input type="number" name="qtdMax" value="<%= qtdMaxVal %>" required><br>
        
        <label>Categoria:</label>
        <select name="idCategoria" required>
            <option value="">Selecione...</option>
            <% 
                if (listaCats != null) {
                    for (Categoria c : listaCats) {
                        String selected = (c.getIdCategoria() == idCatVal) ? "selected" : "";
            %>
                        <option value="<%= c.getIdCategoria() %>" <%= selected %>>
                            <%= c.getNome() %>
                        </option>
            <% 
                    }
                } 
            %>
        </select><br>

        <button type="submit"><%= (prodEdicao != null) ? "Atualizar" : "Salvar" %></button>
        <% if(prodEdicao != null) { %>
            <a href="ProdutoServlet" style="margin-left: 10px;">Cancelar</a>
        <% } %>
    </form>

    <hr>

    <h2>Produtos Cadastrados</h2>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Preço</th>
                <th>Unidade</th>
                <th>Estoque</th>
                <th>Min/Max</th>
                <th>Categoria</th>
                <th>Ações</th>
            </tr>
        </thead>
        <tbody>
            <%
                List<Produto> listaProdutos = (List<Produto>) request.getAttribute("listaProdutos");
                if (listaProdutos != null && !listaProdutos.isEmpty()) {
                    for (Produto p : listaProdutos) {
            %>
                        <tr>
                            <td><%= p.getIdProduto() %></td>
                            <td><%= p.getNome() %></td>
                            <td>R$ <%= p.getPrecoUnitario() %></td>
                            <td><%= p.getUnidade() %></td>
                            <td><%= p.getQtdEstoque() %></td>
                            <td><%= p.getQtdMinimaEstoque() %> / <%= p.getQtdMaximaEstoque() %></td>
                            <td><%= (p.getCategoria() != null) ? p.getCategoria().getNome() : "Sem Categoria" %></td>
                            <td>
                                <a href="ProdutoServlet?acao=editar&id=<%= p.getIdProduto() %>">Editar</a>
                                <a href="ProdutoServlet?acao=delete&id=<%= p.getIdProduto() %>" 
                                   onclick="return confirm('Excluir este produto?');" style="color: red;">
                                   Excluir
                                </a>
                            </td>
                        </tr>
            <%
                    } 
                } else {
            %>
                <tr><td colspan="8">Nenhum produto cadastrado.</td></tr>
            <% } %>
        </tbody>
    </table>

	<br><br>
    <a href="../A3-Controle-Estoque-WEB">Voltar ao Menu Principal</a>

</body>
</html>
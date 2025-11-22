<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="br.unisul.controleestoque.model.Produto" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Movimentação de Estoque</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        h1, h2 { color: #333; }
        form { background: #f4f4f4; padding: 20px; border-radius: 8px; width: 50%; min-width: 300px; }
        label { display: block; margin-top: 10px; font-weight: bold; }
        input, select { padding: 8px; width: 100%; margin-top: 5px; box-sizing: border-box; }
        button { margin-top: 20px; padding: 10px 20px; background-color: #28a745; color: white; border: none; cursor: pointer; font-size: 16px; border-radius: 4px; }
        button:hover { background-color: #218838; }
        a { display: block; margin-top: 20px; text-decoration: none; color: #007bff; }
        .info { font-size: 0.9em; color: #666; margin-top: 5px; }
    </style>
</head>
<body>

    <h1>Movimentação de Estoque</h1>
    
    <form action="MovimentacaoServlet" method="POST">
        
        <label for="idProduto">Produto:</label>
        <select name="idProduto" id="idProduto" required>
            <option value="">Selecione um produto...</option>
            <%
                // Recebe a lista de produtos enviada pelo Servlet
                List<Produto> lista = (List<Produto>) request.getAttribute("listaProdutos");
                if (lista != null) {
                    for (Produto p : lista) {
            %>
                        <option value="<%= p.getIdProduto() %>">
                            <%= p.getNome() %> (Estoque Atual: <%= p.getQtdEstoque() %> <%= p.getUnidade() %>)
                        </option>
            <%
                    }
                }
            %>
        </select>

        <label for="tipo">Tipo de Movimentação:</label>
        <select name="tipo" id="tipo" required>
            <option value="Entrada">Entrada (Adicionar ao Estoque)</option>
            <option value="Saída">Saída (Remover do Estoque)</option>
        </select>

        <label for="quantidade">Quantidade:</label>
        <input type="number" name="quantidade" id="quantidade" min="1" required>
        <div class="info">Digite apenas números positivos.</div>

        <button type="submit">Registrar Movimentação</button>
    </form>

    <a href="../A3-Controle-Estoque-WEB">Voltar ao Menu Principal</a>

</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="br.unisul.controleestoque.model.Produto" %>

<%
    String tipo = (String) request.getAttribute("tipoRelatorio");
    String titulo = (String) request.getAttribute("tituloRelatorio");
    List<Produto> lista = (List<Produto>) request.getAttribute("listaResultados");
    
    // Variáveis para totais do Balanço
    double totalGeralFinanceiro = 0;
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Relatórios</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        h1 { color: #333; }
        .botoes { margin-bottom: 20px; padding: 10px; background: #f4f4f4; border-radius: 8px; }
        .btn { text-decoration: none; color: white; background-color: #007bff; padding: 10px 15px; margin-right: 10px; border-radius: 5px; display: inline-block; }
        .btn:hover { background-color: #0056b3; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        .total-row { font-weight: bold; background-color: #e9ecef; }
        .alerta { color: red; font-weight: bold; }
        a { text-decoration: none; color: #007bff; }
    </style>
</head>
<body>

    <h1>Relatórios Gerenciais</h1>
    
    <div class="botoes">
        <a href="RelatoriosServlet?tipo=precos" class="btn">1. Lista de Preços</a>
        <a href="RelatoriosServlet?tipo=balanco" class="btn">2. Balanço Financeiro</a>
        <a href="RelatoriosServlet?tipo=minimo" class="btn" style="background-color: #dc3545;">3. Abaixo do Mínimo</a>
    </div>

    <h2><%= (titulo != null) ? titulo : "Selecione uma opção acima" %></h2>

    <% if (lista != null && !lista.isEmpty()) { %>
        
        <table>
            <thead>
                <tr>
                    <th>Produto</th>
                    <th>Categoria</th>
                    
                    <% if ("precos".equals(tipo)) { %>
                        <th>Unidade</th>
                        <th>Preço Unitário</th>
                    <% } %>
                    
                    <% if ("balanco".equals(tipo)) { %>
                        <th>Qtd. Estoque</th>
                        <th>Preço Unit.</th>
                        <th>Valor Total (Qtd * Preço)</th>
                    <% } %>
                    
                    <% if ("minimo".equals(tipo)) { %>
                        <th>Qtd. Atual</th>
                        <th>Qtd. Mínima</th>
                        <th>Situação</th>
                    <% } %>
                </tr>
            </thead>
            <tbody>
                <% for (Produto p : lista) { %>
                    <tr>
                        <td><%= p.getNome() %></td>
                        <td><%= (p.getCategoria() != null) ? p.getCategoria().getNome() : "-" %></td>
                        
                        <%-- TABELA 1: LISTA DE PREÇOS --%>
                        <% if ("precos".equals(tipo)) { %>
                            <td><%= p.getUnidade() %></td>
                            <td>R$ <%= String.format("%.2f", p.getPrecoUnitario()) %></td>
                        <% } %>
                        
                        <%-- TABELA 2: BALANÇO FINANCEIRO --%>
                        <% if ("balanco".equals(tipo)) { 
                            double totalItem = p.getQtdEstoque() * p.getPrecoUnitario();
                            totalGeralFinanceiro += totalItem;
                        %>
                            <td><%= p.getQtdEstoque() %></td>
                            <td>R$ <%= String.format("%.2f", p.getPrecoUnitario()) %></td>
                            <td>R$ <%= String.format("%.2f", totalItem) %></td>
                        <% } %>
                        
                        <%-- TABELA 3: ABAIXO DO MÍNIMO --%>
                        <% if ("minimo".equals(tipo)) { %>
                            <td class="alerta"><%= p.getQtdEstoque() %></td>
                            <td><%= p.getQtdMinimaEstoque() %></td>
                            <td class="alerta">REPOR ESTOQUE!</td>
                        <% } %>
                    </tr>
                <% } %>
                
                <%-- RODAPÉ DE TOTAIS (SÓ PARA BALANÇO) --%>
                <% if ("balanco".equals(tipo)) { %>
                    <tr class="total-row">
                        <td colspan="4" style="text-align: right;">VALOR TOTAL DO ESTOQUE:</td>
                        <td>R$ <%= String.format("%.2f", totalGeralFinanceiro) %></td>
                    </tr>
                <% } %>
            </tbody>
        </table>
        
    <% } else if (tipo != null) { %>
        <p>Nenhum registro encontrado para este relatório.</p>
    <% } %>

    <br><br>
    <a href="../A3-Controle-Estoque-WEB">Voltar ao Menu Principal</a>

</body>
</html>
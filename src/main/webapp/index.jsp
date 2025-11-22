<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Controle de Estoque - Home</title>
    <style>
        body { font-family: Arial, sans-serif; text-align: center; margin-top: 50px; }
        .menu-container { display: inline-block; text-align: left; border: 1px solid #ccc; padding: 20px; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        h1 { color: #333; }
        ul { list-style-type: none; padding: 0; }
        li { margin: 15px 0; }
        a { text-decoration: none; color: white; background-color: #007bff; padding: 10px 20px; border-radius: 5px; display: block; text-align: center; }
        a:hover { background-color: #0056b3; }
    </style>
</head>
<body>

    <h1>Sistema de Controle de Estoque</h1>
    
    <div class="menu-container">
        <h3>Menu Principal</h3>
        <ul>
            <li><a href="CategoriaServlet">Gerenciar Categorias</a></li>
            <li><a href="ProdutoServlet">Gerenciar Produtos</a></li>
            <li><a href="MovimentacaoServlet">Movimentação de Estoque</a></li>
            <li><a href="RelatoriosServlet">Relatórios</a></li>
        </ul>
    </div>

</body>
</html>
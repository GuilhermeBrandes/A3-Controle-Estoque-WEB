package br.unisul.controleestoque.dao;

// Nossos modelos
import br.unisul.controleestoque.model.Movimentacao;
import br.unisul.controleestoque.model.Produto;
// Nossa conexão
import br.unisul.controleestoque.util.ConexaoBD;

// Imports do Java SQL
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class MovimentacaoDAO {

    /**
     * Método para salvar uma nova movimentação (Entrada/Saída)
     * Isso usa uma TRANSAÇÃO para garantir que a movimentação
     * E a atualização do estoque do produto ocorram com sucesso.
     */
    public boolean salvar(Movimentacao movimentacao) {
        
        String sqlInsertMov = "INSERT INTO Movimentacao (data_movimentacao, quantidade, " + 
                              "tipo_movimentacao, id_produto) VALUES (?, ?, ?, ?)";
        
        // O "?" final define se vamos somar ou subtrair
        String sqlUpdateProduto = "UPDATE Produto SET qtd_estoque = qtd_estoque + ? " + 
                                  "WHERE id_produto = ?";
        
        Connection conexao = null;
        PreparedStatement psInsert = null;
        PreparedStatement psUpdate = null;

        try {
            // 1. Obter a conexão
            conexao = ConexaoBD.getConexao();
            
            // --- INÍCIO DA TRANSAÇÃO ---
            // Desligamos o AutoCommit. Nada será salvo até mandarmos.
            conexao.setAutoCommit(false); 
            
            // 2. Preparar o INSERT da Movimentação
            psInsert = conexao.prepareStatement(sqlInsertMov);
            psInsert.setTimestamp(1, new Timestamp(System.currentTimeMillis())); // Pega a data/hora atual
            psInsert.setInt(2, movimentacao.getQuantidade());
            psInsert.setString(3, movimentacao.getTipoMovimentacao());
            psInsert.setInt(4, movimentacao.getProduto().getIdProduto());
            
            // 3. Preparar o UPDATE do Produto
            psUpdate = conexao.prepareStatement(sqlUpdateProduto);
            
            // Lógica de Entrada/Saída
            if (movimentacao.getTipoMovimentacao().equals("Entrada")) {
                psUpdate.setInt(1, movimentacao.getQuantidade()); // SOMA
            } else { // "Saída"
                psUpdate.setInt(1, -movimentacao.getQuantidade()); // SUBTRAI
            }
            psUpdate.setInt(2, movimentacao.getProduto().getIdProduto());

            // 4. Executar os dois comandos
            psInsert.executeUpdate();
            psUpdate.executeUpdate();
            
            // 5. Se os dois comandos funcionaram, EFETIVA a transação
            conexao.commit(); 
            
            System.out.println("Movimentação salva e estoque atualizado!");
            return true;

        } catch (SQLException e) {
            System.err.println("ERRO ao salvar movimentação (TRANSAÇÃO FALHOU):");
            e.printStackTrace();
            try {
                // Se deu erro, DESFAZ tudo o que foi feito
                if (conexao != null) {
                    conexao.rollback(); 
                    System.err.println("Rollback executado.");
                }
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
            return false;
        } finally {
            // 6. Fechar os recursos e ligar o AutoCommit de volta
            try {
                if (psInsert != null) psInsert.close();
                if (psUpdate != null) psUpdate.close();
                if (conexao != null) {
                    conexao.setAutoCommit(true); // Liga o AutoCommit de volta
                    conexao.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * =================================================================
     * MÉTODO DE TESTE (Para Salvar Movimentação)
     * =================================================================
     */
    public static void main(String[] args) {
        System.out.println("--- Teste de Salvar Movimentação ---");
        
        // **IMPORTANTE**: Para movimentar um Produto, precisamos de um ID válido.
        // Vamos usar o "Leite Integral" que você criou.
        // Vá ao MySQL Workbench e veja qual é o ID dele.
        // Vamos supor que seja o ID 1.
        
        // --- PREPARAÇÃO PARA O TESTE ---
        int idDoProdutoExistente = 1; // <-- MUDE ESSE ID se for diferente
        
        // 1. Criamos um objeto Produto "falso" só com o ID
        Produto produtoParaMovimentar = new Produto();
        produtoParaMovimentar.setIdProduto(idDoProdutoExistente);
        
        // 2. Criamos a Movimentação (uma ENTRADA)
        Movimentacao entrada = new Movimentacao();
        entrada.setTipoMovimentacao("Entrada");
        entrada.setQuantidade(50); // Adicionando 50 unidades
        entrada.setProduto(produtoParaMovimentar);
        
        // 3. Criamos o DAO e tentamos salvar
        MovimentacaoDAO dao = new MovimentacaoDAO();
        boolean sucessoEntrada = dao.salvar(entrada);
        
        if (sucessoEntrada) {
            System.out.println("TESTE BEM SUCEDIDO: Entrada salva!");
        } else {
            System.err.println("TESTE FALHOU: Entrada não foi salva.");
        }
        
        // 4. Criamos outra Movimentação (uma SAÍDA)
        Movimentacao saida = new Movimentacao();
        saida.setTipoMovimentacao("Saída");
        saida.setQuantidade(10); // Removendo 10 unidades
        saida.setProduto(produtoParaMovimentar);
        
        boolean sucessoSaida = dao.salvar(saida);
        if (sucessoSaida) {
            System.out.println("TESTE BEM SUCEDIDO: Saída salva!");
        } else {
            System.err.println("TESTE FALHOU: Saída não foi salva.");
        }
    }
}
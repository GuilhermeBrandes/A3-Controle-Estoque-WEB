package br.unisul.controleestoque.model;

public class Produto {

    private int idProduto;
    private String nome;
    private double precoUnitario;
    private String unidade;
    private int qtdEstoque;
    private int qtdMinimaEstoque;
    private int qtdMaximaEstoque;
    private Categoria categoria;

    // Construtor padrão
    public Produto() {
    }

    // Getters e Setters
    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public int getQtdEstoque() {
        return qtdEstoque;
    }

    public void setQtdEstoque(int qtdEstoque) {
        this.qtdEstoque = qtdEstoque;
    }

    public int getQtdMinimaEstoque() {
        return qtdMinimaEstoque;
    }

    public void setQtdMinimaEstoque(int qtdMinimaEstoque) {
        this.qtdMinimaEstoque = qtdMinimaEstoque;
    }

    public int getQtdMaximaEstoque() {
        return qtdMaximaEstoque;
    }

    public void setQtdMaximaEstoque(int qtdMaximaEstoque) {
        this.qtdMaximaEstoque = qtdMaximaEstoque;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
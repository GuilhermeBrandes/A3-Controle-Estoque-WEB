package br.unisul.controleestoque.model;

public class Categoria {

    private int idCategoria;
    private String nome;
    private String tamanho; // (Pequeno, Médio, Grande)
    private String embalagem; // (Lata, Vidro, Plástico)

    // Construtor padrão
    public Categoria() {
    }

    // Getters e Setters
    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public String getEmbalagem() {
        return embalagem;
    }

    public void setEmbalagem(String embalagem) {
        this.embalagem = embalagem;
    }
}
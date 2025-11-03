-- Criação da Tabela de Categoria
--
CREATE TABLE Categoria (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE,
    tamanho ENUM('Pequeno', 'Médio', 'Grande'),
    embalagem ENUM('Lata', 'Vidro', 'Plástico')
);

-- Criação da Tabela de Produto
--
CREATE TABLE Produto (
    id_produto INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL UNIQUE,
    preco_unitario DECIMAL(10, 2) NOT NULL,
    unidade VARCHAR(50),
    qtd_estoque INT NOT NULL DEFAULT 0,
    qtd_minima_estoque INT NOT NULL DEFAULT 0,
    qtd_maxima_estoque INT,

    -- Chave estrangeira para Categoria
    id_categoria INT,
    FOREIGN KEY (id_categoria) REFERENCES Categoria(id_categoria)
);

-- Criação da Tabela de Movimentacao
--
CREATE TABLE Movimentacao (
    id_movimentacao INT AUTO_INCREMENT PRIMARY KEY,
    data_movimentacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    quantidade INT NOT NULL,
    tipo_movimentacao ENUM('Entrada', 'Saída') NOT NULL,

    -- Chave estrangeira para Produto
    id_produto INT NOT NULL,
    FOREIGN KEY (id_produto) REFERENCES Produto(id_produto)
);

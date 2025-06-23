CREATE DATABASE IF NOT EXISTS BetwinVagas;
USE BetwinVagas;

CREATE TABLE Usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    papel ENUM('ADMIN', 'EMPRESA', 'PROFISSIONAL') NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE Administradores (
    id_usuario INT PRIMARY KEY,
    nome VARCHAR(255),
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario) ON DELETE CASCADE
);

CREATE TABLE Empresas (
    id_usuario INT PRIMARY KEY,
    cnpj VARCHAR(18) UNIQUE NOT NULL,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT NOT NULL,
    cidade VARCHAR(255) NOT NULL,
    estado VARCHAR(255),
    pais VARCHAR(255),
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario) ON DELETE CASCADE
);

CREATE TABLE Profissionais (
    id_usuario INT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    telefone VARCHAR(20) NOT NULL,
    sexo ENUM('M', 'F', 'Outro') NOT NULL,
    data_nascimento DATE NOT NULL,
    cidade VARCHAR(255) NOT NULL,
    estado VARCHAR(255),
    pais VARCHAR(255),
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario) ON DELETE CASCADE
);

CREATE TABLE Vagas (
    id_vaga INT AUTO_INCREMENT PRIMARY KEY,
    id_empresa INT NOT NULL,
    titulo VARCHAR(255) NOT NULL,
    descricao TEXT NOT NULL,
    remuneracao DECIMAL(10, 2),
    data_limite_inscricao DATE NOT NULL,
    cidade VARCHAR(255) NOT NULL,
    estado VARCHAR(255) NOT NULL,
    pais VARCHAR(255) NOT NULL,
    ativa BOOLEAN DEFAULT TRUE,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_empresa) REFERENCES Empresas(id_usuario) ON DELETE CASCADE
);

CREATE TABLE Candidaturas (
    id_candidatura INT AUTO_INCREMENT PRIMARY KEY,
    id_profissional INT NOT NULL,
    id_vaga INT NOT NULL,
    data_candidatura TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status_vaga ENUM('ABERTO', 'NAO_SELECIONADO', 'ENTREVISTA') DEFAULT 'ABERTO' NOT NULL,
    UNIQUE (id_profissional, id_vaga),
    FOREIGN KEY (id_profissional) REFERENCES Profissionais(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_vaga) REFERENCES Vagas(id_vaga) ON DELETE CASCADE
);
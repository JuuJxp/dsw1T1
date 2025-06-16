-- Criação do Banco de Dados
CREATE DATABASE IF NOT EXISTS BetwinVagas;
USE BetwinVagas;

-- Tabela Usuarios: armazena informações de login
CREATE TABLE Usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    papel ENUM('admin', 'empresa', 'profissional') NOT NULL,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tabela Administradores: ADM dos cruds
CREATE TABLE Administradores (
    id_administrador INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT UNIQUE NOT NULL,
    nome VARCHAR(255),
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario) ON DELETE CASCADE
);

-- Tabela Empresas: Publica vagas
CREATE TABLE Empresas (
    id_empresa INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT UNIQUE NOT NULL,
    cnpj VARCHAR(18) UNIQUE NOT NULL,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT,
    cidade VARCHAR(255),
    estado VARCHAR(255),
    pais VARCHAR(255),
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario) ON DELETE CASCADE
);

-- Tabela Profissionais: se candidatam para as vagas
CREATE TABLE Profissionais (
    id_profissional INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT UNIQUE NOT NULL,
    nome VARCHAR(255) NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    telefone VARCHAR(20),
    sexo ENUM('M', 'F', 'Outro'),
    data_nascimento DATE,
    cidade VARCHAR(255),
    estado VARCHAR(255),
    pais VARCHAR(255),
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario) ON DELETE CASCADE
);

-- Tabela Vagas: Informações sobre as vagas
CREATE TABLE Vagas (
    id_vaga INT AUTO_INCREMENT PRIMARY KEY,
    id_empresa INT NOT NULL,
    titulo VARCHAR(255) NOT NULL,
    descricao TEXT NOT NULL,
    remuneracao DECIMAL(10, 2),
    data_limite_inscricao DATE NOT NULL,
    cidade VARCHAR(255) NOT NULL,
    ativa BOOLEAN DEFAULT TRUE,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_empresa) REFERENCES Empresas(id_empresa) ON DELETE CASCADE
);

-- Tabela Candidaturas: Registra as candidaturas dos profissionais às vagas
CREATE TABLE Candidaturas (
    id_candidatura INT AUTO_INCREMENT PRIMARY KEY,
    id_profissional INT NOT NULL,
    id_vaga INT NOT NULL,
    data_candidatura TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status_vaga ENUM('ABERTO', 'NAO_SELECIONADO', 'ENTREVISTA') DEFAULT 'ABERTO' NOT NULL,
    -- Restrição para garantir que cada candidato se candidate apenas uma vez por vaga
    UNIQUE (id_profissional, id_vaga),
    FOREIGN KEY (id_profissional) REFERENCES Profissionais(id_profissional) ON DELETE CASCADE,
    FOREIGN KEY (id_vaga) REFERENCES Vagas(id_vaga) ON DELETE CASCADE
);


-- Inserções iniciais

-- Inserções na tabela Usuarios
INSERT INTO Usuarios (email, senha, papel) VALUES
('admin@example.com', 'admin_pass_123', 'admin'),
('empresa1@example.com', 'empresa1_pass_xyz', 'empresa'),
('empresa2@example.com', 'empresa2_pass_abc', 'empresa'),
('profissional1@example.com', 'prof1_pass_789', 'profissional'),
('profissional2@example.com', 'prof2_pass_456', 'profissional');

-- Inserções na tabela Administradores
INSERT INTO Administradores (id_usuario, nome) VALUES
((SELECT id_usuario FROM Usuarios WHERE email = 'admin@example.com'), 'Admin Master');

-- Inserções na tabela Empresas
INSERT INTO Empresas (id_usuario, cnpj, nome, descricao, cidade, estado, pais) VALUES
((SELECT id_usuario FROM Usuarios WHERE email = 'empresa1@example.com'), '00.000.000/0001-01', 'Tech Solutions Ltda.', 'Empresa líder em soluções de software e tecnologia.', 'São Paulo', 'SP', 'Brasil'),
((SELECT id_usuario FROM Usuarios WHERE email = 'empresa2@example.com'), '11.111.111/0001-11', 'Global Innovations S.A.', 'Consultoria especializada em estratégias de marketing e inovação.', 'Rio de Janeiro', 'RJ', 'Brasil');

-- Inserções na tabela Profissionais
INSERT INTO Profissionais (id_usuario, nome, cpf, telefone, sexo, data_nascimento, cidade, estado, pais) VALUES
((SELECT id_usuario FROM Usuarios WHERE email = 'profissional1@example.com'), 'Ana Paula Silva', '111.222.333-44', '(11) 98765-4321', 'F', '1995-03-10', 'Belo Horizonte', 'MG', 'Brasil'),
((SELECT id_usuario FROM Usuarios WHERE email = 'profissional2@example.com'), 'Carlos Eduardo Santos', '555.666.777-88', '(21) 99887-6655', 'M', '1992-11-25', 'Curitiba', 'PR', 'Brasil');

-- Inserções na tabela Vagas
INSERT INTO Vagas (id_empresa, titulo, descricao, remuneracao, data_limite_inscricao, cidade, ativa) VALUES
((SELECT id_empresa FROM Empresas WHERE nome = 'Tech Solutions Ltda.'), 'Desenvolvedor(a) Back-end Jr.', 'Buscamos desenvolvedor(a) com conhecimentos em Python, SQL e API REST.', 3500.00, '2025-07-30', 'São Paulo', TRUE),
((SELECT id_empresa FROM Empresas WHERE nome = 'Global Innovations S.A.'), 'Estagiário(a) de Marketing', 'Oportunidade para estudantes de Marketing, com foco em mídias sociais e análise de dados.', 1200.00, '2025-08-15', 'Rio de Janeiro', TRUE);

-- Inserções na tabela Candidaturas
INSERT INTO Candidaturas (id_profissional, id_vaga, status_vaga) VALUES
((SELECT id_profissional FROM Profissionais WHERE nome = 'Ana Paula Silva'), (SELECT id_vaga FROM Vagas WHERE titulo = 'Desenvolvedor(a) Back-end Jr.'), 'ABERTO'),
((SELECT id_profissional FROM Profissionais WHERE nome = 'Carlos Eduardo Santos'), (SELECT id_vaga FROM Vagas WHERE titulo = 'Estagiário(a) de Marketing'), 'ABERTO');
# dsw1T1

Trabalho 1 realizado por Julia e Luisa Tavares
# Betwin Vagas
## Processo de Desenvolvimento

O desenvolvimento do projeto foi iniciado utilizando a arquitetura de referência do "Roteiro 08" do professor Delano Beder. A entidade `Usuario` foi a primeira a ser implementada, servindo como modelo para estabelecer o padrão de desenvolvimento que foi seguido para todas as outras entidades do sistema. Este padrão consiste na separação de responsabilidades nas camadas de Domain, Acesso a Dados (DAO), Serviços e Controladores.

A estratégia de implementação foi focada em validar o fluxo principal da aplicação de forma antecipada. Para isso, as telas essenciais foram construídas com base no roteiro do professor, permitindo a visualização das primeiras implementações do sistema, como a conexão entre o frontend (Thymeleaf e HTML) e o backend. As funcionalidades foram desenvolvidas seguindo a criação de suas telas, para facilitar que a gente não se perdesse e acabasse com algo imcompleto ao longo do processo.

Funcionalidades não cobertas pelo material que estavamos vendo, como o envio de e-mails de notificação e o download de arquivos de currículo, implementamos por último por achar mais complicado. A lógica de autenticação e as configs fizemos seguindo com os roteiros adiante e alterando para funcionar para nosso projeto.

Na fase final, o projeto foi refinado com validação de erros nos formulários e a implementação de internacionalização para os idiomas português e inglês, que somos mais familiarizadas. A estilização visual foi realizada com CSS proprio, sem a utilização de frameworks como Bootstrap, pois achamos mais fácil de manejar como queriamos.

## Gestão de Projeto

A gestão das tarefas e o acompanhamento do progresso do desenvolvimento foram realizados através de um quadro Kanban na plataforma Trello, para não irmos esquecendo de coisas importantes ao longo do desenvolvimento.

* **Link do Quadro:** [https://trello.com/b/MnkNwGzk/kanban-quadro-modelo](https://trello.com/b/MnkNwGzk/kanban-quadro-modelo)


## Execução e Testes

Para a execução local do projeto e para a verificação do fluxo de funcionalidades implementado.

### Execução da Aplicação

**Pré-requisitos:** Java (JDK) e Apache Maven.

1.  Navegue até o diretório raiz do projeto via terminal.
2.  Execute `mvn clean compile` para compilar o código-fonte.
3.  Execute `mvn spring-boot:run` para iniciar a aplicação.

O sistema estará acessível em `http://localhost:8080`.

### Acesso Inicial e Banco de Dados

A aplicação utiliza um banco de dados em memória que é criado e populado na inicialização através do Hibernate/JPA. Um usuário com perfil de **Administrador** é criado por padrão para permitir o início das operações no sistema, caso não quiser criar, também possui um profissional e uma empresa.

* **Usuário Admin:** `admin@example.com`
* **Senha:** `admin_pass_123`

### Fluxo de Teste Recomendado para testar tudo

1.  **Acesso de Administrador:** Autentique-se com o usuário administrador. Utilize o painel para criar um novo usuário do tipo **Empresa** e outro do tipo **Profissional**. As funcionalidades de edição e exclusão de usuários podem ser testadas nesta etapa. Encerre a sessão.
2.  **Acesso de Empresa:** Autentique-se com o usuário da empresa recém-criada. Acesse a área de vagas e crie uma nova **Vaga**. Encerre a sessão.
3.  **Acesso de Profissional:** Autentique-se com o usuário do profissional. Localize a vaga criada anteriormente e realize a **candidatura**. Encerre a sessão.
4.  **Verificação da Empresa:** Retorne ao sistema com o usuário da empresa. Verifique a vaga publicada para visualizar a lista de candidaturas. O sistema deve permitir a análise do perfil e o **download do currículo** do candidato, bem como a **alteração de status** da candidatura se a vaga já estiver no período de análise (após expirar sua data de inscrição).
5.  **Verificação de Notificação:** Confirme se um **e-mail de notificação** sobre a alteração de status foi enviado ao endereço de e-mail associado ao usuário profissional.

## Protótipo de Interface

O desenvolvimento das telas foi guiado pelo protótipo que criamos no Excalidraw para termos uma ideia de como iriamos querer

`![Protótipo das Telas](src/main/resources//static/image/prototipo.png)`

## Desenvolvedoras

* Julia Tavares dos Santos
* Luisa Tavares dos Santos

Obrigaada :D


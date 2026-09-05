# Mercadinho JMM

Projeto desenvolvido para a disciplina de Programação Orientada a Objetos da UFAPE. O sistema possui um catálogo público para clientes e uma área administrativa protegida para gerenciar produtos, vendas e as demais rotinas do mercado.

## Integrantes

- José Emerson Costa de Araujo
- Julia Larissa Barbosa Belarmino Cabral
- Luan Texeira Carvalho
- Marcos Vinicius Gomes da Silva
- Sabrina de Melo de Carvalho Tavares

## Requisitos

- Java 17
- PostgreSQL
- Node.js 20 ou superior

## Banco de dados

Crie um banco chamado `mercado` no PostgreSQL. As credenciais usadas pelo projeto ficam em `src/main/resources/application.properties`.

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/mercado
spring.datasource.username=postgres
spring.datasource.password=123
```

Altere a senha se o seu PostgreSQL utilizar outro valor.

## Executar o backend

No PowerShell, dentro da pasta principal:

```powershell
.\mvnw.cmd spring-boot:run
```

O backend ficará disponível em `http://localhost:8082`.

## Primeiro acesso administrativo

Ao iniciar o backend pela primeira vez, um administrador é criado automaticamente:

- E-mail: `admin@mercadinhojmm.com`
- Senha: `admin123`

As credenciais podem ser substituídas antes da execução:

```powershell
$env:ADMIN_NOME="Administrador"
$env:ADMIN_EMAIL="seu-email@exemplo.com"
$env:ADMIN_PASSWORD="uma-senha-segura"
.\mvnw.cmd spring-boot:run
```

## Executar o frontend

Em outro PowerShell:

```powershell
cd mercadinho-frontend
npm install
npm run dev
```

Acesse `http://localhost:3000`.

## Áreas disponíveis

- `/produtos`: catálogo público, somente para consulta.
- `/acesso`: entrada para clientes e administradores.
- `/admin`: visão geral administrativa.
- Administração de produtos, vendas, clientes, funcionários, fornecedores, estoque, lotes, caixas, pagamentos, financeiro e dados do mercado.

Somente funcionários com cargo `Administrador`, `Admin` ou `Gerente` conseguem entrar na área administrativa.

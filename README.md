# Projeto Microserviços ESPM

## Como rodar o projeto

### Pré-requisitos

- Java 17+
- Maven 3.8+
- Docker e Docker Compose

### Passos para rodar

1. Clone o repositório:
   ```bash
   git clone <url-do-repo>
   cd espm.poo25.1-main/spring/api
   ```

2. Compile todos os microsserviços:
   ```bash
   cd ../account-service
   mvn clean package
   cd ../auth-service
   mvn clean package
   cd ../data-service
   mvn clean package
   cd ../gateway-service
   mvn clean package
   cd ../api
   ```

3. Suba os containers:
   ```bash
   docker compose up -d --build
   ```

4. Acesse o gateway em [http://localhost:8080](http://localhost:8080)

---

## Exemplos de uso dos endpoints

### Cadastro de usuário

```bash
curl -X POST http://localhost:8080/account/register \
  -H "Content-Type: application/json" \
  -d '{"nome":"João","email":"joao@email.com","senha":"123456"}'
```

### Login

```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"joao@email.com","senha":"123456"}'
```

A resposta trará um token JWT.

### Listar usuários (autenticado)

```bash
curl -H "Authorization: Bearer <TOKEN>" http://localhost:8080/account/users
```

### Importar dados dos sensores

```bash
curl -H "Authorization: Bearer <TOKEN>" http://localhost:8080/data/import
```

### Exportar dados de um tipo de sensor

```bash
curl -H "Authorization: Bearer <TOKEN>" http://localhost:8080/data/export/temperatura
```

---

## Como rodar os testes

```bash
cd spring/account-service
mvn test
# Repita para os outros serviços
```

---

## Variáveis de ambiente

- Os segredos do JWT e credenciais do banco devem ser configurados via variáveis de ambiente no compose.yaml.

---

## Observações

- O serviço de autenticação (`auth`) deve ser iniciado antes dos demais.
- Todos os endpoints de dados exigem autenticação JWT.
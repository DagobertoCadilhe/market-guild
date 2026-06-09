# Market Guild

## Descrição do Projeto

Market Guild é um projeto que busca simular o mercado de um jogo como Tibia. Usando tecnologias como PostgreSQL e MongoDB para armazenamento dos dados de jogadores e itens do mercado respectivamente, Spring Boot para o desenvolvimento dos microsserviços que são o coração da aplicação, e muitas outras ferramentas para simular o mercado.

## Lista dos Serviços

| Serviço | Responsabilidade | Porta |
|---|---|---|
| eureka-server | Discovery server dos serviços | 8761 |
| api-gateway | Ponto único de entrada da rede | 8080 |
| player-service | Microsserviço responsável pelo usuário/player | 8081 |
| market-service | Microsserviço responsável pelo controle do mercado | 8082 |
| postgres | Banco de dados relacional para o player-service | 5432 |
| mongodb | Banco de dados não relacional para o market-service | 27017 |

## Tecnologias Utilizadas

- Spring Boot
- Eureka (Spring Cloud Netflix)
- API Gateway (Spring Cloud Gateway)
- OpenFeign
- Resilience4j (Circuit Breaker)
- PostgreSQL
- MongoDB
- JPA / Hibernate
- Docker
- Docker Compose

## Como Executar o Projeto

1. Faça o download do projeto
2. Abra o Docker Desktop
3. Abra um terminal e aponte para o diretório do projeto:

```bash
cd \market-guild
```

4. Execute o comando:

```bash
docker-compose up --build
```

5. Aguarde todos os containers subirem.

## Portas Utilizadas

| Serviço | Porta |
|---|---|
| Eureka | 8761 |
| API Gateway | 8080 |
| player-service | 8081 |
| market-service | 8082 |
| PostgreSQL | 5432 |
| MongoDB | 27017 |

## Exemplos de Endpoints

### player-service

| Método | Endpoint | Descrição |
|---|---|---|
| POST | /api/players | Criar um player |
| GET | /api/players/{id} | Buscar um player pelo ID |
| PATCH | /api/players/{id}/balance | Subtrair do balance do player |

### market-service

| Método | Endpoint | Descrição |
|---|---|---|
| POST | /api/market | Listar um item no mercado |
| GET | /api/market/{itemId} | Buscar um item pelo ID |
| GET | /api/market/list | Ver todos os itens listados |

## Como Acessar o Discovery Server

Após subir o container, acesse:

```
http://localhost:8761
```

## Como Testar as Rotas pelo API Gateway

Utilize a porta **8080** em todas as requisições. O Gateway redirecionará automaticamente para o serviço correto baseado no caminho da URL.

**Exemplo:**
```
http://localhost:8080/api/players  →  player-service
http://localhost:8080/api/market   →  market-service
```

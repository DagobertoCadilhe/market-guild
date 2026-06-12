# Market Guild

## Descrição do Projeto
Market Guild é um projeto que busca simular o mercado de um jogo como Tibia. Usando tecnologias como PostgreSQL e MongoDB para armazenamento dos dados de jogadores e itens do mercado respectivamente, Spring Boot para o desenvolvimento dos microsserviços que são o coração da aplicação, e muitas outras ferramentas para simular o mercado.

## Arquitetura
A solução é composta por microsserviços independentes com bancos de dados separados, comunicação síncrona via Feign e assíncrona via Kafka, e uma camada completa de observabilidade.

```
Cliente → API Gateway → market-service → Feign → player-service
                                       → Kafka → player-service (consumer)
```

## Lista dos Serviços
| Serviço | Responsabilidade | Porta |
|---|---|---|
| eureka-server | Discovery server dos serviços | 8761 |
| api-gateway | Ponto único de entrada da rede (reativo/WebFlux) | 8080 |
| player-service | Microsserviço responsável pelo usuário/player | 8081 |
| market-service | Microsserviço responsável pelo controle do mercado | 8082 |
| postgres | Banco de dados relacional para o player-service | 5432 |
| mongodb | Banco de dados não relacional para o market-service | 27017 |
| kafka | Broker de mensagens para comunicação assíncrona | 9092 |
| kafka-ui | Interface visual para monitorar tópicos Kafka | 8090 |
| prometheus | Coleta de métricas dos serviços | 9090 |
| loki | Agregador de logs | 3100 |
| promtail | Agente de coleta de logs dos containers | - |
| grafana | Visualização de métricas e logs | 3000 |

## Tecnologias Utilizadas
- Spring Boot 4.x
- Eureka (Spring Cloud Netflix)
- API Gateway (Spring Cloud Gateway WebFlux — reativo)
- Apache Kafka (KRaft mode, sem Zookeeper)
- OpenFeign
- Resilience4j (Circuit Breaker)
- PostgreSQL
- MongoDB
- JPA / Hibernate
- Docker / Docker Compose
- Prometheus + Grafana (métricas)
- Loki + Promtail (logs)
- Correlation ID manual (rastreamento entre serviços)

## Fluxo de Compra
1. Cliente faz `POST /api/market` via Gateway
2. `market-service` verifica saldo do player via Feign (síncrono)
3. Item é salvo no MongoDB
4. Evento `ItemBoughtEvent` é publicado no Kafka (assíncrono)
5. `player-service` consome o evento, debita o saldo e adiciona o item na bag do player

## Observabilidade

### Correlation ID
O API Gateway gera um `X-Correlation-ID` único para cada requisição. Esse ID é propagado via header HTTP (Feign) e via payload do evento Kafka, aparecendo em todos os logs de todos os serviços. Isso permite rastrear uma operação completa — síncrona e assíncrona — por um único identificador.

### Métricas
O Spring Boot Actuator expõe métricas em `/actuator/prometheus`. O Prometheus coleta essas métricas a cada 15 segundos. O Grafana visualiza as métricas em dashboards.

### Logs
O Promtail coleta automaticamente os logs de todos os containers Docker e os envia para o Loki. O Grafana permite filtrar logs por serviço e por Correlation ID.

**Exemplo de busca no Grafana:**
```
{container=~"market-guild-player-service-1|market-guild-market-service-1"} |= "seu-correlation-id"
```

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
| Kafka | 9092 |
| Kafka UI | 8090 |
| Prometheus | 9090 |
| Grafana | 3000 |
| Loki | 3100 |

## Exemplos de Endpoints

### player-service
| Método | Endpoint | Descrição |
|---|---|---|
| POST | /api/players | Criar um player |
| GET | /api/players/{id} | Buscar um player pelo ID (inclui bag) |
| PATCH | /api/players/{id}/balance | Subtrair do balance do player |

### market-service
| Método | Endpoint | Descrição |
|---|---|---|
| POST | /api/market | Listar um item no mercado |
| GET | /api/market/{itemId} | Buscar um item pelo ID |
| GET | /api/market/list | Ver todos os itens listados |

## Como Acessar os Serviços de Observabilidade
| Serviço | URL | Credenciais |
|---|---|---|
| Eureka | http://localhost:8761 | - |
| Kafka UI | http://localhost:8090 | - |
| Prometheus | http://localhost:9090 | - |
| Grafana | http://localhost:3000 | admin / admin |

## Como Testar as Rotas pelo API Gateway
Utilize a porta **8080** em todas as requisições. O Gateway redirecionará automaticamente para o serviço correto baseado no caminho da URL.

**Exemplo:**
```
http://localhost:8080/api/players  →  player-service
http://localhost:8080/api/market   →  market-service
```
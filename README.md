# rinha-backend-2023 ftsuda

Requisitos do projeto em https://github.com/zanfranceschi/rinha-de-backend-2023-q3

## Stack usada

- Spring Webflux com R2DBC + PostgreSQL
- Java 17

## Instruções

### Pré requisito: docker instalado

Rodar o comando abaixo

```bash
docker compose up -d
```

Para remover o container

```bash
docker compose down -v
```

## Aprendizados

- [X] Uso do Webflux - Normalmente uso o MVC padrão no dia a dia
- [X] Acesso ao BD com R2DBC - Normalmente uso Spring Data JPA/Hibernate
- [X] Subir imagem para dockerhub
    - Pré-requisito: conta em https://hub.docker.com/
    ```bash
    docker login
    docker build -t ftsuda/rinha-backend:1.0.0 .
    docker tag ftsuda/rinha-backend:1.0.0 ftsuda/rinha-backend:1.0.0
    docker push ftsuda/rinha-backend:1.0.0
    ```

## Pendências

- [ ] Tunning das configurações dos recursos (connection pool, thread pool, CPU, memória, etc)
- [ ] Uso do Redis compartilhado entre instâncias (cache atual feito manualmente só funciona localmente)
- [ ] Alterar projeto para usar Virtual Threads (JDK 19+)
- [ ] Alterar projeto para usar GraalVM
- [ ] Jackson converte automaticamente números para strings (não atende o requisito de apelido não numérico)
- [ ] Escrever os testes funcionais do projeto
- [ ] Plugar Prometheus/Grafana para acompanhar uso dos recursos durante execução dos testes
- [ ] Github Action para automatizar o build e publish

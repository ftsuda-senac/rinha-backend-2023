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
    - Pré-rquisito: conta em https://hub.docker.com/
    ```bash
    docker login
    docker build -t ftsuda/rinha-backend:1.0.0 .
    docker tag ftsuda/rinha-backend:1.0.0 ftsuda/rinha-backend:1.0.0
    docker push ftsuda/rinha-backend:1.0.0
    ```

## Pendências

- [ ] Tunning das configurações dos recursos (connection pool, thread pool, CPU, memória, etc)
- [ ] Jackson converte automaticamente números para strings
- [ ] Escrever os testes funcionais do projeto
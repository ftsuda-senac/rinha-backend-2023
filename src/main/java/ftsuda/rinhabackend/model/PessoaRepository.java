package ftsuda.rinhabackend.model;

import java.util.UUID;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface PessoaRepository extends R2dbcRepository<Pessoa, UUID> {

    // @Query("SELECT DISTINCT * FROM pessoa WHERE LOWER(nome) ILIKE '%'||:termoBusca||'%' OR LOWER(apelido) LIKE
    // '%'||:termoBusca||'%' OR LOWER(stack_db) LIKE '%'||:termoBusca||'%'")
    @Query("SELECT DISTINCT * FROM pessoa WHERE nome ILIKE '%'||:termoBusca||'%' OR apelido ILIKE '%'||:termoBusca||'%' OR stack_db ILIKE '%'||:termoBusca||'%' LIMIT 50")
    Flux<Pessoa> findBySearchTerm(String lowerTerm);

}

package ftsuda.rinhabackend.controller;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.codec.DecodingException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import ftsuda.rinhabackend.model.Pessoa;
import ftsuda.rinhabackend.model.PessoaRepository;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// Referências
// https://www.bezkoder.com/spring-boot-r2dbc-postgresql/
// https://gokhana.dev/reactive-programming-with-spring-webflux/
// https://reflectoring.io/getting-started-with-spring-webflux/
// https://daasrattale.medium.com/hands-on-practice-with-reactive-spring-with-redis-cache-and-docker-support-4d0bc27f1e54

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    private static final Logger log = LoggerFactory.getLogger(PessoaController.class);

    // TODO: Removendo camada Services pois é uma aplicação simples.
    private final PessoaRepository repository;

    // TODO: Cache local simples para veritar ida ao BD em caso de apelidos já cadastrados
    // Em cluster, não compartilha informações entre as instâncias, mas estatisticamente pode reduzir a carga ao BD
    // Idealmente, deve ser um cache externo compartilhado (ex: Redis)
    private Set<String> apelidosUsados = new HashSet<>();

    public PessoaController(PessoaRepository repository) {
        this.repository = repository;
    }

    // Scheduled Não funciona em fluxo reativo
    // Ver https://joao-dartora.medium.com/entendendo-mais-a-fundo-os-schedulers-do-spring-webflux-8953cfd89b2
    // https://stackoverflow.com/questions/54093132/scheduled-and-spring-webflux
    // @Scheduled(fixedRate = 5000)
    // @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    // public Mono<Void> updateCache() {
    // repository.findAllApelidos().collectList().subscribe((result) -> {
    // log.debug("*** Updating local cache");
    // apelidosUsados.addAll(result);
    // });
    // return Mono.empty();
    // }

    @PostMapping
    // @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<Pessoa>> save(@Valid @RequestBody Pessoa pessoa, UriComponentsBuilder ucb) {
        if (apelidosUsados.contains(pessoa.getApelido())) {
            return Mono.just(ResponseEntity.unprocessableEntity().build());
        }
        return repository.save(pessoa).map(p -> {
            apelidosUsados.add(p.getApelido());
            return ResponseEntity.created(ucb.pathSegment("pessoas", "{id}").buildAndExpand(p.getId().toString()).toUri()).body(p);
        });
    }

    @Deprecated
    public Mono<Pessoa> findById1(@PathVariable UUID id) {
        return repository.findById(id).switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @GetMapping("/{id}")
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public Mono<ResponseEntity<Pessoa>> findById(@PathVariable UUID id) {
        return repository.findById(id).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public Flux<Pessoa> search(@RequestParam("t") String t) {
        return repository.findBySearchTerm(t);
    }

    /**
     * Tratamento dos erros de integridade dos dados
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public Mono<Void> handlePostError(Exception ex) {
        // log.error(ex.getMessage());
        return Mono.empty();
    }

    /**
     * Tratamento dos erros de sintaxe
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({WebExchangeBindException.class, ValidationException.class, DecodingException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<Void> handleInvalidRequestError(Exception ex) {
        // log.error(ex.getMessage());
        return Mono.empty();
    }
}

package ftsuda.rinhabackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ftsuda.rinhabackend.model.PessoaRepository;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/contagem-pessoas")
public class ContagemPessoasController {

    private final PessoaRepository repository;

    public ContagemPessoasController(PessoaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public Mono<Long> count() {
        return repository.count();
    }

}

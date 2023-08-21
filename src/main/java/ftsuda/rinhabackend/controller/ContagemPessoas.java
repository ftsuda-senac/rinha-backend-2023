package ftsuda.rinhabackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ftsuda.rinhabackend.model.Pessoa;
import ftsuda.rinhabackend.model.PessoaRepository;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/contagem-pessoas")
public class ContagemPessoas {

    private final PessoaRepository repository;

    public ContagemPessoas(PessoaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public Flux<Pessoa> findAll() {
        return repository.findAll();
    }

}

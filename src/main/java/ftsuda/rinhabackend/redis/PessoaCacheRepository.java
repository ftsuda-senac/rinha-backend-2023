package ftsuda.rinhabackend.redis;

import org.springframework.data.keyvalue.repository.KeyValueRepository;

public interface PessoaCacheRepository extends KeyValueRepository<PessoaCache, String> {

}

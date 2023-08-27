package ftsuda.rinhabackend;

import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

@Component
public class RedisLoader {

    private final ReactiveRedisConnectionFactory factory;
    private final ReactiveRedisOperations<String, PessoaCache> coffeeOps;

    public RedisLoader(ReactiveRedisConnectionFactory factory, ReactiveRedisOperations<String, PessoaCache> coffeeOps) {
        this.factory = factory;
        this.coffeeOps = coffeeOps;
    }

    @PostConstruct
    public void loadData() {
        // Limpa o cache
        factory.getReactiveConnection().serverCommands().flushAll();
    }
}

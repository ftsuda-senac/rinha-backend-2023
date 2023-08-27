package ftsuda.rinhabackend;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

// https://spring.io/guides/gs/spring-data-reactive-redis/
@Component
public class RedisConfig {

    @Bean
    ReactiveRedisOperations<String, PessoaCache> redisOperations(ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<PessoaCache> serializer = new Jackson2JsonRedisSerializer<>(PessoaCache.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, PessoaCache> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, PessoaCache> context = builder.value(serializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
    }
}

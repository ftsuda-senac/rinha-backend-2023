package ftsuda.rinhabackend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.web.reactive.config.EnableWebFlux;
import io.r2dbc.spi.ConnectionFactory;

@EnableWebFlux
// @EnableScheduling
@EnableR2dbcRepositories(basePackages = {"ftsuda.rinhamvc.model"})
@EnableRedisRepositories(basePackages = {"ftsuda.rinhamvc.redis"})
@SpringBootApplication
public class RinhaBackendApplication {

    private static final Logger log = LoggerFactory.getLogger(RinhaBackendApplication.class);

    @Value("${spring.profiles.active:}")
    private String activeProfile;

    // https://docs.spring.io/spring-data/data-redis/docs/current/reference/html/#redis:support:cache-abstraction
    // @Bean
    // RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
    // return RedisCacheManager.create(connectionFactory);
    // }

    // https://stackoverflow.com/a/71699383
    // https://docs.spring.io/spring-data/data-redis/docs/current/reference/html/#redis:reactive:string
    // https://github.com/Bryksin/redis-reactive-cache/blob/master/src/main/java/com/vsware/libraries/redisreactivecache/config/RedisReactiveCacheConfig.java
    @Bean
    @Primary
    ReactiveStringRedisTemplate reactiveRedisTemplate(ReactiveRedisConnectionFactory connectionFactory) {
        return new ReactiveStringRedisTemplate(connectionFactory);
    }

    @Bean
    ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
        long start = System.currentTimeMillis();
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        log.info("*** ConnectionFactoryInitializer duration: {}", System.currentTimeMillis() - start);
        log.info("Active profile: {}", activeProfile);

        if ("dev".equals(activeProfile)) {
            log.info("Creating db schema");
            ResourceDatabasePopulator resource = new ResourceDatabasePopulator(new ClassPathResource("schema.sql"));
            initializer.setDatabasePopulator(resource);
        }
        return initializer;
    }

    public static void main(String[] args) {
        SpringApplication.run(RinhaBackendApplication.class, args);
    }

}

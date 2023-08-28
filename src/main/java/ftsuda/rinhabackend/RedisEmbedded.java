package ftsuda.rinhabackend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import redis.embedded.RedisServer;

// https://medium.com/@wahyudi.hh/embedded-redis-for-spring-boot-integration-test-e0d6b164b8fa
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Profile("dev")
public class RedisEmbedded {

    private static final Logger log = LoggerFactory.getLogger(RedisEmbedded.class);

    @Value("${spring.data.redis.port:6379}")
    private int redisPort;

    private RedisServer devRedisServer;

    @PostConstruct
    public void onInit() {
        try {
            log.error("Starting dev Redis server...");
            devRedisServer = RedisServer.builder().port(redisPort).build();
            devRedisServer.start();
            log.error("dev Redis server started");
        } catch (Exception ex) {
            log.error("Error starting dev Redis server", ex);
        }
    }

    @PreDestroy
    public void onDestroy() {
        if (devRedisServer != null) {
            devRedisServer.stop();
        }
    }

}

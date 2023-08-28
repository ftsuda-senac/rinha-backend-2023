package ftsuda.rinhabackend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

@Component
public class ContextListener {

    private static final Logger log = LoggerFactory.getLogger(ContextListener.class);

    @Autowired
    private RedisConnectionFactory redisConnFactory;

    @EventListener
    public void handleContextRefreshed(ContextRefreshedEvent event) {
        log.debug("Deleting all Redis entries {}", event.toString());
        redisConnFactory.getConnection().serverCommands().flushAll();
    }


}

package ftsuda.rinhabackend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.web.reactive.config.EnableWebFlux;
import io.r2dbc.spi.ConnectionFactory;

@EnableWebFlux
// @EnableScheduling
@SpringBootApplication
public class RinhaBackendApplication {

    private static final Logger log = LoggerFactory.getLogger(RinhaBackendApplication.class);

    @Value("${spring.profiles.active:}")
    private String activeProfile;

    @Value("${spring.profiles.include:}")
    private String includedProfile;

    @Bean
    ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
        long start = System.currentTimeMillis();
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        log.info("*** ConnectionFactoryInitializer duration: {}", System.currentTimeMillis() - start);
        log.info("Active profile: {}", activeProfile);
        log.info("Include profile: {}", includedProfile);

        if ("principal".equals(includedProfile) || "dev".equals(activeProfile)) {
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

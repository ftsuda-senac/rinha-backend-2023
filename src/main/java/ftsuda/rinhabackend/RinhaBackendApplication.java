package ftsuda.rinhabackend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.web.reactive.config.EnableWebFlux;
import io.r2dbc.spi.ConnectionFactory;

@EnableWebFlux
@SpringBootApplication
public class RinhaBackendApplication {

    private static final Logger log = LoggerFactory.getLogger(RinhaBackendApplication.class);

    @Autowired
    Environment env;

    @Bean
    ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        log.info("Profiles: {}", String.join(",", env.getActiveProfiles()));
        for (String profileName : env.getActiveProfiles()) {
            if ("principal".equals(profileName) || "dev".equals(profileName)) {
                log.info("Creating db schema");
                ResourceDatabasePopulator resource = new ResourceDatabasePopulator(new ClassPathResource("schema.sql"));
                initializer.setDatabasePopulator(resource);
                break;
            }
        }
        return initializer;
    }

	public static void main(String[] args) {
		SpringApplication.run(RinhaBackendApplication.class, args);
	}

}

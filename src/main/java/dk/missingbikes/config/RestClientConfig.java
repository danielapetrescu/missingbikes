package dk.missingbikes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * A single RestClient shared by every outgoing call.
 *
 * RestClient.create() builds a new client, with its own connection pool, each
 * time it is called. Doing that per request throws away the pooling and makes
 * the client impossible to configure in one place, so it is created once here
 * and injected where it is needed.
 */
@Configuration
public class RestClientConfig {

    @Bean
    public RestClient restClient(){
        return RestClient.create();
    }
}

package de.fhg.ivi.ids.broker;

import de.fraunhofer.iais.eis.InfrastructureComponent;
import de.fraunhofer.iais.eis.ids.broker.core.common.impl.ResourcePersistenceAdapter;
import de.fraunhofer.iais.eis.ids.broker.core.common.impl.SelfDescriptionPersistenceAdapter;
import de.fraunhofer.iais.eis.ids.broker.core.common.persistence.ResourcePersistenceAndIndexing;
import de.fraunhofer.iais.eis.ids.broker.core.common.persistence.SelfDescriptionPersistenceAndIndexing;
import de.fraunhofer.iais.eis.ids.index.common.persistence.NullIndexing;
import de.fraunhofer.iais.eis.ids.index.common.persistence.RepositoryFacade;
import de.fraunhofer.iais.eis.ids.index.common.persistence.spi.Indexing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.net.URI;
import java.util.ServiceLoader;

@ComponentScan({
        "de.fhg.ivi.ids.broker",
        "ids.*",
        "ids.messaging.*"
})
@SpringBootApplication
public class BrokerApplication {


    public static void main(String[] args) {
        SpringApplication.run(BrokerApplication.class, args);
    }

    @Bean
    RepositoryFacade createRepositoryFacade(
            @Value("${sparql.url}") String sparqlEndpointUrl
    ) {
        return new RepositoryFacade(sparqlEndpointUrl);
    }

    @Bean
    Indexing<?> createIndexing() {
        return ServiceLoader.load(Indexing.class).findFirst().orElse(new NullIndexing<InfrastructureComponent>());
    }

    @Bean(name="ConnectorIndexer")
    @Autowired
    SelfDescriptionPersistenceAdapter createConnectorIndexer(
            RepositoryFacade repositoryFacade,
            Indexing<InfrastructureComponent> indexing,
            @Value("${component.catalogUri}") URI catalogURI
    ) {
        // TODO externalize number of indexed connector resources
        return new SelfDescriptionPersistenceAndIndexing(
                repositoryFacade,
                catalogURI,
                indexing,
                100
        );
    }

    @Bean
    ResourcePersistenceAdapter createResourceIndexer(
            RepositoryFacade repositoryFacade,
            @Value("${component.catalogUri}") URI catalogURI
    ) {
        // TODO externalize number of indexed connector resources
        return new ResourcePersistenceAndIndexing(repositoryFacade, catalogURI, 100);
    }

}
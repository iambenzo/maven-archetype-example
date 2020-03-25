#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import ${package}.model.${entityName};
#if (${includeEvent} == "true")
import ${package}.model.${entityName}Event;#end
import ${package}.repository.${entityName}Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.Assert.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ${entityName}WebTests {

    private WebTestClient client;

    private List<${entityName}> expectedList;

    @Autowired
    private ${entityName}Repository repository;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void beforeEach() {
        this.client =
                WebTestClient
                        .bindToServer()
                        .baseUrl("http://localhost:" + port + "/${entityNameLowerCase}s")
                        .build();
#if (${includeRead} == "true")
        this.expectedList =
                repository.findAll().collectList().block();#end
    }

#if (${includeRead} == "true")
    @Test
    public void testGetAll${entityName}s() {
        client
                .get()
                .uri("/")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(${entityName}.class)
                .isEqualTo(expectedList);
    }

    @Test
    public void test${entityName}InvalidIdNotFound() {
        client
                .get()
                .uri("/aaa")
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    public void test${entityName}IdFound() {
        ${entityName} expected${entityName} = expectedList.get(0);
        client
                .get()
                .uri("/{id}", expected${entityName}.getId())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(${entityName}.class)
                .isEqualTo(expected${entityName});
    }#end

#if (${includeEvent} == "true")
    @Test
    public void test${entityName}Events() {
        FluxExchangeResult<${entityName}Event> result =
                client.get().uri("/events")
                        .accept(MediaType.TEXT_EVENT_STREAM)
                        .exchange()
                        .expectStatus().isOk()
                        .returnResult(${entityName}Event.class);

        ${entityName}Event expectedEvent =
                new ${entityName}Event(0L, "${entityName} Event");

        StepVerifier.create(result.getResponseBody())
                .expectNext(expectedEvent)
                .expectNextCount(2)
                .consumeNextWith(event ->
                        assertEquals(Long.valueOf(3), event.getEventId()))
                .thenCancel()
                .verify();
    }#end
}

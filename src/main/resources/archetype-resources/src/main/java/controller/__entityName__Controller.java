#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.controller;

import ${package}.model.${entityName};
#if (${includeEvent} == "true")
import ${package}.model.${entityName}Event;#end
import ${package}.repository.${entityName}Repository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@Component
public class ${entityName}Controller {
    private ${entityName}Repository repository;

    public ${entityName}Controller(${entityName}Repository repository) {
        this.repository = repository;
    }

#if (${includeRead} == "true")
    public Mono<ServerResponse> getAll${entityName}s(ServerRequest request) {
        Flux<${entityName}> ${entityNameLowerCase}s = repository.findAll();

        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(${entityNameLowerCase}s, ${entityName}.class);
    }

    public Mono<ServerResponse> get${entityName}(ServerRequest request) {
        String id = request.pathVariable("id");

        Mono<${entityName}> ${entityNameLowerCase}Mono = this.repository.findById(id);
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();

        return ${entityNameLowerCase}Mono
                .flatMap(${entityNameLowerCase} ->
                        ServerResponse.ok()
                                .contentType(APPLICATION_JSON)
                                .body(fromObject(${entityNameLowerCase})))
                .switchIfEmpty(notFound);
    }#end

#if (${includeCreate} == "true")
    public Mono<ServerResponse> save${entityName}(ServerRequest request) {
        Mono<${entityName}> ${entityNameLowerCase}Mono = request.bodyToMono(${entityName}.class);

        return ${entityNameLowerCase}Mono.flatMap(${entityNameLowerCase} ->
                ServerResponse.status(HttpStatus.CREATED)
                        .contentType(APPLICATION_JSON)
                        .body(repository.save(${entityNameLowerCase}), ${entityName}.class));
    }#end

#if (${includeUpdate} == "true")
    public Mono<ServerResponse> update${entityName}(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<${entityName}> existing${entityName}Mono = this.repository.findById(id);
        Mono<${entityName}> ${entityNameLowerCase}Mono = request.bodyToMono(${entityName}.class);

        Mono<ServerResponse> notFound = ServerResponse.notFound().build();

        return ${entityNameLowerCase}Mono.zipWith(existing${entityName}Mono,
                (${entityNameLowerCase}, existing${entityName}) ->
                        new ${entityName}(existing${entityName}.getId(), ${entityNameLowerCase}.getName())
        )
                .flatMap(${entityNameLowerCase} ->
                        ServerResponse.ok()
                                .contentType(APPLICATION_JSON)
                                .body(repository.save(${entityNameLowerCase}), ${entityName}.class)
                ).switchIfEmpty(notFound);
    }#end

#if (${includeDelete} == "true")
    public Mono<ServerResponse> delete${entityName}(ServerRequest request) {
        String id = request.pathVariable("id");

        Mono<${entityName}> ${entityNameLowerCase}Mono = this.repository.findById(id);
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();

        return ${entityNameLowerCase}Mono
                .flatMap(existing${entityName} ->
                        ServerResponse.ok()
                                .build(repository.delete(existing${entityName}))
                )
                .switchIfEmpty(notFound);
    }

    public Mono<ServerResponse> deleteAll${entityName}s(ServerRequest request) {
        return ServerResponse.ok()
                .build(repository.deleteAll());
    }#end

#if (${includeEvent} == "true")
    public Mono<ServerResponse> get${entityName}Events(ServerRequest request) {
        Flux<${entityName}Event> eventsFlux = Flux.interval(Duration.ofSeconds(1)).map(val ->
                new ${entityName}Event(val, "${entityName} Event")
        );

        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(eventsFlux, ${entityName}Event.class);
    }#end
}

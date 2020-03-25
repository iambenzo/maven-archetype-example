#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import ${package}.model.${entityName};
import ${package}.repository.${entityName}Repository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import reactor.core.publisher.Flux;

import ${package}.controller.${entityName}Controller;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.http.HttpMethod;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@SpringBootApplication
public class ${entityName}Application {

	public static void main(String[] args) {
		SpringApplication.run(${entityName}Application.class, args);
	}

	@Bean
	CommandLineRunner init(/*ReactiveMongoOperations operations, */${entityName}Repository repository) {
		return args -> {
			Flux<${entityName}> ${entityNameLowerCase}Flux = Flux.just(
					new ${entityName}(null, "Big Latte"),
					new ${entityName}(null, "Big Decaf"),
					new ${entityName}(null, "Green Tea"))
					.flatMap(repository::save);

			${entityNameLowerCase}Flux
					.thenMany(repository.findAll())
					.subscribe(System.out::println);

            /*operations.collectionExists(${entityName}.class)
                    .flatMap(exists -> exists ? operations.dropCollection(${entityName}.class) : Mono.just(exists))
					.thenMany(v -> operations.createCollection(${entityName}.class))
					.thenMany(${entityNameLowerCase}Flux)
					.thenMany(repository.findAll())
					.subscribe(System.out::println);*/
		};
	}

	@Bean
	RouterFunction<ServerResponse> routes(${entityName}Controller controller) {
//		return route(GET("/${entityNameLowerCase}s").and(accept(APPLICATION_JSON)), controller::getAll${entityName}s)
//				.andRoute(POST("/${entityNameLowerCase}s").and(contentType(APPLICATION_JSON)), controller::save${entityName})
//				.andRoute(DELETE("/${entityNameLowerCase}s").and(accept(APPLICATION_JSON)), controller::deleteAll${entityName}s)
//				.andRoute(GET("/${entityNameLowerCase}s/events").and(accept(TEXT_EVENT_STREAM)), controller::get${entityName}Events)
//				.andRoute(GET("/${entityNameLowerCase}s/{id}").and(accept(APPLICATION_JSON)), controller::get${entityName})
//				.andRoute(PUT("/${entityNameLowerCase}s/{id}").and(contentType(APPLICATION_JSON)), controller::update${entityName})
//				.andRoute(DELETE("/${entityNameLowerCase}s/{id}").and(accept(APPLICATION_JSON)), controller::delete${entityName});

		return nest(path("/${entityNameLowerCase}s"),
				nest(accept(APPLICATION_JSON).or(contentType(APPLICATION_JSON)).or(accept(TEXT_EVENT_STREAM)),
						route(
							#if (${includeRead} == "true")
							GET("/"), controller::getAll${entityName}s)
							#if (${includeCreate} == "true")
								.andRoute(method(HttpMethod.POST), controller::save${entityName})#end
								#if (${includeDelete} == "true")
								.andRoute(DELETE("/"), controller::deleteAll${entityName}s)#end
								#if (${includeEvent} == "true")
								.andRoute(GET("/events"), controller::get${entityName}Events)#end
								#if (${includeRead} == "true")
								.andNest(path("/{id}"),
										route(method(HttpMethod.GET), controller::get${entityName})
										#if (${includeUpdate} == "true")
												.andRoute(method(HttpMethod.PUT), controller::update${entityName})#end
												#if (${includeDelete} == "true")
												.andRoute(method(HttpMethod.DELETE), controller::delete${entityName})#end#end
										)#end
						)
				);
	}
}

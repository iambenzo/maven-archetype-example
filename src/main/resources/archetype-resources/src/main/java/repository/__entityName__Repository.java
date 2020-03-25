#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.repository;

import ${package}.model.${entityName};
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ${entityName}Repository
        extends ReactiveMongoRepository<${entityName}, String> {
}

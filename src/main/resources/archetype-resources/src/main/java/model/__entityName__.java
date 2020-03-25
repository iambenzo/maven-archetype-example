#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document
public class ${entityName} {
    @Id
    private String id;

    private String name;

    public ${entityName}(){}

    public ${entityName}(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ${entityName} ${entityNameLowerCase} = (${entityName}) o;
        return Objects.equals(id, ${entityNameLowerCase}.id) &&
                Objects.equals(name, ${entityNameLowerCase}.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "${entityName}{" +
                "id='" + id + '${symbol_escape}'' +
                ", name='" + name + '${symbol_escape}'' +
                '}';
    }
}

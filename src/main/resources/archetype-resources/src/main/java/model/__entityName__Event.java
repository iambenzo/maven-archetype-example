#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.model;

import java.util.Objects;

public class ${entityName}Event {
    private Long eventId;

    private String eventType;

    public ${entityName}Event() {}

    public ${entityName}Event(Long eventId, String eventType) {
        this.eventId = eventId;
        this.eventType = eventType;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ${entityName}Event that = (${entityName}Event) o;
        return Objects.equals(eventId, that.eventId) &&
                Objects.equals(eventType, that.eventType);
    }

    @Override
    public int hashCode() {

        return Objects.hash(eventId, eventType);
    }

    @Override
    public String toString() {
        return "${entityName}Event{" +
                "eventId='" + eventId + '${symbol_escape}'' +
                ", eventType='" + eventType + '${symbol_escape}'' +
                '}';
    }
}

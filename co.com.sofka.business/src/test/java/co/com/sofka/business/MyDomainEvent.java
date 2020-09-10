package co.com.sofka.business;

import co.com.sofka.domain.generic.DomainEvent;

public class MyDomainEvent extends DomainEvent {
    public MyDomainEvent() {
        super("co.com.sofka.domain");
    }
}

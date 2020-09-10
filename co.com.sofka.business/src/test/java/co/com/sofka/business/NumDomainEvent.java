package co.com.sofka.business;

import co.com.sofka.domain.generic.DomainEvent;

public class NumDomainEvent extends DomainEvent {
    private final Integer num;

    public NumDomainEvent(Integer num) {
        super("co.com.sofka.num");
        this.num = num;
    }

    public Integer getNum() {
        return num;
    }

}

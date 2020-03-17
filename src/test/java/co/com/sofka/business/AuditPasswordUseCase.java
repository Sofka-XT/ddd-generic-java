package co.com.sofka.business;


import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.ResponseEvents;
import co.com.sofka.business.support.TriggeredEvent;
import co.com.sofka.domain.generic.AggregateRootId;
import co.com.sofka.domain.user.events.AuditRegistered;
import co.com.sofka.domain.user.events.UserPasswordUpdated;
import co.com.sofka.infraestructure.annotation.BusinessLogin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

@BusinessLogin
public class AuditPasswordUseCase extends UseCase<TriggeredEvent<UserPasswordUpdated>, ResponseEvents> {

    private static final Logger logger = LoggerFactory.getLogger(AuditPasswordUseCase.class);

    @Override
    protected void executeUseCase(TriggeredEvent<UserPasswordUpdated> request) {
        AggregateRootId aggregateRootId = request.getDomainEvent().aggregateRootId;
        logger.info("executeUseCase[AuditPasswordUseCase]" + aggregateRootId.value());
        emit().onSuccess(new ResponseEvents(Arrays.asList(new AuditRegistered(aggregateRootId))));
    }

}

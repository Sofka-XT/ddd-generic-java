package co.com.sofka.domain.generic;

import java.time.Instant;
import java.util.UUID;

public abstract class DomainEvent {
    private Long versionType;

    public final Instant when;
    public final UUID uuid;
    public final String type;

    protected DomainEvent(final String type ) {
        this.type = type;
        this.when = Instant.now();
        this.uuid = UUID.randomUUID();
        this.versionType = 0L;
    }


    public void nextVersionType(Long versionType) {
        this.versionType = versionType + 1;
    }

    public Long getVersionType() {
        return versionType;
    }
}

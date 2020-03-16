package co.com.sofka.domain.user.queries;

import co.com.sofka.domain.generic.Query;

public class All implements Query {
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    private String region;

}

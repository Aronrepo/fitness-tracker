package org.codecool.fitnesstracker.fitnesstracker.user;

import java.util.HashSet;
import java.util.Set;


import static org.codecool.fitnesstracker.fitnesstracker.user.Authorities.*;

public enum Role {
    USER(new HashSet<>(Set.of(SET_CALORIES,
            GET_CALORIES,
            SET_ACTIVITIES,
            GET_ACTIVITIES,
            GET_ANALYZE,
            CHANGE_USER,
            GET_FOODTYPE))),
    ADMIN(new HashSet<>(Set.of(BAN_USER)));

    private final Set<Authorities> authorities;

    Role(Set<Authorities> authorities) {
        this.authorities = authorities;
    }

    public Set<Authorities> getAuthorities() {
        return authorities;
    }

}

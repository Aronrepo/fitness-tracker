package org.codecool.fitnesstracker.fitnesstracker.user;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static org.codecool.fitnesstracker.fitnesstracker.user.Authorities.*;

public enum Role {
    USER(new HashSet<>(Set.of(SET_CALORIES,
            GET_CALORIES,
            SET_ACTIVITIES,
            GET_ACTIVITIES,
            GET_ANALYZE,
            CHANGE_USER))),
    ADMIN(new HashSet<>(Set.of(BAN_USER)));

    private final Set<Authorities> authorities;

    Role(Set<Authorities> authorities) {
        this.authorities = authorities;
    }

    public Set<Authorities> getAuthorities() {
        return authorities;
    }



    // example for mapping authorities to roles.
    // Exercise: Make it adhere to Open/Close principle
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        var authorityNames = switch (this) {
//            // FIXME: Don't use had-written string literals
//            case USER -> Set.of("SET_CALORIES", "REMOVE_ACTIVITY");
//            case ADMIN -> Set.of("BAN_USER");
//        };
//        return authorityNames.stream().map(SimpleGrantedAuthority::new).toList();
//    }
}

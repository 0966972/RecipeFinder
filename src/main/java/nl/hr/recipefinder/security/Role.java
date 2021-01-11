package nl.hr.recipefinder.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public enum Role {
  USER(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))),
  ADMIN(Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))),
  BANNED(Collections.singletonList(new SimpleGrantedAuthority("ROLE_BANNED")));

  private final Collection<GrantedAuthority> authorities;

  Role(Collection<GrantedAuthority> authorities) {
    this.authorities = authorities;
  }

  public Collection<GrantedAuthority> getAuthorities() {
    return authorities;
  }

}

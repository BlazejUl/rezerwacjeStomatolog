package uliasz.blazej.backend.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uliasz.blazej.backend.database.entity.Dentist;
import uliasz.blazej.backend.database.entity.Patient;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    private final String email;
    private final String password;
    private final String role;

    public CustomUserDetails(Patient patient, String role){
        this.email = patient.getEmail();
        this.password = patient.getPassword();
        this.role = role;
    }
    public CustomUserDetails(Dentist dentist, String role){
        this.email = dentist.getEmail();
        this.password = dentist.getPassword();
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() { return password; }

    @Override
    public String getUsername() { return email; }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}

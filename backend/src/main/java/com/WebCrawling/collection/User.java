package com.WebCrawling.collection;

import java.util.Collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Document(collection = "users")

public class User implements UserDetails {

    @Id
    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Role role;

    /*
     * Renvoie la liste des autorisations accordées à l'utilisateur. Comme par
     * exemple si il peut accéder à certaines ressources
     */
    // getAuthorities() Récupère les autorisations (rôles) associés à l'user
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Retourne une liste avec une seule autorité SimpleGrantedAuthority et on lui
        // attribut le rôle actuel de l'utilisateur en appelant role.name
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    // Retourne le nom d'utilisateur
    @Override
    public String getUsername() {
        return email;
    }

    // Retourne le mot de passe
    @Override
    public String getPassword() {
        return password;
    }

    /*
     * Indique si le compte a expiré, si isAccountNonExpired = false le User ne peut
     * pas être authentifié
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // Si false User locked il ne peut pas être authentifié
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /*
     * Si false les informatioons du USer sont expiré il ne peut pas être
     * authentifié
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // Si isEnabled = false le User il ne peut pas être authentifié
    @Override
    public boolean isEnabled() {
        return true;
    }

}

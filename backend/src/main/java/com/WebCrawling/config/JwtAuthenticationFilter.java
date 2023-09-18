package com.WebCrawling.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mongodb.lang.NonNull;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    // OncePerRequestFilter permet d'utiliser la méthode doFilterInternal afin de
    // créer des filtres qui seront appelé sur chaques requêtes

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        if (request.getServletPath().contains("/api/v1/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        /*
         * Partie 1 : Partie du filtre qui extrait le token et examine l'en-tête
         * Authorisation pour vérifier qu'on a un token présent qui commence par bearer.
         * Sinon cela signifie qu'on a pas de token ou token non valide et on passe au
         * filtre suivant
         */

        /*
         * Authorisation est souvent utilisé pour envoyé des informations sur
         * l'authentification comme des jetons jwt par exemple
         */

        final String authHeader = request.getHeader("Authorization"); // Header qui contient le JWT Token
        final String jwt; // Sera utilisée pour stocker le jeton JWT extrait de l'en-tête Authorization.
        final String userEmail; // Sera utilisée pour stocker l'e-mail de l'utilisateur extrait du jeton JWT

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            // traitement de la requête intérompu car jwt pas valide donc passe au prochain
            // filtre
            return;
        }
        jwt = authHeader.substring(7); // 7 car bearer plus l'espace = 7 caractères

        /*
         * Partie 2 Le Token étant vérifié on vérifie maintenant qu'on a un User dans la
         * base de donnée correspondant aux données extraites du token pour cela on doit
         * utiliser le JwtService pour extraire le username
         */

        userEmail = jwtService.extractUsername(jwt); // Méthode de Jwt Service pour extraire le username

        /*
         * Partie 3 Vérifie que le Token est valid et Update le SecurityContextHolder
         * puis envoie une requête au DispatcherServlet
         */

        // Vérifie que le userEmail n'est pas nul et que la personne n'est pas encore
        // authentifié
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
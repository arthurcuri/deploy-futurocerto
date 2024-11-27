package com.tis3.futuro_certo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tis3.futuro_certo.models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);

    List<Usuario> findByIsAdvogadoTrueAndPortfolioIsNull();

    List<Usuario> findByIsAdvogadoFalse();
    
    List<Usuario> findByIsAdvogadoTrueAndPortfolioIsNotNull();

}

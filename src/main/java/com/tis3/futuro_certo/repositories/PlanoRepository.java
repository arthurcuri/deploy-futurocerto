package com.tis3.futuro_certo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tis3.futuro_certo.models.Plano;

@Repository
public interface PlanoRepository extends JpaRepository<Plano, Long> {
    boolean existsByNome(String nome);
}

package com.tis3.futuro_certo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tis3.futuro_certo.models.Evento;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
    
}

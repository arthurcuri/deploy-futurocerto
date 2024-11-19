package com.tis3.futuro_certo.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tis3.futuro_certo.models.Agenda;
import com.tis3.futuro_certo.models.Usuario;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Long> {
    List<Agenda> findByAdvogado(Usuario advogado);
    List<Agenda> findByAdvogadoAndDataHoraAfter(Usuario advogado, LocalDateTime now);
    
}

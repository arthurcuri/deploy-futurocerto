package com.tis3.futuro_certo.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tis3.futuro_certo.models.Agenda;
import com.tis3.futuro_certo.models.Usuario;

import org.springframework.data.jpa.repository.Query;


@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Long> {
    List<Agenda> findByAdvogado(Usuario advogado);
    List<Agenda> findByAdvogadoAndDataHoraAfter(Usuario advogado, LocalDateTime now);
    List<Agenda> findByAdvogadoIsNull();
    
    @Query("SELECT a FROM Agenda a WHERE a.id NOT IN (SELECT e.agenda.id FROM Evento e WHERE e.agenda IS NOT NULL)")
    List<Agenda> findAgendasSemEvento();
    
}

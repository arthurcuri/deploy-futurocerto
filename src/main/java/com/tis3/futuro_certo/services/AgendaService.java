package com.tis3.futuro_certo.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tis3.futuro_certo.models.Agenda;
import com.tis3.futuro_certo.models.Usuario;
import com.tis3.futuro_certo.repositories.AgendaRepository;
import com.tis3.futuro_certo.services.exception.ResourceNotFoundException;

@Service
public class AgendaService {

    @Autowired
    private AgendaRepository agendaRepository;

    public Agenda createDisponibilidade(Agenda agenda) {
        return agendaRepository.save(agenda);
    }

    public List<Agenda> getDisponibilidadesAdvogado(Usuario advogado) {
        return agendaRepository.findByAdvogado(advogado);
    }


    public List<Agenda> getDisponibilidadesFuturas(Usuario advogado) {
        return agendaRepository.findByAdvogadoAndDataHoraAfter(advogado, LocalDateTime.now());
    }

    public void deleteDisponibilidade(Long id) {
        agendaRepository.deleteById(id);
    }

    public Agenda getDisponibilidadeById(Long id) {
        Optional<Agenda> agenda = agendaRepository.findById(id);
        if (agenda.isPresent()) {
            return agenda.get();
        } else {
            throw new ResourceNotFoundException("Disponibilidade não encontrada com ID: " + id);
        }
    }

    public Agenda updateDisponibilidade(Agenda agenda) {
        if (agenda.getId() == null || !agendaRepository.existsById(agenda.getId())) {
            throw new ResourceNotFoundException("Disponibilidade não encontrada com ID: " + agenda.getId());
        }
        return agendaRepository.save(agenda);
    }

    public List<Agenda> getAllDisponibilidades() {
        return agendaRepository.findAll();
    }

    public List<Agenda> getDisponibilidadesSemAdvogado() {
        return agendaRepository.findByAdvogadoIsNull();
    }

    public List<Agenda> getAgendasSemEvento() {
        return agendaRepository.findAgendasSemEvento();
    }
    
}

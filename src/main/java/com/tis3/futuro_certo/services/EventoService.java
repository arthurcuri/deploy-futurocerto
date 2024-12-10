package com.tis3.futuro_certo.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tis3.futuro_certo.dtos.EventoDTO;
import com.tis3.futuro_certo.models.Agenda;
import com.tis3.futuro_certo.models.Evento;
import com.tis3.futuro_certo.models.Usuario;
import com.tis3.futuro_certo.repositories.AgendaRepository;
import com.tis3.futuro_certo.repositories.EventoRepository;
import com.tis3.futuro_certo.services.exception.ResourceNotFoundException;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private AgendaRepository agendaRepository;

    
    public Evento criarEvento(Long agendaId, Usuario cliente, boolean isPago) {
        
        Agenda agenda = agendaRepository.findById(agendaId)
                .orElseThrow(() -> new ResourceNotFoundException("Agenda não encontrada com ID: " + agendaId));
    
        
        Usuario advogado = agenda.getAdvogado();
    
        
        Evento evento = new Evento(agenda.getDataHora(), advogado, cliente, agenda, isPago); 
    
        return eventoRepository.save(evento);
    }
    

    
    public Evento getEventoById(Long id) {
        Optional<Evento> evento = eventoRepository.findById(id);
        return evento.orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado com ID: " + id));
    }

   
    public void deleteEvento(Long id) {
        if (!eventoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Evento não encontrado com ID: " + id);
        }
        eventoRepository.deleteById(id);
    }

    public List<EventoDTO> findAllEventos() {
        List<Evento> eventos = eventoRepository.findAll(); 
        return eventos.stream()
                      .map(evento -> new EventoDTO(
                          evento.getId(),
                          evento.getDataEvento(),
                          evento.getAdvogado() != null ? evento.getAdvogado().getNome() : null, 
                          evento.getCliente() != null ? evento.getCliente().getNome() : null,  
                          evento.isPago()
                      ))
                      .collect(Collectors.toList()); 
    }
    

    public Evento salvarEvento(Evento evento) {
        return eventoRepository.save(evento);
    }
    
    public Evento findById(Long id) {
        Optional<Evento> evento = eventoRepository.findById(id);
        if (evento.isPresent()) {
            return evento.get(); 
        } else {
            throw new RuntimeException("Evento não encontrado com ID: " + id);
        }
    }
}

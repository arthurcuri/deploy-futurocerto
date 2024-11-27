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

    // Método para criar um evento
    public Evento criarEvento(Long agendaId, Usuario cliente, boolean isPago) {
        // Busca a disponibilidade pela Agenda
        Agenda agenda = agendaRepository.findById(agendaId)
                .orElseThrow(() -> new ResourceNotFoundException("Agenda não encontrada com ID: " + agendaId));
    
        // Pega o advogado da Agenda
        Usuario advogado = agenda.getAdvogado();
    
        // Cria o evento usando a data, o advogado da Agenda e o parâmetro isPago
        Evento evento = new Evento(agenda.getDataHora(), advogado, cliente, agenda, isPago); // Passando isPago
    
        return eventoRepository.save(evento);
    }
    

    // Método para obter um evento por ID
    public Evento getEventoById(Long id) {
        Optional<Evento> evento = eventoRepository.findById(id);
        return evento.orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado com ID: " + id));
    }

    // Método para deletar um evento
    public void deleteEvento(Long id) {
        if (!eventoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Evento não encontrado com ID: " + id);
        }
        eventoRepository.deleteById(id);
    }

    public List<EventoDTO> findAllEventos() {
        List<Evento> eventos = eventoRepository.findAll(); // Obtém todos os eventos do banco
        return eventos.stream()
                      .map(evento -> new EventoDTO(
                          evento.getId(),
                          evento.getDataEvento(),
                          evento.getAdvogado() != null ? evento.getAdvogado().getNome() : null, // Obtém nome do advogado
                          evento.getCliente() != null ? evento.getCliente().getNome() : null,  // Obtém nome do cliente
                          evento.isPago()
                      ))
                      .collect(Collectors.toList()); // Converte para lista de DTOs
    }
    

    public Evento salvarEvento(Evento evento) {
        return eventoRepository.save(evento);
    }
    
    public Evento findById(Long id) {
        Optional<Evento> evento = eventoRepository.findById(id);
        if (evento.isPresent()) {
            return evento.get();  // Retorna o evento encontrado
        } else {
            throw new RuntimeException("Evento não encontrado com ID: " + id);
        }
    }
}

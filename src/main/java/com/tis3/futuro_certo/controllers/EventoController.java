package com.tis3.futuro_certo.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tis3.futuro_certo.dtos.EventoDTO;
import com.tis3.futuro_certo.models.Agenda;
import com.tis3.futuro_certo.models.Evento;
import com.tis3.futuro_certo.models.Usuario;
import com.tis3.futuro_certo.services.AgendaService;
import com.tis3.futuro_certo.services.EventoService;
import com.tis3.futuro_certo.services.UsuarioService;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AgendaService agendaService;

    @PostMapping("/advogado/{advogadoId}/cliente/{clienteId}/agenda/{agendaId}/criar")
    public ResponseEntity<Evento> criarEvento(
            @PathVariable Long advogadoId,
            @PathVariable Long clienteId,
            @PathVariable Long agendaId,
            @RequestParam(required = false, defaultValue = "false") boolean isPago) {

        // Buscar o advogado
        Usuario advogado = usuarioService.getUsuarioById(advogadoId);
        if (advogado == null || !advogado.getIsAdvogado()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Buscar o cliente
        Usuario cliente = usuarioService.getUsuarioById(clienteId);
        if (cliente == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Buscar a disponibilidade na Agenda
        Agenda agenda = agendaService.getDisponibilidadeById(agendaId);
        if (agenda == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Verificar se o advogado na disponibilidade corresponde ao advogado passado
        if (!agenda.getAdvogado().getId().equals(advogadoId)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Criar o evento
        Evento evento = new Evento();
        evento.setDataEvento(LocalDateTime.now()); // ou outra data específica
        evento.setAdvogado(advogado);
        evento.setCliente(cliente);
        evento.setAgenda(agenda);
        evento.setIsPago(isPago); // Define o campo isPago

        // Chamada correta ao método criarEvento
        Evento novoEvento = eventoService.criarEvento(agendaId, cliente, isPago); // Passando isPago

        return new ResponseEntity<>(novoEvento, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarEvento(@PathVariable Long id) {
        eventoService.deleteEvento(id);
        return new ResponseEntity<>("Evento deletado com sucesso!", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/eventos")
    public ResponseEntity<List<EventoDTO>> getAllEventos() {
        List<EventoDTO> eventos = eventoService.findAllEventos();
        return ResponseEntity.ok(eventos);
    }

    @PutMapping("/{id}/alterar-pago")
    public ResponseEntity<Evento> alterarStatusPagamento(@PathVariable Long id, @RequestParam boolean isPago) {
        // Buscar o evento pelo ID
        Evento evento = eventoService.getEventoById(id);
        if (evento == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Alterar o status de pagamento
        evento.setIsPago(isPago);

        // Salvar a alteração
        Evento eventoAtualizado = eventoService.salvarEvento(evento); // Supondo que o método salvarEvento existe no
                                                                      // EventoService

        return new ResponseEntity<>(eventoAtualizado, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoDTO> getEvento(@PathVariable Long id) {
        // Busca o evento pelo ID
        Evento evento = eventoService.findById(id);

        // Verifica se o evento foi encontrado
        if (evento == null) {
            return ResponseEntity.notFound().build();
        }

        // Mapeia o evento para o DTO
        EventoDTO eventoDTO = new EventoDTO(
                evento.getId(),
                evento.getDataEvento(),
                evento.getAdvogado() != null ? evento.getAdvogado().getNome() : null, // Nome do advogado
                evento.getCliente() != null ? evento.getCliente().getNome() : null, // Nome do cliente
                evento.isPago() // Indica se o evento foi pago
        );

        return ResponseEntity.ok(eventoDTO); // Retorna o DTO no corpo da resposta
    }

}

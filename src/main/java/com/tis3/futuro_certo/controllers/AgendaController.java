package com.tis3.futuro_certo.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tis3.futuro_certo.dtos.DisponibilidadeDTO;
import com.tis3.futuro_certo.models.Agenda;
import com.tis3.futuro_certo.models.Usuario;
import com.tis3.futuro_certo.services.AgendaService;
import com.tis3.futuro_certo.services.UsuarioService;

@CrossOrigin(origins = "http://localhost:5500")
@RestController
@RequestMapping("/agenda")
public class AgendaController {

    @Autowired
    private AgendaService agendaService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/advogado/{advogadoId}/disponibilidade")
    public ResponseEntity<String> criarDisponibilidade(
            @PathVariable Long advogadoId, @RequestBody Agenda agenda) {
        Usuario advogado = usuarioService.getUsuarioById(advogadoId);

        if (!advogado.getIsAdvogado()) {
            return new ResponseEntity<>("O usuário não é um advogado.", HttpStatus.BAD_REQUEST);
        }

        agenda.setAdvogado(advogado);
        agendaService.createDisponibilidade(agenda);

        return new ResponseEntity<>("Disponibilidade criada com sucesso!", HttpStatus.CREATED);
    }

    @GetMapping("/advogado/{advogadoId}/disponibilidades")
    public ResponseEntity<List<Agenda>> listarDisponibilidades(@PathVariable Long advogadoId) {
        Usuario advogado = usuarioService.getUsuarioById(advogadoId);
        List<Agenda> disponibilidades = agendaService.getDisponibilidadesFuturas(advogado);

        return new ResponseEntity<>(disponibilidades, HttpStatus.OK);
    }

    @DeleteMapping("/disponibilidade/{id}")
    public ResponseEntity<String> removerDisponibilidade(@PathVariable Long id) {
        agendaService.deleteDisponibilidade(id);
        return new ResponseEntity<>("Disponibilidade removida com sucesso", HttpStatus.NO_CONTENT);
    }

    @PutMapping("/advogado/{advogadoId}/disponibilidade/{id}")
    public ResponseEntity<String> atualizarDisponibilidade(
            @PathVariable Long advogadoId,
            @PathVariable Long id,
            @RequestBody Agenda agenda) {
        Usuario advogado = usuarioService.getUsuarioById(advogadoId);

        if (!advogado.getIsAdvogado()) {
            return new ResponseEntity<>("O usuário não é um advogado.", HttpStatus.BAD_REQUEST);
        }

        // Você deve buscar a disponibilidade existente
        Agenda disponibilidadeExistente = agendaService.getDisponibilidadeById(id);

        // Atualize os campos da disponibilidade conforme necessário
        disponibilidadeExistente.setDataHora(agenda.getDataHora());
        // Você pode adicionar mais campos para atualizar aqui...

        agendaService.updateDisponibilidade(disponibilidadeExistente);

        return new ResponseEntity<>("Disponibilidade atualizada com sucesso!", HttpStatus.OK);
    }

    @GetMapping("/disponibilidades")
    public ResponseEntity<List<DisponibilidadeDTO>> getAllDisponibilidades() {
        List<Agenda> disponibilidades = agendaService.getAllDisponibilidades();
        List<DisponibilidadeDTO> dtos = disponibilidades.stream()
                .map(DisponibilidadeDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

}

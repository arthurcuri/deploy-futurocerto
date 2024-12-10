package com.tis3.futuro_certo.dtos;

import java.time.LocalDateTime;

import com.tis3.futuro_certo.models.Agenda;

public class DisponibilidadeDTO {
    private Long id; 
    private LocalDateTime dataHora;
    private String advogadoNome;
    private String advogadoEmail;
    private Long advogadoId; 

    public DisponibilidadeDTO(Agenda agenda) {
        this.id = agenda.getId(); 
        this.dataHora = agenda.getDataHora();
        this.advogadoNome = agenda.getAdvogado().getNome();
        this.advogadoEmail = agenda.getAdvogado().getEmail();
        this.advogadoId = agenda.getAdvogado().getId(); 
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getAdvogadoNome() {
        return advogadoNome;
    }

    public void setAdvogadoNome(String advogadoNome) {
        this.advogadoNome = advogadoNome;
    }

    public String getAdvogadoEmail() {
        return advogadoEmail;
    }

    public void setAdvogadoEmail(String advogadoEmail) {
        this.advogadoEmail = advogadoEmail;
    }

    public Long getAdvogadoId() {
        return advogadoId; 
    }

    public void setAdvogadoId(Long advogadoId) {
        this.advogadoId = advogadoId; 
    }
}

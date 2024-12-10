package com.tis3.futuro_certo.dtos;

import java.time.LocalDateTime;

public class EventoDTO {

    private Long id;
    private LocalDateTime dataEvento;
    private String advogadoNome;
    private String clienteNome;
    private boolean isPago;

    
    public EventoDTO(Long id, LocalDateTime dataEvento, String advogadoNome, String clienteNome, boolean isPago) {
        this.id = id;
        this.dataEvento = dataEvento;
        this.advogadoNome = advogadoNome;
        this.clienteNome = clienteNome;
        this.isPago = isPago;
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(LocalDateTime dataEvento) {
        this.dataEvento = dataEvento;
    }

    public String getAdvogadoNome() {
        return advogadoNome;
    }

    public void setAdvogadoNome(String advogadoNome) {
        this.advogadoNome = advogadoNome;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }

    public boolean isPago() {
        return isPago;
    }

    public void setPago(boolean isPago) {
        this.isPago = isPago;
    }
}

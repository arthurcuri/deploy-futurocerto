package com.tis3.futuro_certo.models;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "A data do evento não pode ser nula")
    private LocalDateTime dataEvento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advogado_id")
    private Usuario advogado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Usuario cliente;

    @OneToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "agenda_id", referencedColumnName = "id")
    private Agenda agenda;

    // Novo campo isPago
    private boolean isPago;

    public Evento() {}

    public Evento(LocalDateTime dataEvento, Usuario advogado, Usuario cliente, Agenda agenda, boolean isPago) {
        this.dataEvento = dataEvento;
        this.advogado = advogado;
        this.cliente = cliente;
        this.agenda = agenda;
        this.isPago = isPago; // Inicializa o campo isPago
    }

     // Método setter para isPago
     public void setIsPago(boolean isPago) {
        this.isPago = isPago;
    }

    // Método getter para isPago
    public boolean isPago() {
        return isPago;
    }
}   

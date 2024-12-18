package com.tis3.futuro_certo.models;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "A data e hora da disponibilidade não pode ser nula")
    @Future(message = "A data e hora devem ser no futuro")
    private LocalDateTime dataHora;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario advogado;

    public Agenda() {}

    public Agenda(LocalDateTime dataHora, Usuario advogado) {
        this.dataHora = dataHora;
        this.advogado = advogado;
    }
}

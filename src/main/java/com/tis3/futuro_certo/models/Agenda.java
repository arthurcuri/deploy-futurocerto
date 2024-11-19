package com.tis3.futuro_certo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "A data e hora da disponibilidade não pode ser nula")
    @Future(message = "A data e hora devem ser no futuro")
    private LocalDateTime dataHora;

    @ManyToOne(fetch = FetchType.LAZY) // Use LAZY para evitar carregar agendas desnecessariamente
    @JoinColumn(name = "usuario_id")
    private Usuario advogado;

    public Agenda() {}

    public Agenda(LocalDateTime dataHora, Usuario advogado) {
        this.dataHora = dataHora;
        this.advogado = advogado;
    }
}

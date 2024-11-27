package com.tis3.futuro_certo.models;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Beneficio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Remuneração não pode ser nula")
    @ElementCollection
    private List<Double> remuneracao;

    @NotNull(message = "Teto INSS não pode ser nulo")
    @ElementCollection
    private List<Double> tetoINSS;

    @NotNull(message = "INPC não pode ser nulo")
    @ElementCollection
    private List<Double> inpc;

    public Beneficio() {}

    public Beneficio(List<Double> remuneracao, List<Double> tetoINSS, List<Double> inpc) {
        this.remuneracao = remuneracao;
        this.tetoINSS = tetoINSS;
        this.inpc = inpc;
    }
}

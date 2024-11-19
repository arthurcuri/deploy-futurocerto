package com.tis3.futuro_certo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class Plano {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome não pode ser vazio")
    private String nome;

    @NotBlank(message = "Descrição não pode ser vazia")
    private String descricao;

    @NotNull(message = "Preço não pode ser nulo")
    private Double preco;

    @NotNull(message = "isMensal não pode ser nulo")
    private Boolean isMensal;

    @NotBlank(message = "Chave PIX não pode ser vazia")
    private String chavePix;

    public Plano() {}

    public Plano(String nome, String descricao, Double preco, Boolean isMensal, String chavePix) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.isMensal = isMensal;
        this.chavePix = chavePix;
    }
}
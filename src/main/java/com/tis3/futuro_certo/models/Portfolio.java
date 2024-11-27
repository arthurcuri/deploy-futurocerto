package com.tis3.futuro_certo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Formação não pode ser vazia")
    private String formacao;

    @NotBlank(message = "Descrição não pode ser vazia")
    @Lob
    private String descricao;

    @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario advogado;

    public Portfolio() {}

    public Portfolio(String formacao, String descricao, Usuario advogado) {
        this.formacao = formacao;
        this.descricao = descricao;
        this.advogado = advogado;
    }
}

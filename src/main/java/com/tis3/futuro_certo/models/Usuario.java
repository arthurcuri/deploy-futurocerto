package com.tis3.futuro_certo.models;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome não pode ser vazio")
    private String nome;

    @NotBlank(message = "Email não pode ser vazio")
    @Email(message = "Email deve ser válido")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Senha não pode ser vazia")
    private String senha;

    @NotNull(message = "IsAdvogado não pode ser nulo")
    private Boolean isAdvogado;

    @OneToOne(mappedBy = "advogado")
    @JsonIgnoreProperties("advogado") 
    private Portfolio portfolio;

    @OneToMany(mappedBy = "advogado")
    private Set<Agenda> agendas;


    public Usuario() {}

    public Usuario(String nome, String email, String senha, Boolean isAdvogado) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.isAdvogado = isAdvogado;
    }
}

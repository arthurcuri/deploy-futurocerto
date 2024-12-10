package com.tis3.futuro_certo.dtos;

import lombok.Data;

@Data
public class PortfolioDTO {

    private Long id;
    private String formacao;
    private String descricao;
    private Long advogadoId; 
    private String advogadoNome; 
}

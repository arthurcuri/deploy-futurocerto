package com.tis3.futuro_certo.dtos;

import lombok.Data;

@Data
public class PortfolioDTO {

    private Long id;
    private String formacao;
    private String descricao;
    private Long advogadoId; // Apenas o ID do advogado
    private String advogadoNome; // Opcional: Nome do advogado para exibição, se necessário
}

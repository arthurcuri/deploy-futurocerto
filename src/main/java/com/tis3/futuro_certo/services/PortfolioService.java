package com.tis3.futuro_certo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tis3.futuro_certo.dtos.PortfolioDTO;
import com.tis3.futuro_certo.models.Portfolio;
import com.tis3.futuro_certo.repositories.PortfolioRepository;
import com.tis3.futuro_certo.repositories.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PortfolioService {

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Conversão de Portfolio para PortfolioDTO
    public PortfolioDTO toDTO(Portfolio portfolio) {
        PortfolioDTO dto = new PortfolioDTO();
        dto.setId(portfolio.getId());
        dto.setFormacao(portfolio.getFormacao());
        dto.setDescricao(portfolio.getDescricao());
        dto.setAdvogadoId(portfolio.getAdvogado().getId());
        dto.setAdvogadoNome(portfolio.getAdvogado().getNome());
        return dto;
    }

    // Conversão de PortfolioDTO para Portfolio
    public Portfolio toEntity(PortfolioDTO dto) {
        Portfolio portfolio = new Portfolio();
        portfolio.setId(dto.getId());
        portfolio.setFormacao(dto.getFormacao());
        portfolio.setDescricao(dto.getDescricao());
        portfolio.setAdvogado(
            usuarioRepository.findById(dto.getAdvogadoId())
                .orElseThrow(() -> new EntityNotFoundException("Advogado não encontrado"))
        );
        return portfolio;
    }

    public Portfolio createPortfolio(Portfolio portfolio) {
        return portfolioRepository.save(portfolio);
    }

    public Portfolio updatePortfolio(Long id, Portfolio portfolioDetails) {
        Portfolio existingPortfolio = getPortfolioById(id);
        existingPortfolio.setFormacao(portfolioDetails.getFormacao());
        existingPortfolio.setDescricao(portfolioDetails.getDescricao());
        existingPortfolio.setAdvogado(portfolioDetails.getAdvogado());
        return portfolioRepository.save(existingPortfolio);
    }

    public Portfolio getPortfolioById(Long id) {
        return portfolioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Portfólio não encontrado"));
    }

    public Page<PortfolioDTO> getAllPortfolios(Pageable pageable) {
        return portfolioRepository.findAll(pageable).map(this::toDTO);
    }

    public void deletePortfolio(Long id) {
        if (!portfolioRepository.existsById(id)) {
            throw new EntityNotFoundException("Portfólio não encontrado");
        }
        portfolioRepository.deleteById(id);
    }
}

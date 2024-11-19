package com.tis3.futuro_certo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tis3.futuro_certo.models.Portfolio;
import com.tis3.futuro_certo.models.Usuario;
import com.tis3.futuro_certo.repositories.PortfolioRepository;
import com.tis3.futuro_certo.repositories.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PortfolioService {

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Portfolio createPortfolio(Portfolio portfolio) {
        Usuario advogado = usuarioRepository.findById(portfolio.getAdvogado().getId())
                .orElseThrow(() -> new EntityNotFoundException("Advogado não encontrado"));

        if (!advogado.getIsAdvogado()) {
            throw new IllegalArgumentException("Usuário não é um advogado.");
        }

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

    public Page<Portfolio> getAllPortfolios(Pageable pageable) {
        try {
            Page<Portfolio> portfolios = portfolioRepository.findAll(pageable);
            System.out.println("Total de portfólios: " + portfolios.getTotalElements());
            return portfolios;
        } catch (Exception e) {
            e.printStackTrace(); // Exibir a exceção no console
            throw e; // Re-lançar a exceção para que o controlador possa capturá-la
        }
    }
    

    public void deletePortfolio(Long id) {
        if (!portfolioRepository.existsById(id)) {
            throw new EntityNotFoundException("Portfólio não encontrado");
        }
        portfolioRepository.deleteById(id);
    }
}

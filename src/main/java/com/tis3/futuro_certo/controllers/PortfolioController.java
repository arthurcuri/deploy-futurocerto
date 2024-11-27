package com.tis3.futuro_certo.controllers;

import com.tis3.futuro_certo.dtos.PortfolioDTO;
import com.tis3.futuro_certo.services.PortfolioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/portfolios")
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    @PostMapping
    public ResponseEntity<PortfolioDTO> createPortfolio(@Valid @RequestBody PortfolioDTO portfolioDTO) {
        var portfolio = portfolioService.toEntity(portfolioDTO);
        var createdPortfolio = portfolioService.createPortfolio(portfolio);
        return new ResponseEntity<>(portfolioService.toDTO(createdPortfolio), HttpStatus.CREATED);
    }

    @GetMapping
    public Page<PortfolioDTO> getAllPortfolios(Pageable pageable) {
        return portfolioService.getAllPortfolios(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PortfolioDTO> getPortfolioById(@PathVariable Long id) {
        var portfolio = portfolioService.getPortfolioById(id);
        return new ResponseEntity<>(portfolioService.toDTO(portfolio), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PortfolioDTO> updatePortfolio(@PathVariable Long id, @Valid @RequestBody PortfolioDTO portfolioDTO) {
        var portfolio = portfolioService.toEntity(portfolioDTO);
        var updatedPortfolio = portfolioService.updatePortfolio(id, portfolio);
        return new ResponseEntity<>(portfolioService.toDTO(updatedPortfolio), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePortfolio(@PathVariable Long id) {
        portfolioService.deletePortfolio(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

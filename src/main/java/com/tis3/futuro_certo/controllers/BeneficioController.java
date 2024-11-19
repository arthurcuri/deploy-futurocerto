package com.tis3.futuro_certo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tis3.futuro_certo.models.Beneficio;
import com.tis3.futuro_certo.services.BeneficioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/beneficios")
public class BeneficioController {

    @Autowired
    private BeneficioService beneficioService;

    @PostMapping("/register")
    public ResponseEntity<String> registerBeneficio(@Valid @RequestBody Beneficio beneficio) {
        try {
            beneficioService.createBeneficio(beneficio);
            return new ResponseEntity<>("Benefício cadastrado com sucesso!", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBeneficio(@PathVariable Long id, @Valid @RequestBody Beneficio beneficio) {
        try {
            Beneficio updatedBeneficio = beneficioService.updateBeneficio(id, beneficio);
            return new ResponseEntity<>(updatedBeneficio, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Beneficio> getBeneficioById(@PathVariable Long id) {
        Beneficio beneficio = beneficioService.getBeneficioById(id);
        return new ResponseEntity<>(beneficio, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<Beneficio>> getAllBeneficios(Pageable pageable) {
        Page<Beneficio> beneficios = beneficioService.getAllBeneficios(pageable);
        return new ResponseEntity<>(beneficios, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBeneficio(@PathVariable Long id) {
        beneficioService.deleteBeneficio(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

package com.tis3.futuro_certo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tis3.futuro_certo.models.Plano;
import com.tis3.futuro_certo.services.PlanoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/planos")
public class PlanoController {

    @Autowired
    private PlanoService planoService;

    @PostMapping("/register")
    public ResponseEntity<String> registerPlano(@Valid @RequestBody Plano plano) {
        try {
            planoService.createPlano(plano);
            return new ResponseEntity<>("Plano cadastrado com sucesso!", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePlano(@PathVariable Long id, @Valid @RequestBody Plano plano) {
        try {
            Plano updatedPlano = planoService.updatePlano(id, plano);
            return new ResponseEntity<>(updatedPlano, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plano> getPlanoById(@PathVariable Long id) {
        Plano plano = planoService.getPlanoById(id);
        return new ResponseEntity<>(plano, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<Plano>> getAllPlanos(Pageable pageable) {
        Page<Plano> planos = planoService.getAllPlanos(pageable);
        return new ResponseEntity<>(planos, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlano(@PathVariable Long id) {
        planoService.deletePlano(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

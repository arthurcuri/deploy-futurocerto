package com.tis3.futuro_certo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tis3.futuro_certo.models.Beneficio;
import com.tis3.futuro_certo.repositories.BeneficioRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class BeneficioService {

    @Autowired
    private BeneficioRepository beneficioRepository;

    public Beneficio createBeneficio(Beneficio beneficio) {
        return beneficioRepository.save(beneficio);
    }

    public Beneficio updateBeneficio(Long id, Beneficio beneficio) {
        if (!beneficioRepository.existsById(id)) {
            throw new EntityNotFoundException("Benefício não encontrado");
        }
        beneficio.setId(id);
        return beneficioRepository.save(beneficio);
    }

    public Beneficio getBeneficioById(Long id) {
        return beneficioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Benefício não encontrado"));
    }

    public Page<Beneficio> getAllBeneficios(Pageable pageable) {
        return beneficioRepository.findAll(pageable);
    }

    public void deleteBeneficio(Long id) {
        if (!beneficioRepository.existsById(id)) {
            throw new EntityNotFoundException("Benefício não encontrado");
        }
        beneficioRepository.deleteById(id);
    }
}

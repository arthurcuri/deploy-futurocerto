    package com.tis3.futuro_certo.services;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.Pageable;
    import org.springframework.stereotype.Service;

    import com.tis3.futuro_certo.models.Plano;
    import com.tis3.futuro_certo.repositories.PlanoRepository;

    import jakarta.persistence.EntityNotFoundException;

    @Service
    public class PlanoService {

        @Autowired
        private PlanoRepository planoRepository;

        public Plano createPlano(Plano plano) throws IllegalArgumentException {
            if (planoRepository.existsByNome(plano.getNome())) {
                throw new IllegalArgumentException("Nome do plano já está em uso");
            }
            return planoRepository.save(plano);
        }

        public Plano updatePlano(Long id, Plano plano) {
            if (!planoRepository.existsById(id)) {
                throw new EntityNotFoundException("Plano não encontrado");
            }
            if (planoRepository.existsByNome(plano.getNome())) {
                throw new IllegalArgumentException("Nome do plano já está em uso");
            }
            plano.setId(id);
            return planoRepository.save(plano);
        }

        public Plano getPlanoById(Long id) {
            return planoRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Plano não encontrado"));
        }

        public Page<Plano> getAllPlanos(Pageable pageable) {
            return planoRepository.findAll(pageable);
        }

        public void deletePlano(Long id) {
            if (!planoRepository.existsById(id)) {
                throw new EntityNotFoundException("Plano não encontrado");
            }
            planoRepository.deleteById(id);
        }
    }

package com.tis3.futuro_certo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tis3.futuro_certo.models.Usuario;
import com.tis3.futuro_certo.repositories.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario createUsuario(Usuario usuario) throws Exception {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new Exception("Email já está sendo utilizado.");
        }
        return usuarioRepository.save(usuario);
    }

    public Usuario updateUsuario(Long id, Usuario usuario) throws Exception {
        if (!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuário não encontrado");
        }

        Optional<Usuario> existingUser = usuarioRepository.findByEmail(usuario.getEmail());
        if (existingUser.isPresent() && !existingUser.get().getId().equals(id)) {
            throw new Exception("Email já está sendo utilizado por outro usuário.");
        }

        usuario.setId(id);
        return usuarioRepository.save(usuario);
    }

    public Usuario getUsuarioById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }

    public Page<Usuario> getAllUsuarios(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    public void deleteUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuário não encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    public Optional<Usuario> getUsuarioByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public List<Usuario> getAdvogadosSemPortfolio() {
        return usuarioRepository.findByIsAdvogadoTrueAndPortfolioIsNull();
    }

    public List<Usuario> getAdvogadosComPortfolio() {
        return usuarioRepository.findByIsAdvogadoTrueAndPortfolioIsNotNull();
    }
    

    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public List<Usuario> getAllUsuariosWithoutPagination() {
        return usuarioRepository.findAll();
    }

    public List<Usuario> getUsuariosNaoAdvogados() {
        return usuarioRepository.findByIsAdvogadoFalse();
    }
}

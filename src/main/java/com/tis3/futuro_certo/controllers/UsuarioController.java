package com.tis3.futuro_certo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tis3.futuro_certo.dtos.LoginRequest;
import com.tis3.futuro_certo.models.Usuario;
import com.tis3.futuro_certo.services.JwtService;
import com.tis3.futuro_certo.services.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUsuario(@Valid @RequestBody Usuario usuario) {
        try {
            usuarioService.createUsuario(usuario);
            return new ResponseEntity<>("Usuário cadastrado com sucesso!", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUsuario(@PathVariable Long id, @Valid @RequestBody Usuario usuario) {
        try {
            Usuario updatedUsuario = usuarioService.updateUsuario(id, usuario);
            return new ResponseEntity<>(updatedUsuario, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping
    public ResponseEntity<Page<Usuario>> getAllUsuarios(Pageable pageable) {
        try {
            Page<Usuario> usuarios = usuarioService.getAllUsuarios(pageable);
            return new ResponseEntity<>(usuarios, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.getUsuarioById(id);
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        try {
            usuarioService.deleteUsuario(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        try {
            Optional<Usuario> usuarioOptional = usuarioService.getUsuarioByEmail(loginRequest.getEmail());

            if (usuarioOptional.isPresent() && usuarioOptional.get().getSenha().equals(loginRequest.getSenha())) {
                String token = jwtService.generateToken(usuarioOptional.get());
                return ResponseEntity.ok(token);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou senha incorretos.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro no servidor.");
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Usuario> getUserByEmail(@PathVariable String email) {
        Optional<Usuario> usuarioOptional = usuarioService.findByEmail(email);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setSenha(null); 
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/advogados/sem-portifolio")
    public ResponseEntity<List<Usuario>> getAdvogadosSemPortfolio() {
        List<Usuario> advogadosSemPortfolio = usuarioService.getAdvogadosSemPortfolio();
        return ResponseEntity.ok(advogadosSemPortfolio);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Usuario>> getAllUsuariosWithoutPagination() {
        try {
            List<Usuario> usuarios = usuarioService.getAllUsuariosWithoutPagination();
            usuarios.forEach(usuario -> usuario.setSenha(null)); 
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/nao-advogados")
    public ResponseEntity<List<Usuario>> getUsuariosNaoAdvogados() {
        List<Usuario> naoAdvogados = usuarioService.getUsuariosNaoAdvogados();
        return ResponseEntity.ok(naoAdvogados);
    }

    @GetMapping("/advogados/com-portifolio")
    public ResponseEntity<List<Usuario>> getAdvogadosComPortfolio() {
        List<Usuario> advogadosComPortfolio = usuarioService.getAdvogadosComPortfolio();
        return ResponseEntity.ok(advogadosComPortfolio);
    }

}
    
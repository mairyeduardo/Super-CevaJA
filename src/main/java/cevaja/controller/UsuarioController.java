package cevaja.controller;

import cevaja.model.Usuario;
import cevaja.model.dto.UsuarioRequestDTO;
import cevaja.model.dto.UsuarioResponseDTO;
import cevaja.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@RestController
@RequestMapping("api/v1/usuario")
public class UsuarioController {

    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/listartodos")
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        return ResponseEntity.ok(this.usuarioService.buscarTodosUsuarios());
    }

    @PostMapping("/adicionar")
    public ResponseEntity<Void> adicionarUsuario(@RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        usuarioService.cadastrarUsuario(usuarioRequestDTO);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @DeleteMapping("/remover/{email}")
    public ResponseEntity<UsuarioResponseDTO> removerUsuarioPorEmail(@PathVariable("email") String email) {
        UsuarioResponseDTO usuarioRemovido = usuarioService.removerPorEmail(email);
        return ResponseEntity.ok(usuarioRemovido);
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<UsuarioResponseDTO> alterarNomeEOuSobrenomeDoUsuarioPassandoID(@PathVariable("id") Long id,
                                                                                         @RequestBody UsuarioRequestDTO usuario) {
        UsuarioResponseDTO usuarioAlterado = usuarioService.alterarNomeEOuSobrenomePorId(id, usuario);
        return ResponseEntity.ok(usuarioAlterado);
    }

}

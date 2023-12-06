package cevaja.service;

import cevaja.model.Usuario;
import cevaja.model.dto.UsuarioRequestDTO;
import cevaja.model.dto.UsuarioResponseDTO;
import cevaja.model.dto.converter.UsuarioConverter;
import cevaja.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<UsuarioResponseDTO> buscarTodosUsuarios() {
        List<Usuario> usuarios =  usuarioRepository.findAll();
        List<UsuarioResponseDTO> usuariosResponse = new ArrayList<>();

        for (Usuario u: usuarios) {
            usuariosResponse.add(UsuarioConverter.converterEntidadeParaDTO(u));
        }

        return usuariosResponse;
    }

    private Usuario buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public void cadastrarUsuario(UsuarioRequestDTO usuarioRequestDTO) {
        String verificarEmailUsuarioASerAdicionado = usuarioRequestDTO.getEmail();
        Usuario usuarioExistente = buscarUsuarioPorEmail(verificarEmailUsuarioASerAdicionado);
        if (usuarioExistente != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Não foi possivel cadastrar usuario, email: " + verificarEmailUsuarioASerAdicionado + " já cadastrado"
            );
        } else {
            Usuario usuarioEntity = UsuarioConverter.converterDTOParaEntidade(usuarioRequestDTO);
            usuarioRepository.save(usuarioEntity);
        }
    }

}

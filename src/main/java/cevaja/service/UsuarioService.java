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
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<UsuarioResponseDTO> usuariosResponse = new ArrayList<>();

        for (Usuario u : usuarios) {
            usuariosResponse.add(UsuarioConverter.converterEntidadeParaDTO(u));
        }

        return usuariosResponse;
    }

    private Usuario buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    private Usuario buscarUsuarioPorCPF(String cpf) {
        return usuarioRepository.findByCpf(cpf);
    }

    public void cadastrarUsuario(UsuarioRequestDTO usuarioRequestDTO) {
        String verificarEmailUsuarioASerAdicionado = usuarioRequestDTO.getEmail();
        String verificarCPFUsuarioASerAdicionado = usuarioRequestDTO.getCpf();
        Usuario EmailusuarioExistente = buscarUsuarioPorEmail(verificarEmailUsuarioASerAdicionado);
        Usuario CpfDoUsuarioExistente = buscarUsuarioPorCPF(verificarCPFUsuarioASerAdicionado);
        if (CpfDoUsuarioExistente != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Não foi possivel cadastrar usuario, cpf: " + verificarCPFUsuarioASerAdicionado + " já cadastrado"

            );
        } else if (EmailusuarioExistente != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Não foi possivel cadastrar usuario, email: " + verificarEmailUsuarioASerAdicionado + " já cadastrado"
            );
        } else {
            Usuario usuarioEntity = UsuarioConverter.converterDTOParaEntidade(usuarioRequestDTO);
            usuarioRepository.save(usuarioEntity);
        }
    }

    public UsuarioResponseDTO removerPorEmail(String email) {

        Usuario usuarioASerRemovido = usuarioRepository.findByEmail(email);

        if (usuarioASerRemovido == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Não é possivel remover um usuario inexistente." +
                            " O Usuario de email: " + email + " não existe na base da dados.");
        } else {
            UsuarioResponseDTO usuarioDto = UsuarioConverter.converterEntidadeParaDTO(usuarioASerRemovido);
            usuarioRepository.delete(usuarioASerRemovido);
            return usuarioDto;
        }
    }

    public UsuarioResponseDTO alterarNomeEOuSobrenomePorId(Long id, UsuarioRequestDTO usuarioRequestDTO) {
        Usuario usuarioEncontrado = usuarioRepository.findById(id).get();

        String nomeUsuarioDTO = usuarioRequestDTO.getNome();
        if (nomeUsuarioDTO != null) {
            usuarioEncontrado.setNome(nomeUsuarioDTO);
        }

        String sobrenomeUsuarioDTO = usuarioRequestDTO.getSobrenome();
        if (sobrenomeUsuarioDTO != null) {
            usuarioEncontrado.setSobrenome(sobrenomeUsuarioDTO);
        }

        usuarioRepository.save(usuarioEncontrado);
        UsuarioResponseDTO usuarioDTO = UsuarioConverter.converterEntidadeParaDTO(usuarioEncontrado);
        return usuarioDTO;
    }


}

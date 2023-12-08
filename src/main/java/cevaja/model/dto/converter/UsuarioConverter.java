package cevaja.model.dto.converter;

import cevaja.model.Usuario;
import cevaja.model.dto.UsuarioRequestDTO;
import cevaja.model.dto.UsuarioResponseDTO;

import java.util.List;

public class UsuarioConverter {

    public static Usuario converterDTOParaEntidade(UsuarioRequestDTO usuarioRequestDTO) {
        Usuario usuarioRequestEntity = new Usuario();
        usuarioRequestEntity.setNome(usuarioRequestDTO.getNome());
        usuarioRequestEntity.setSobrenome(usuarioRequestDTO.getSobrenome());
        usuarioRequestEntity.setDataNascimento(usuarioRequestDTO.getDataNascimento());
        usuarioRequestEntity.setCpf(usuarioRequestDTO.getCpf());
        usuarioRequestEntity.setEmail(usuarioRequestDTO.getEmail());
        usuarioRequestEntity.setSenha(usuarioRequestDTO.getSenha());

        return usuarioRequestEntity;
    }

    public static UsuarioResponseDTO converterEntidadeParaDTO(Usuario usuario) {
        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO();
        usuarioResponseDTO.setNome(usuario.getNome());
        usuarioResponseDTO.setSobrenome(usuario.getSobrenome());
        usuarioResponseDTO.setDataNascimento(usuario.getDataNascimento());
        usuarioResponseDTO.setEmail(usuario.getEmail());

        return usuarioResponseDTO;
    }

}

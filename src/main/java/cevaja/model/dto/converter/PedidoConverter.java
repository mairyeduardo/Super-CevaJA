package cevaja.model.dto.converter;

import cevaja.model.Cerveja;
import cevaja.model.Pedido;
import cevaja.model.Usuario;
import cevaja.model.dto.PedidoRequestDTO;
import cevaja.model.dto.PedidoResponseDTO;

public class PedidoConverter {

    public static Pedido converterDTOParaEntidade(PedidoRequestDTO pedidoRequestDTO, Usuario usuario) {
        Pedido pedidoRequestEntity = new Pedido();
        pedidoRequestEntity.setUsuario(usuario);
        pedidoRequestEntity.setCerveja(pedidoRequestDTO.getCerveja());
        return pedidoRequestEntity;
    }

    public static PedidoResponseDTO converterEntidadeParaDTO(Pedido pedido) {
        PedidoResponseDTO pedidoResponseDTO = new PedidoResponseDTO();
        pedidoResponseDTO.setId(pedido.getId());
        pedidoResponseDTO.setNome(pedido.getUsuario().getNome());
        pedidoResponseDTO.setEmail(pedido.getUsuario().getEmail());
        pedidoResponseDTO.setIdUsuario(pedido.getUsuario().getId());
        pedidoResponseDTO.setCerveja(pedido.getCerveja());
        return pedidoResponseDTO;
    }
}

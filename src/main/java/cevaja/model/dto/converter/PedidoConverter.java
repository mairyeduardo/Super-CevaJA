package cevaja.model.dto.converter;

import cevaja.model.Cerveja;
import cevaja.model.Pedido;
import cevaja.model.dto.PedidoRequestDTO;
import cevaja.model.dto.PedidoResponseDTO;

public class PedidoConverter {

    public static Pedido converterDTOParaEntidade(PedidoRequestDTO pedidoRequestDTO) {
        Pedido pedidoRequestEntity = new Pedido();
        pedidoRequestEntity.setCervejaList(pedidoRequestDTO.getCervejas());
        pedidoRequestEntity.setNome(pedidoRequestDTO.getNome());
        pedidoRequestEntity.setEmail(pedidoRequestDTO.getEmail());
        return pedidoRequestEntity;
    }

    public static PedidoResponseDTO converterEntidadeParaDTO(Pedido pedido) {
        PedidoResponseDTO pedidoResponseDTO = new PedidoResponseDTO();
        pedidoResponseDTO.setId(pedido.getId());
        pedidoResponseDTO.setNome(pedido.getNome());
        pedidoResponseDTO.setEmail(pedido.getEmail());
        pedidoResponseDTO.setIdUsuario(pedido.getIdUsuario());
        pedidoResponseDTO.setCervejas(pedido.getCervejaList());
        return pedidoResponseDTO;
    }
}

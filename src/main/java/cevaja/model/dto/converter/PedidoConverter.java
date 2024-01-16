package cevaja.model.dto.converter;

import cevaja.model.Cerveja;
import cevaja.model.Pedido;
import cevaja.model.Usuario;
import cevaja.model.dto.PedidoCervejaInputDTO;
import cevaja.model.dto.PedidoRequestDTO;
import cevaja.model.dto.PedidoResponseDTO;
import cevaja.service.CervejaService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;

@Service
public class PedidoConverter {

    private CervejaService service;

    public PedidoConverter(CervejaService service) {
        this.service = service;
    }


    public Pedido converterDTOParaEntidade(PedidoRequestDTO pedidoRequestDTO, Usuario usuario) {

        var cervejas = new ArrayList<Cerveja>();

        for (PedidoCervejaInputDTO cervejaPedido : pedidoRequestDTO.getCerveja()) {
            Cerveja cervejaSalva = service.buscarPorId(cervejaPedido.getId());

            if (cervejaSalva == null) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Não é possivel fechar pedido, Cerveja não cadastrado no banco."
                );
            }
            cervejaSalva.setQuantidade(BigDecimal.valueOf(cervejaPedido.getQuantidade()));
            cervejas.add(cervejaSalva);
        }

        Pedido pedidoRequestEntity = new Pedido();
        pedidoRequestEntity.setUsuario(usuario);
        pedidoRequestEntity.setCerveja(cervejas);
        return pedidoRequestEntity;
    }

    public PedidoResponseDTO converterEntidadeParaDTO(Pedido pedido) {
        PedidoResponseDTO pedidoResponseDTO = new PedidoResponseDTO();
        pedidoResponseDTO.setId(pedido.getId());
        pedidoResponseDTO.setNome(pedido.getUsuario().getNome());
        pedidoResponseDTO.setEmail(pedido.getUsuario().getEmail());
        pedidoResponseDTO.setIdUsuario(pedido.getUsuario().getId());
        pedidoResponseDTO.setCerveja(pedido.getCerveja());
        pedidoResponseDTO.setValorTotal(pedido.getValorTotal());
        return pedidoResponseDTO;
    }
}

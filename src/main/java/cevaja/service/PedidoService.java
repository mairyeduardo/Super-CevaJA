package cevaja.service;

import cevaja.integration.response.TemperaturaResponse;
import cevaja.integration.service.TemperaturaIntegrationService;
import cevaja.model.Cerveja;
import cevaja.model.Pedido;
import cevaja.model.Usuario;
import cevaja.model.dto.PedidoRequestDTO;
import cevaja.model.dto.PedidoResponseDTO;
import cevaja.model.dto.converter.PedidoConverter;
import cevaja.repository.PedidoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PedidoService {

    private PedidoRepository pedidoRepository;
    private TemperaturaIntegrationService temperaturaIntegrationService;
    private UsuarioService usuarioService;
    private PedidoConverter pedidoConverter;

    public PedidoService(PedidoRepository pedidoRepository, TemperaturaIntegrationService temperaturaIntegrationService, UsuarioService usuarioService,PedidoConverter pedidoConverter) {
        this.pedidoRepository = pedidoRepository;
        this.temperaturaIntegrationService = temperaturaIntegrationService;
        this.usuarioService = usuarioService;
        this.pedidoConverter = pedidoConverter;
    }

    public List<PedidoResponseDTO> buscarTodosPedidos() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        List<PedidoResponseDTO> pedidoResponse = new ArrayList<>();

        for (Pedido p : pedidos) {
            pedidoResponse.add(pedidoConverter.converterEntidadeParaDTO(p));
        }
        return pedidoResponse;
    }

    public PedidoResponseDTO efetuarPedido(PedidoRequestDTO pedidoRequestDTO) {

        var usuario = usuarioService.buscarPorId(pedidoRequestDTO.getIdUsuario());

        if (Objects.isNull(usuario)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Não é possivel fechar pedido, usuario não cadastrado no banco."
            );
        }

        if (!usuario.podeComprarBebida()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Não é possivel fechar pedido, usuario menor de idade."
            );
        }

        Pedido pedido = pedidoConverter.converterDTOParaEntidade(pedidoRequestDTO, usuario);
        pedido.calculoDoValorTotalDoPedido(temperaturaIntegrationService.buscarTemperaturaAtual());
        pedidoRepository.save(pedido);

       return pedidoConverter.converterEntidadeParaDTO(pedido);
    }

}

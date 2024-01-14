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
    static final BigDecimal valorDescontoQNTItens = BigDecimal.valueOf(10);
    static final BigDecimal valorDescontoGraus = BigDecimal.valueOf(15);
    private PedidoRepository pedidoRepository;
    private TemperaturaIntegrationService temperaturaIntegrationService;
    private UsuarioService usuarioService;

    public PedidoService(PedidoRepository pedidoRepository, TemperaturaIntegrationService temperaturaIntegrationService, UsuarioService usuarioService) {
        this.pedidoRepository = pedidoRepository;
        this.temperaturaIntegrationService = temperaturaIntegrationService;
        this.usuarioService = usuarioService;
    }

    public List<PedidoResponseDTO> buscarTodosPedidos() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        List<PedidoResponseDTO> pedidoResponse = new ArrayList<>();

        for (Pedido p : pedidos) {
            pedidoResponse.add(PedidoConverter.converterEntidadeParaDTO(p));
        }
        return pedidoResponse;
    }

    public String efetuarPedido(PedidoRequestDTO pedidoRequestDTO) {

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

        Pedido pedido = PedidoConverter.converterDTOParaEntidade(pedidoRequestDTO, usuario);
        pedidoRepository.save(pedido);

        String message = "Valor total do pedido = " + calculoDoValorTotalDoPedido(pedidoRequestDTO);
        return message;
    }


    public BigDecimal calculoDoValorTotalDoPedido(PedidoRequestDTO pedidoRequestDTO) {

        List<Cerveja> cervejasPedidas = pedidoRequestDTO.getCerveja();
        BigDecimal valorTotalCervejas = BigDecimal.valueOf(0);
        BigDecimal somarQuantidades = BigDecimal.valueOf(0);

        for (int i = 0; i < cervejasPedidas.size(); i++) {
            BigDecimal quantidade = cervejasPedidas.get(i).getQuantidade();
            somarQuantidades.add(somarQuantidades.add(quantidade));
            BigDecimal valor = cervejasPedidas.get(i).getValor();
            valorTotalCervejas.add(valorTotalCervejas.add(quantidade.multiply(valor)));
        }

        BigDecimal porcentagemDescontoAtual = BigDecimal.valueOf(0);
        Integer quantidadeItensParaDesconto = 10;

        if (somarQuantidades.longValue() >= quantidadeItensParaDesconto) {
            porcentagemDescontoAtual.add(porcentagemDescontoAtual.add(valorDescontoQNTItens));
        }

        TemperaturaResponse temperaturaAtual = temperaturaIntegrationService.buscarTemperaturaAtual();
        Integer grausNecessariosParaDesconto = 22;

        if (temperaturaAtual.getCurrent().getTemp_c() <= grausNecessariosParaDesconto) {
            porcentagemDescontoAtual.add(porcentagemDescontoAtual.add(valorDescontoGraus));
        }

        BigDecimal valorDoDesconto = valorTotalCervejas.multiply(porcentagemDescontoAtual).divide(BigDecimal.valueOf(100));
        BigDecimal valorTotalPedidoComDesconto = valorTotalCervejas.subtract(valorDoDesconto);


        return valorTotalPedidoComDesconto;
    }

}

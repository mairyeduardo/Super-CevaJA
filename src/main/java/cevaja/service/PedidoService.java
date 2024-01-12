package cevaja.service;

import cevaja.integration.response.TemperaturaResponse;
import cevaja.integration.service.TemperaturaIntegrationService;
import cevaja.model.Cerveja;
import cevaja.model.Pedido;
import cevaja.model.Usuario;
import cevaja.model.dto.CervejaResponseDTO;
import cevaja.model.dto.PedidoRequestDTO;
import cevaja.model.dto.PedidoResponseDTO;
import cevaja.model.dto.converter.CervejaConverter;
import cevaja.model.dto.converter.PedidoConverter;
import cevaja.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PedidoService {
    static final BigDecimal valorDescontoQNTItens = BigDecimal.valueOf(10);
    static final BigDecimal valorDescontoGraus = BigDecimal.valueOf(15);
    private PedidoRepository pedidoRepository;
    private TemperaturaIntegrationService temperaturaIntegrationService;

    public PedidoService(PedidoRepository pedidoRepository, TemperaturaIntegrationService temperaturaIntegrationService) {
        this.pedidoRepository = pedidoRepository;
        this.temperaturaIntegrationService = temperaturaIntegrationService;
    }

    public String efetuarPedido(Long id, PedidoRequestDTO pedidoRequestDTO) {

        Pedido pedido = PedidoConverter.converterDTOParaEntidade(pedidoRequestDTO);
        pedidoRepository.save(pedido);

        String message = "Valor total do pedido = " + calculoDoValorTotalDoPedido(pedidoRequestDTO);
        return message;
    }


    public BigDecimal calculoDoValorTotalDoPedido(PedidoRequestDTO pedidoRequestDTO) {

        List<Cerveja> cervejasPedidas = pedidoRequestDTO.getCervejas();
        BigDecimal valorTotalCervejas = BigDecimal.valueOf(0);
        BigDecimal somarQuantidades = BigDecimal.valueOf(0);

        for (int i = 0; i <= cervejasPedidas.size(); i++) {
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

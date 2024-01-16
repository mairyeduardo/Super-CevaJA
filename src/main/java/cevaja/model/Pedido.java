package cevaja.model;

import cevaja.integration.response.TemperaturaResponse;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "Pedido_Tb")
@Data
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private Usuario usuario;
    @ManyToMany
    @JoinTable(
            name = "pedido_cerveja",
            joinColumns = @JoinColumn(name = "pedido_id"),
            inverseJoinColumns = @JoinColumn(name = "cerveja_id")
    )
    private List<Cerveja> cerveja;

    @Column
    private BigDecimal valorTotal;

    public void calculoDoValorTotalDoPedido(TemperaturaResponse temperaturaResponse) {

        var valorDescontoQNTItens = BigDecimal.valueOf(10);
        var valorDescontoGraus = BigDecimal.valueOf(15);

        List<Cerveja> cervejasPedidas = this.getCerveja();
        BigDecimal valorTotalCervejas = BigDecimal.valueOf(0);
        BigDecimal somarQuantidades = BigDecimal.valueOf(0);

        for (int i = 0; i < cervejasPedidas.size(); i++) {
            BigDecimal quantidade = cervejasPedidas.get(i).getQuantidade();
            somarQuantidades = somarQuantidades.add(quantidade);
            BigDecimal valor = cervejasPedidas.get(i).getValor();
            valorTotalCervejas = valorTotalCervejas.add(quantidade.multiply(valor));
        }

        BigDecimal porcentagemDescontoAtual = BigDecimal.valueOf(0);
        Integer quantidadeItensParaDesconto = 10;

        if (somarQuantidades.longValue() >= quantidadeItensParaDesconto) {
            porcentagemDescontoAtual = porcentagemDescontoAtual.add(valorDescontoQNTItens);
        }

        TemperaturaResponse temperaturaAtual = temperaturaResponse;
        Integer grausNecessariosParaDesconto = 22;

        if (temperaturaAtual.getCurrent().getTemp_c() <= grausNecessariosParaDesconto) {
            porcentagemDescontoAtual = porcentagemDescontoAtual.add(valorDescontoGraus);
        }

        BigDecimal valorDoDesconto = valorTotalCervejas.multiply(porcentagemDescontoAtual).divide(BigDecimal.valueOf(100));
        BigDecimal valorTotalPedidoComDesconto = valorTotalCervejas.subtract(valorDoDesconto);
        this.valorTotal = valorTotalPedidoComDesconto;
    }
}

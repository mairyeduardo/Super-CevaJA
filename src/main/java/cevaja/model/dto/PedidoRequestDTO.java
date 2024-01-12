package cevaja.model.dto;

import cevaja.model.Cerveja;

import lombok.Data;

import java.util.List;

@Data
public class PedidoRequestDTO {

    private String nome;
    private String email;
    private List<Cerveja> cervejas;
}

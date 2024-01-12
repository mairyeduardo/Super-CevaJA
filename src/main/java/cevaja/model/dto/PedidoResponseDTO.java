package cevaja.model.dto;

import cevaja.model.Cerveja;

import lombok.Data;

import java.util.List;

@Data
public class PedidoResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private Long idUsuario;
    private List<Cerveja> cervejas;
}

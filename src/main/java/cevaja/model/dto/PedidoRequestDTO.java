package cevaja.model.dto;

import cevaja.model.Cerveja;

import lombok.Data;

import java.util.List;

@Data
public class PedidoRequestDTO {

    private Long idUsuario;
    private List<PedidoCervejaInputDTO> cerveja;
}

package cevaja.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CervejaResponseDTO {

    private String tipo;
    private BigDecimal valor;

}

package cevaja.model.dto.converter;

import cevaja.model.Cerveja;
import cevaja.model.dto.CervejaRequestDTO;
import cevaja.model.dto.CervejaResponseDTO;

public class CervejaConverter {

    public static Cerveja converterDTOParaEntidade(CervejaRequestDTO cervejaRequestDTO) {
        Cerveja cervejaRequestEntity = new Cerveja();
        cervejaRequestEntity.setTipo(cervejaRequestDTO.getTipo());
        cervejaRequestEntity.setValor(cervejaRequestDTO.getValor());
        cervejaRequestEntity.setQuantidade(cervejaRequestDTO.getQuantidade());

        return cervejaRequestEntity;
    }

    public static CervejaResponseDTO converterEntidadeParaDTO(Cerveja cerveja) {
        CervejaResponseDTO cervejaResponseDTO = new CervejaResponseDTO();
        cervejaResponseDTO.setId(cerveja.getId());
        cervejaResponseDTO.setTipo(cerveja.getTipo());
        cervejaResponseDTO.setValor(cerveja.getValor());
        cervejaResponseDTO.setQuantidade(cerveja.getQuantidade());
        return cervejaResponseDTO;
    }
}

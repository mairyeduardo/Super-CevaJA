package cevaja.service;

import cevaja.model.Cerveja;
import cevaja.model.dto.CervejaRequestDTO;
import cevaja.model.dto.CervejaResponseDTO;
import cevaja.model.dto.converter.CervejaConverter;
import cevaja.repository.CervejaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CervejaService {

    private CervejaRepository cervejaRepository;

    public CervejaService(CervejaRepository cervejaRepository) {
        this.cervejaRepository = cervejaRepository;
    }

    public List<CervejaResponseDTO> buscarCervejas() {
        List<Cerveja> cervejas = cervejaRepository.findAll();
        List<CervejaResponseDTO> cervejasResponse = new ArrayList<>();

        for (Cerveja c: cervejas) {
            cervejasResponse.add(CervejaConverter.converterEntidadeParaDTO(c));
        }
        return cervejasResponse;
    }

    private Cerveja buscarCervejaPorTipo(String tipo) {
        return cervejaRepository.findByTipo(tipo);
    }

    public void adicionarCerveja(CervejaRequestDTO cervejaRequestDTO) {
        String verificarTipoDaCervejaASerAdicionada = cervejaRequestDTO.getTipo();
        Cerveja TipoCervejaExistenteSalva = buscarCervejaPorTipo(verificarTipoDaCervejaASerAdicionada);
        if (TipoCervejaExistenteSalva != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Não foi possivel adicionar cerveja, cerveja = " + verificarTipoDaCervejaASerAdicionada + " já cadastrada");
        } else {
            Cerveja cervejaEntity = CervejaConverter.converterDTOParaEntidade(cervejaRequestDTO);
            cervejaRepository.save(cervejaEntity);
        }
    }

    public CervejaResponseDTO removerPorTipo(String tipo) {

        Cerveja cervejaASerRemovida = cervejaRepository.findByTipo(tipo);

        if (cervejaASerRemovida == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Não é possivel remover, cerveja de tipo: " +tipo+ " não cadastrada no banco de dados.");
        } else {
            CervejaResponseDTO cervejaResponseDTO = CervejaConverter.converterEntidadeParaDTO(cervejaASerRemovida);
            cervejaRepository.delete(cervejaASerRemovida);
            return cervejaResponseDTO;
        }
    }

    public CervejaResponseDTO alterarPorTipo (String tipo, CervejaRequestDTO cervejaRequestDTO) {
      Cerveja CervejaEncontada = cervejaRepository.findByTipo(tipo);

      BigDecimal novoValorCerveja = cervejaRequestDTO.getValor();

        if (CervejaEncontada == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Não é possivel alterar dados de uma cerveja inexistente.");

        } else {
            CervejaEncontada.setValor(novoValorCerveja);
        }
        cervejaRepository.save(CervejaEncontada);
        CervejaResponseDTO cervejaDTO = CervejaConverter.converterEntidadeParaDTO(CervejaEncontada);
        return cervejaDTO;
    }

}

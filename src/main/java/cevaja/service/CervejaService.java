package cevaja.service;

import cevaja.model.Cerveja;
import cevaja.model.dto.CervejaRequestDTO;
import cevaja.model.dto.CervejaResponseDTO;
import cevaja.model.dto.converter.CervejaConverter;
import cevaja.repository.CervejaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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





}

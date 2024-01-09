package cevaja.service;

import cevaja.model.Cerveja;
import cevaja.model.dto.CervejaResponseDTO;
import cevaja.model.dto.converter.CervejaConverter;
import cevaja.repository.CervejaRepository;
import org.springframework.stereotype.Service;

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





}

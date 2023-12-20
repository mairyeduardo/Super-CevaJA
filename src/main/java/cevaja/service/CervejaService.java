package cevaja.service;

import cevaja.repository.CervejaRepository;
import org.springframework.stereotype.Service;

@Service
public class CervejaService {

    private CervejaRepository cervejaRepository;

    public CervejaService(CervejaRepository cervejaRepository) {
        this.cervejaRepository = cervejaRepository;
    }
}

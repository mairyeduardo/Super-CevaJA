package cevaja.controller;

import cevaja.model.Cerveja;
import cevaja.model.dto.CervejaResponseDTO;
import cevaja.service.CervejaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/cervejas/tipos")
public class CervejaController {

    private CervejaService cervejaService;

    public CervejaController(CervejaService cervejaService) {
        this.cervejaService = cervejaService;
    }

    @GetMapping("/listarCervejas")
    public ResponseEntity<List<CervejaResponseDTO>> listarCervejas() {
        return ResponseEntity.ok(this.cervejaService.buscarCervejas());
    }
}

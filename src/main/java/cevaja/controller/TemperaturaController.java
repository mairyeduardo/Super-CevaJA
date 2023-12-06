package cevaja.controller;

import cevaja.integration.response.TemperaturaResponse;
import cevaja.integration.service.TemperaturaIntegrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/temperatura")
public class TemperaturaController {

    private TemperaturaIntegrationService temperaturaIntegrationService;


     public TemperaturaController( TemperaturaIntegrationService temperaturaIntegrationService) {
        this.temperaturaIntegrationService = temperaturaIntegrationService;
    }

    @GetMapping()
    public ResponseEntity<TemperaturaResponse> buscarTemperatura(){
         TemperaturaResponse buscarTemperaturaServicoExterno = this.temperaturaIntegrationService.buscarTemperaturaAtual();
         return ResponseEntity.ok(buscarTemperaturaServicoExterno);
    }

}

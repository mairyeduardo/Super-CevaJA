package cevaja.integration.service;

import cevaja.integration.response.TemperaturaResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TemperaturaIntegrationService {

    private final RestTemplate restTemplate;

    @Value("${weather-external-api}")
    private String uri;

    public TemperaturaIntegrationService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public TemperaturaResponse buscarTemperaturaAtual(){
        String urlCompleta = this.uri;
        TemperaturaResponse temperaturaExterna =  this.restTemplate.getForObject(urlCompleta, TemperaturaResponse.class);
        return temperaturaExterna;
    }

}

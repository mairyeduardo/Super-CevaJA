package cevaja.controller;

import cevaja.model.dto.CervejaRequestDTO;
import cevaja.model.dto.CervejaResponseDTO;
import cevaja.service.CervejaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/adicionar")
    public ResponseEntity<Void> adicionarCerveja(@RequestBody CervejaRequestDTO cervejaRequestDTO) {
        cervejaService.adicionarCerveja(cervejaRequestDTO);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @DeleteMapping("/remover/{tipo}")
    public ResponseEntity<CervejaResponseDTO> removerCervejaPorTipo(@PathVariable("tipo") String tipo) {
        CervejaResponseDTO cervejaRemovida = cervejaService.removerPorTipo(tipo);
        return ResponseEntity.ok(cervejaRemovida);
    }

    @PutMapping("/alterarValor/{tipo}")
    public ResponseEntity<CervejaResponseDTO> alterarValorCervejaPassandoTipo(@PathVariable("tipo") String tipo, @RequestBody CervejaRequestDTO cervejaRequestDTO) {
        CervejaResponseDTO cervejaAserAlterada = cervejaService.alterarPorTipo(tipo, cervejaRequestDTO );
        return ResponseEntity.ok(cervejaAserAlterada);
    }

}

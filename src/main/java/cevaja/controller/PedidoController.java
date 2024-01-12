package cevaja.controller;

import cevaja.model.dto.PedidoRequestDTO;
import cevaja.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/pedidos")
public class PedidoController {

    private PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> fazerPedido(@PathVariable("id") Long id, @RequestBody PedidoRequestDTO pedidoRequestDTO) {
        pedidoService.efetuarPedido(id, pedidoRequestDTO);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }


}

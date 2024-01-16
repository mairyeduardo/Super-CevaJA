package cevaja.controller;

import cevaja.model.dto.PedidoRequestDTO;
import cevaja.model.dto.PedidoResponseDTO;
import cevaja.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/pedidos")
public class PedidoController {

    private PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> fazerPedido(@RequestBody PedidoRequestDTO pedidoRequestDTO) {
        pedidoService.efetuarPedido(pedidoRequestDTO);
        return ResponseEntity.ok(pedidoService.efetuarPedido(pedidoRequestDTO));
    }

    @GetMapping("/listarpedidos")
    public ResponseEntity<List<PedidoResponseDTO>> listarPedidos() {
        return ResponseEntity.ok(this.pedidoService.buscarTodosPedidos());
    }
}

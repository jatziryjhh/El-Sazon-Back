package mx.edu.utez.El_Sazon_Back.controller.pedido;

import jakarta.validation.Valid;
import mx.edu.utez.El_Sazon_Back.config.ApiResponse;
import mx.edu.utez.El_Sazon_Back.controller.inventario.InventarioDto;
import mx.edu.utez.El_Sazon_Back.controller.producto.ProductoDto;
import mx.edu.utez.El_Sazon_Back.service.pedido.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/pedido")

public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse> getAll(){
        return pedidoService.getAll();
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse> save(@Valid @RequestBody PedidoDto pedidoDto){
        return pedidoService.register(pedidoDto.toEntity());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> findById(@PathVariable Long id){
        return pedidoService.findById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable Long id) {
        return pedidoService.delete(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @Valid @RequestBody PedidoDto pedidoDto){
        ResponseEntity<ApiResponse> updateResponse = pedidoService.update(id, pedidoDto.toEntity());
        return updateResponse;
    }
}

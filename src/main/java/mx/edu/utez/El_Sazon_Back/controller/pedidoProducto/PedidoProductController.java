package mx.edu.utez.El_Sazon_Back.controller.pedidoProducto;

import jakarta.validation.Valid;
import mx.edu.utez.El_Sazon_Back.config.ApiResponse;
import mx.edu.utez.El_Sazon_Back.controller.inventario.InventarioDto;
import mx.edu.utez.El_Sazon_Back.service.pedidoProducto.PedidoProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/pedidoproducto")

public class PedidoProductController {

    private final PedidoProductoService pedidoProductoService;

    public PedidoProductController(PedidoProductoService pedidoProductoService) {
        this.pedidoProductoService = pedidoProductoService;
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse> getAll(){
        return pedidoProductoService.getAll();
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse> save(@Valid @RequestBody PedidoProductoDto pedidoProductoDto){
        return pedidoProductoService.register(pedidoProductoDto.toEntity());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> findById(@PathVariable Long id){
        return pedidoProductoService.findById(id);
    }


}

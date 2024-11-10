package mx.edu.utez.El_Sazon_Back.controller.inventario;

import jakarta.validation.Valid;
import mx.edu.utez.El_Sazon_Back.config.ApiResponse;
import mx.edu.utez.El_Sazon_Back.model.inventario.Inventario;
import mx.edu.utez.El_Sazon_Back.service.inventario.InventarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/inventario")

public class InventarioController {

    public final InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse> getAll(){
        return inventarioService.getAll();
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse> save(@Valid @RequestBody InventarioDto inventarioDto){
        return inventarioService.registerAll(inventarioDto.toEntity());
    }

    @PostMapping("/producto")
    public ResponseEntity<ApiResponse> saveProduct(@Valid @RequestBody InventarioDto inventarioDto){
        return inventarioService.registerProduct(inventarioDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> findById(@PathVariable Long id){
        return inventarioService.findById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @Valid @RequestBody InventarioDto inventarioDto){
        ResponseEntity<ApiResponse> updateResponse = inventarioService.update(id, inventarioDto.toEntity());
        return updateResponse;
    }
}

package mx.edu.utez.El_Sazon_Back.service.inventario;

import mx.edu.utez.El_Sazon_Back.config.ApiResponse;
import mx.edu.utez.El_Sazon_Back.model.inventario.Inventario;
import mx.edu.utez.El_Sazon_Back.model.inventario.InventarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.SQLException;
import java.util.Optional;

@Service
@Transactional

public class InventarioService {
    private final InventarioRepository inventarioRepository;

    public InventarioService(InventarioRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse> getAll(){
        return new ResponseEntity<>(new ApiResponse(inventarioRepository.findAll(),
                HttpStatus.OK), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> register(Inventario inventario){
        inventario = inventarioRepository.saveAndFlush(inventario);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, false, "Registro de inventario exitoso"), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse> findById(@PathVariable Long id){
        Optional<Inventario> findById = inventarioRepository.findById(id);
        if (findById.isEmpty())
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "No se encontró el Id"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(new ApiResponse(inventarioRepository.findById(id),HttpStatus.OK), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public  ResponseEntity<ApiResponse> update(Long id, Inventario updateInventario){
        Optional<Inventario> optionalInventario = inventarioRepository.findById(id);
        if (optionalInventario.isPresent()){
            Inventario inventario = optionalInventario.get();
            inventario.setCantidad_inventario(updateInventario.getCantidad_inventario());
            return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, false, "Cantidad de productos actualizada"), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND, true, "No se encontró el Id"), HttpStatus.NOT_FOUND);
        }
    }
}

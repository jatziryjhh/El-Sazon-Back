package mx.edu.utez.El_Sazon_Back.service.venta;

import mx.edu.utez.El_Sazon_Back.config.ApiResponse;
import mx.edu.utez.El_Sazon_Back.model.venta.Venta;
import mx.edu.utez.El_Sazon_Back.model.venta.VentaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.SQLException;
import java.util.Optional;

@Service
@Transactional

public class VentaService {
    private final VentaRepository ventaRepository;

    public VentaService(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse> getAll(){
        return new ResponseEntity<>(new ApiResponse(ventaRepository.findAll(),
                HttpStatus.OK), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> register(Venta venta){
        venta = ventaRepository.saveAndFlush(venta);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, false, "Venta registrada"), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse> findById(@PathVariable Long id){
        Optional<Venta> findById = ventaRepository.findById(id);
        if (findById.isEmpty())
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "No se encontró el Id"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(new ApiResponse(ventaRepository.findById(id),HttpStatus.OK), HttpStatus.OK);
    }
}

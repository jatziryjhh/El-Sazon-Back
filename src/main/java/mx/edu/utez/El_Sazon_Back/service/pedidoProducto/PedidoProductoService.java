package mx.edu.utez.El_Sazon_Back.service.pedidoProducto;

import mx.edu.utez.El_Sazon_Back.config.ApiResponse;
import mx.edu.utez.El_Sazon_Back.model.pedidoProducto.PedidoProductRepository;
import mx.edu.utez.El_Sazon_Back.model.pedidoProducto.PedidoProducto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.SQLException;
import java.util.Optional;

@Service
@Transactional

public class PedidoProductoService {
    private final PedidoProductRepository pedidoProductRepository;

    public PedidoProductoService(PedidoProductRepository pedidoProductRepository) {
        this.pedidoProductRepository = pedidoProductRepository;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse> getAll(){
        return new ResponseEntity<>(new ApiResponse(pedidoProductRepository.findAll(), HttpStatus.OK), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> register(PedidoProducto pedidoProducto){
        pedidoProducto = pedidoProductRepository.saveAndFlush(pedidoProducto);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, false, "Pedido registrado"), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse> findById(@PathVariable Long id){
        Optional<PedidoProducto> findById = pedidoProductRepository.findById(id);
        if (findById.isEmpty())
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "No se encontró el id"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(new ApiResponse(pedidoProductRepository.findById(id), HttpStatus.OK), HttpStatus.OK);
    }
}

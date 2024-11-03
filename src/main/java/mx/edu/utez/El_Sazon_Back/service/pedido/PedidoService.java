package mx.edu.utez.El_Sazon_Back.service.pedido;

import mx.edu.utez.El_Sazon_Back.config.ApiResponse;
import mx.edu.utez.El_Sazon_Back.model.pedido.Pedido;
import mx.edu.utez.El_Sazon_Back.model.pedido.PedidoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.SQLException;
import java.util.Optional;

@Service
@Transactional

public class PedidoService {
    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse> getAll(){
        return new ResponseEntity<>(new ApiResponse(pedidoRepository.findAll(),
                HttpStatus.OK), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> register(Pedido pedido){
        pedido = pedidoRepository.saveAndFlush(pedido);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, false, "Pedido registrado"), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id){
        Optional<Pedido> findById = pedidoRepository.findById(id);
        if (findById.isEmpty())
            return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND, true,
                    "No se encontró la habitacion"), HttpStatus.NOT_FOUND);
        pedidoRepository.deleteById(id);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, false, "Pedido eliminado"), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse> findById(@PathVariable Long id){
        Optional<Pedido> findById = pedidoRepository.findById(id);
        if (findById.isEmpty())
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "No se encontró el Id"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(new ApiResponse(pedidoRepository.findById(id),HttpStatus.OK), HttpStatus.OK);
    }
}

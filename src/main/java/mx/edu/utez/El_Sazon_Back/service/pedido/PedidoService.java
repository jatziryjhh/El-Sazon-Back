package mx.edu.utez.El_Sazon_Back.service.pedido;

import mx.edu.utez.El_Sazon_Back.config.ApiResponse;
import mx.edu.utez.El_Sazon_Back.model.pedido.Pedido;
import mx.edu.utez.El_Sazon_Back.model.pedido.PedidoRepository;
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

public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final VentaRepository ventaRepository;

    public PedidoService(PedidoRepository pedidoRepository, VentaRepository ventaRepository) {
        this.pedidoRepository = pedidoRepository;
        this.ventaRepository = ventaRepository;
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
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(id);
        if (pedidoOptional.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND, true,
                    "No se encontró el pedido"), HttpStatus.NOT_FOUND);
        }

        Pedido pedido = pedidoOptional.get();

        // Aqui desvicunle el pedido de la relación con la venta
        if (pedido.getVenta() != null) {
            Venta venta = pedido.getVenta();
            venta.setPedido(null);
            ventaRepository.save(venta);
            pedido.setVenta(null);
        }

        // Desvincule los productos asociados
        if (pedido.getProductos() != null && !pedido.getProductos().isEmpty()) {
            pedido.getProductos().clear();
            pedidoRepository.save(pedido);
        }
        pedidoRepository.deleteById(id);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, false, "Pedido eliminado correctamente"), HttpStatus.OK);
    }


    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse> findById(@PathVariable Long id){
        Optional<Pedido> findById = pedidoRepository.findById(id);
        if (findById.isEmpty())
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "No se encontró el Id"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(new ApiResponse(pedidoRepository.findById(id),HttpStatus.OK), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public  ResponseEntity<ApiResponse> update(Long id, Pedido updatePedido){
        Optional<Pedido> optionalPedido = pedidoRepository.findById(id);
        if (optionalPedido.isPresent()){
            Pedido pedido = optionalPedido.get();
            pedido.setStatus(updatePedido.getStatus());
            return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, false, "Pedido Actualizado"), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND, true, "No se encontró el producto"), HttpStatus.NOT_FOUND);
        }
    }
}

package mx.com.openwebinars.tienda.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import mx.com.openwebinars.tienda.dao.entity.LineaPedidoEntity;
import mx.com.openwebinars.tienda.dao.entity.PedidoEntity;

public interface LineaPedidoDao extends JpaRepository<LineaPedidoEntity, Long>, JpaSpecificationExecutor<LineaPedidoEntity>{

	List<LineaPedidoEntity> findByPedido(PedidoEntity pedido);

}

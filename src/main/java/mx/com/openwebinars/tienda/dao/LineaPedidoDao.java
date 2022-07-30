package mx.com.openwebinars.tienda.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import mx.com.openwebinars.tienda.dao.entity.LineaPedidoEntity;

public interface LineaPedidoDao extends JpaRepository<LineaPedidoEntity, Long>, JpaSpecificationExecutor<LineaPedidoEntity>{

}

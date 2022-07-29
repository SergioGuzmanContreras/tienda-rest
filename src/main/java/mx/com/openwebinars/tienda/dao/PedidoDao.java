package mx.com.openwebinars.tienda.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import mx.com.openwebinars.tienda.dao.entity.PedidoEntity;

public interface PedidoDao extends JpaRepository<PedidoEntity, Long>, JpaSpecificationExecutor<PedidoEntity>{

	Page<PedidoEntity> findByClienteContainsIgnoreCase(@Param("cliente") String cliente, Pageable pageable);

	
}

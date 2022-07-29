package mx.com.openwebinars.tienda.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import mx.com.openwebinars.tienda.dao.entity.PedidoEntity;

public interface PedidoService {

	PedidoEntity findById(Long id);
	List<PedidoEntity> findAll();
	Page<PedidoEntity> findAll(Pageable pageable);
	Page<PedidoEntity> findByClienteContainsIgnoreCase(String cliente, Pageable pageable);
	PedidoEntity save(PedidoEntity request);
	PedidoEntity update(PedidoEntity request);	
	void delete(Long request);
	Page<PedidoEntity> findByArgs(final Optional<String> cliente , final Optional<BigDecimal> precio, Pageable pageable);

}

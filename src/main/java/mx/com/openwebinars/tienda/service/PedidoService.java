package mx.com.openwebinars.tienda.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import mx.com.openwebinars.tienda.dao.entity.PedidoEntity;

public interface PedidoService {

	PedidoEntity findById(Long id);
	Page<PedidoEntity> findAll(Pageable pageable);
	Page<PedidoEntity> findAll(Optional<String> cliente, Pageable pageable);
	PedidoEntity save(PedidoEntity request);
	PedidoEntity update(PedidoEntity request);	
	void delete(Long request);

}

package mx.com.openwebinars.tienda.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import mx.com.openwebinars.tienda.dao.entity.ProductoEntity;

public interface ProductoService {

	ProductoEntity findById(Long id);
	Page<ProductoEntity> findByArgs(final Optional<String> producto, final Optional<BigDecimal> precio, Pageable pageable);

	ProductoEntity save(ProductoEntity request);
	ProductoEntity update(ProductoEntity request);	
	void delete(Long request);

}

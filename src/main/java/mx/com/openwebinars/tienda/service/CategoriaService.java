package mx.com.openwebinars.tienda.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import mx.com.openwebinars.tienda.dao.entity.CategoriaEntity;

public interface CategoriaService {

	CategoriaEntity findById(Long id);
	Page<CategoriaEntity> findAll(Pageable pageable);
	CategoriaEntity save(CategoriaEntity request);
	CategoriaEntity update(CategoriaEntity request);	
	void delete(Long request);

	
}

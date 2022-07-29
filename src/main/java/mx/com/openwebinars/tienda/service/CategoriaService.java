package mx.com.openwebinars.tienda.service;

import java.util.List;

import mx.com.openwebinars.tienda.dao.entity.CategoriaEntity;


public interface CategoriaService {

	CategoriaEntity findById(Long id);
	List<CategoriaEntity> findAll();

	CategoriaEntity save(CategoriaEntity request);
	CategoriaEntity update(CategoriaEntity request);	
	void delete(Long request);

	
}

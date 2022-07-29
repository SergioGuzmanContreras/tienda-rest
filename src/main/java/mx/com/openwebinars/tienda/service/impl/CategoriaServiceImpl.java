package mx.com.openwebinars.tienda.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.openwebinars.tienda.dao.CategoriaDao;
import mx.com.openwebinars.tienda.dao.entity.CategoriaEntity;
import mx.com.openwebinars.tienda.service.CategoriaService;

@Service
public class CategoriaServiceImpl implements CategoriaService{

	@Autowired
	private CategoriaDao categoriaDao;
	
	@Override
	public List<CategoriaEntity> findAll() {
		return this.categoriaDao.findAll();
	}

	@Override
	public CategoriaEntity findById(Long id) {
		return this.categoriaDao.findById(id).orElse(null);
	}

	@Override
	public CategoriaEntity save(CategoriaEntity request) {
		return this.categoriaDao.save(request);
	}

	@Override
	public CategoriaEntity update(CategoriaEntity request) {
		if(this.categoriaDao.existsById(request.getId()))
			return this.categoriaDao.save(request);
		return null;
	}

	@Override
	public void delete(Long request) {
		var producto = this.findById(request);
		if(producto != null)
			this.categoriaDao.delete(producto);
	}
	
}

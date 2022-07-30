package mx.com.openwebinars.tienda.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import mx.com.openwebinars.tienda.dao.CategoriaDao;
import mx.com.openwebinars.tienda.dao.entity.CategoriaEntity;
import mx.com.openwebinars.tienda.service.CategoriaService;
import mx.com.openwebinars.tienda.utils.exceptions.CategoriaNotFoundException;

@Service
public class CategoriaServiceImpl implements CategoriaService{

	@Autowired
	private CategoriaDao categoriaDao;
	
	@Override
	public CategoriaEntity findById(Long id) {
		return this.categoriaDao.findById(id).orElseThrow(() -> new CategoriaNotFoundException(id));
	}

	@Override
	public CategoriaEntity save(CategoriaEntity request) {
		var data = this.categoriaDao.findByCategoria(request.getCategoria());
		if(data == null)
			return this.categoriaDao.save(request);
		throw new CategoriaNotFoundException(data);
	}

	@Override
	public CategoriaEntity update(CategoriaEntity request) {
		if(this.categoriaDao.existsById(request.getId())) {
			var data = this.categoriaDao.findByCategoria(request.getCategoria());
			if(data == null)
				return this.categoriaDao.save(request);
			throw new CategoriaNotFoundException(data);
		}
		throw new CategoriaNotFoundException(request.getId());
	}

	@Override
	public void delete(Long request) {
		var producto = this.findById(request);
		if(producto != null)
			this.categoriaDao.delete(producto);
		else 
			throw new CategoriaNotFoundException(request);
	}

	@Override
	public Page<CategoriaEntity> findAll(Pageable pageable) {
		return this.categoriaDao.findAll(pageable);
	}
	
}

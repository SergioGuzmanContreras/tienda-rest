package mx.com.openwebinars.tienda.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import mx.com.openwebinars.tienda.dao.ProductoDao;
import mx.com.openwebinars.tienda.dao.entity.ProductoEntity;
import mx.com.openwebinars.tienda.service.ProductoService;
import mx.com.openwebinars.tienda.utils.exceptions.ProductoNotFoundException;


@Service
public class ProductoServiceImpl implements ProductoService {

	@Autowired
	private ProductoDao productoDao;
	
	@Override
	public List<ProductoEntity> findAll() {
		return this.productoDao.findAll();
	}

	@Override
	public ProductoEntity findById(Long id) {
		return this.productoDao.findById(id).orElseThrow(() ->new ProductoNotFoundException(id));
	}

	@Override
	public ProductoEntity save(ProductoEntity request) {
		var response = this.productoDao.save(request);
		if(response == null)
			new ProductoNotFoundException(null);
		return response;
	}

	@Override
	public ProductoEntity update(ProductoEntity request) {
		if(this.productoDao.existsById(request.getId()))
			return this.productoDao.save(request);
		return null;
	}

	@Override
	public void delete(Long request) {
		var producto = this.findById(request);
		if(producto != null)
			this.productoDao.delete(producto);
		throw new ProductoNotFoundException(request);
	}

	@Override
	public Page<ProductoEntity> findAll(Pageable pageable) {
		return this.productoDao.findAll(pageable);
	}

	@Override
	public Page<ProductoEntity> findByProductoContainsIgnoreCase(String producto, Pageable pageable) {
		return this.productoDao.findByProductoContainsIgnoreCase(producto, pageable);
	}

	@Override
	public Page<ProductoEntity> findByArgs(Optional<String> producto, Optional<BigDecimal> precio, Pageable pageable) {
		var specNombreProducto = new Specification<ProductoEntity>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<ProductoEntity> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				if(producto.isPresent())
					return criteriaBuilder.like(criteriaBuilder.lower(root.get("producto")),"%" + producto.get() + "%");
				else
					return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
			}
		};
		var precioMenorQue = new Specification<ProductoEntity>() {
			private static final long serialVersionUID = -6425933618995534912L;
			@Override
			public Predicate toPredicate(Root<ProductoEntity> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				if(precio.isPresent())
					return criteriaBuilder.lessThanOrEqualTo(root.get("precio"), precio.get());
				else
					return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
			}
		
		};
		var specFinal = specNombreProducto.and(precioMenorQue); 
		return this.productoDao.findAll(specFinal, pageable);
	}
	
	
}

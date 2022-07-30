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

import mx.com.openwebinars.tienda.dao.PedidoDao;
import mx.com.openwebinars.tienda.dao.entity.PedidoEntity;
import mx.com.openwebinars.tienda.service.PedidoService;
import mx.com.openwebinars.tienda.utils.exceptions.ProductoNotFoundException;

@Service
public class PedidoServiceImpl implements PedidoService {

	@Autowired
	private PedidoDao pedidos;
	
	@Override
	public PedidoEntity findById(Long id) {
		return this.pedidos.findById(id).orElse(null);
	}

	@Override
	public List<PedidoEntity> findAll() {
		return this.pedidos.findAll();
	}

	@Override
	public Page<PedidoEntity> findAll(Pageable pageable) {		
		var data = this.pedidos.findAll(pageable);
		if(data.isEmpty())
			return Page.empty();
		return data;
	}

	@Override
	public PedidoEntity save(PedidoEntity request) {
		var response = this.pedidos.save(request);
		if(response == null)
			new ProductoNotFoundException(null);
		return response;
	}

	@Override
	public PedidoEntity update(PedidoEntity request) {
		if(this.pedidos.existsById(request.getId()))
			return this.pedidos.save(request);
		return null;
	}

	@Override
	public void delete(Long request) {
		var pedido = this.findById(request);
		if(pedido != null)
			this.delete(request);
	}

	@Override
	public Page<PedidoEntity> findByArgs(Optional<String> cliente, Optional<BigDecimal> precio, Pageable pageable) {
		var specCliente = new Specification<PedidoEntity>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<PedidoEntity> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				if(cliente.isPresent())
					return criteriaBuilder.like(criteriaBuilder.lower(root.get("cliente")),"%" + cliente.get() + "%");
				else
					return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
			}
		};
		return this.pedidos.findAll(specCliente, pageable);
	}

	@Override
	public Page<PedidoEntity> findByClienteContainsIgnoreCase(String cliente, Pageable pageable) {
		return this.pedidos.findByClienteContainsIgnoreCase(cliente, pageable);
	}
	

}

package mx.com.openwebinars.tienda.service.impl;

import java.util.ArrayList;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.com.openwebinars.tienda.dao.LineaPedidoDao;
import mx.com.openwebinars.tienda.dao.PedidoDao;
import mx.com.openwebinars.tienda.dao.entity.LineaPedidoEntity;
import mx.com.openwebinars.tienda.dao.entity.PedidoEntity;
import mx.com.openwebinars.tienda.service.PedidoService;
import mx.com.openwebinars.tienda.utils.exceptions.ProductoNotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

	private final PedidoDao pedidosDao;
	private final LineaPedidoDao lineasDao;
	
	@Override
	public PedidoEntity findById(Long id) {
		return this.pedidosDao.findById(id).orElse(null);
	}

	@Override
	public PedidoEntity save(PedidoEntity request) {
		var lineas = new ArrayList<LineaPedidoEntity>();
		var response = this.pedidosDao.save(request);
		if(response == null)
			new ProductoNotFoundException(null);
		else {
			if(!request.getLineas().isEmpty()) {
				for(LineaPedidoEntity linea : response.getLineas()) {
					linea.setPedido(response);
					log.info("Linea {}", linea.getPedido());
					var save = this.lineasDao.save(linea);
					if(save != null)
						lineas.add(save);
					else 
						log.error("Error al guardar pedido '' {} '' linea {}", response.getId(), linea);
				}
			}
		}
		response.setLineas(lineas);
		return response;
	}
	
	@Override
	public PedidoEntity update(PedidoEntity request) {
		if(this.pedidosDao.existsById(request.getId()))
			return this.pedidosDao.save(request);
		return null;
	}

	@Override
	public void delete(Long request) {
		var pedido = this.findById(request);
		if(pedido != null)
			this.delete(request);
	}

	@Override
	public Page<PedidoEntity> findAll(Optional<String> cliente, Pageable pageable) {		
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
		return this.pedidosDao.findAll(specCliente, pageable); 
	}

	@Override
	public Page<PedidoEntity> findAll(Pageable pageable) {
		return this.pedidosDao.findAll(pageable);
	}
	

}

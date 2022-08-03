package mx.com.openwebinars.tienda.component;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.com.openwebinars.tienda.dao.LineaPedidoDao;
import mx.com.openwebinars.tienda.dao.entity.LineaPedidoEntity;
import mx.com.openwebinars.tienda.dao.entity.PedidoEntity;
import mx.com.openwebinars.tienda.dao.entity.ProductoEntity;
import mx.com.openwebinars.tienda.models.LineaPedido;
import mx.com.openwebinars.tienda.models.Pedido;

@Component
public class PedidoConverter {

	@Autowired
	private LineaPedidoDao lineaDao;
	
	public PedidoEntity convertTo(Pedido pedido) {
		var lineas = new ArrayList<LineaPedidoEntity>();
		if(!pedido.getLineas().isEmpty())
			pedido.getLineas().forEach(w -> lineas.add(this.convertTo(w)));
		return PedidoEntity.builder()
				.id(pedido.getId())
				.cliente(pedido.getCliente())
				.fecha(new Date())
				.lineas(lineas)
				.build();	
	}
	
	private LineaPedidoEntity convertTo(LineaPedido linea) {
		return LineaPedidoEntity.builder()
				.id(linea.getId())
				.producto(ProductoEntity.builder()
						.id(linea.getIdProducto())
					.build())
				.precio(linea.getPrecio())
				.cantidad(linea.getCantidad())
				.build();
	}

	public Pedido convertTo(PedidoEntity pedido) {
		var lineasResponse = new ArrayList<LineaPedido>();
		var lineas = this.lineaDao.findByPedido(pedido);
		if(!lineas.isEmpty())
			lineas.forEach(w -> lineasResponse.add(this.convertTo(w)));
		var response = Pedido.builder()
				.id(pedido.getId())
				.cliente(pedido.getCliente())
				.fecha(new Date())
				.lineas(lineasResponse)
				.build();	
		response.setTotal(response.precioTotal());
		return response;
	}

	private LineaPedido convertTo(LineaPedidoEntity linea) {
		var response = LineaPedido.builder()
				.id(linea.getId())
				.idProducto(linea.getProducto().getId())
				.precio(linea.getPrecio())
				.cantidad(linea.getCantidad())
				.build();
		response.setSubTotal(response.precioSubTotal());
		return response;
	}

}

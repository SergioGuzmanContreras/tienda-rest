package mx.com.openwebinars.tienda.component;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.com.openwebinars.tienda.dao.entity.ProductoEntity;
import mx.com.openwebinars.tienda.models.Producto;

@Component
public class ProductoConverter {

	@Autowired
	private ModelMapper modelMapper;

	public Producto converterTo(ProductoEntity producto) {
		return modelMapper.map(producto, Producto.class);
	}

	public ProductoEntity converterTo(Producto producto) {
		return modelMapper.map(producto, ProductoEntity.class);
	}

	public Producto converterLoombok(ProductoEntity producto) {
		return Producto.builder()
				.id(producto.getId())
				.producto(producto.getProducto())
//				.precio(producto.getPrecio())
				.imagen(producto.getImagen())
				.categoriaCategoria(producto.getCategoria().getCategoria())
				.build();
	}
}

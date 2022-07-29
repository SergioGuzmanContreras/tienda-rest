package mx.com.openwebinars.tienda.models;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.com.openwebinars.tienda.views.ProductoViews;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

	@JsonView(ProductoViews.Dto.class)
	private Long id;
	@JsonView(ProductoViews.Dto.class)
	private String producto;
	@JsonView(ProductoViews.DtoPrecio.class)
	private BigDecimal precio;
	@JsonView(ProductoViews.Dto.class)
	private String imagen;
	@JsonView(ProductoViews.Dto.class)
	private String categoriaCategoria;

}
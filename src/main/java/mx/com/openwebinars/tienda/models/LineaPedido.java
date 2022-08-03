package mx.com.openwebinars.tienda.models;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LineaPedido {

	private Long id;
	private Long idProducto;
	private BigDecimal precio;
	private Integer cantidad;
	private BigDecimal subTotal;
	
	public BigDecimal precioSubTotal() {
		return new BigDecimal(cantidad * precio.doubleValue());
	}
	
}

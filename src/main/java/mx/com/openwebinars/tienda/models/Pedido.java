package mx.com.openwebinars.tienda.models;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

	private Long id;
	private String cliente;
	private Date fecha;
	private BigDecimal total;
	private List<LineaPedido> lineas;

	
	public BigDecimal precioTotal() {
		return new BigDecimal(this.lineas.stream().mapToDouble(d -> d.getSubTotal().doubleValue()).sum());
	}
	
}

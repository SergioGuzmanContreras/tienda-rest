package mx.com.openwebinars.tienda.models;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoCreate {

	private String producto;
	private BigDecimal precio;
	private Long categoriaId;
}

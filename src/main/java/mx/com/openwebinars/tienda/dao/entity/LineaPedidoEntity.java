package mx.com.openwebinars.tienda.dao.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lineaPedido")
public class LineaPedidoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idLineaPedido")
	private Long id;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idProducto", referencedColumnName = "idProducto", nullable = false, foreignKey=@ForeignKey(name = "fk_pedidos_productos"))
	private ProductoEntity producto;
	
	@Column(name = "precio", nullable= false, precision=7, scale=2) 
	@Digits(integer=9, fraction=2)
	private BigDecimal precio;
	
	@Column(name = "cantidad", nullable= false) 
	private Integer cantidad;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idPedido", referencedColumnName = "idPedido", nullable = false, foreignKey=@ForeignKey(name = "fk_pedidos_pedido"))
	private PedidoEntity pedido;
	
}

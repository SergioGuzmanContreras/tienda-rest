package mx.com.openwebinars.tienda.dao.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "productos")
public class ProductoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idProducto")
	private Long id;
	
	@Column(name = "nombre", nullable = false, length = 100)
	private String producto;

	@Column(name = "precion", nullable= false, precision=7, scale=2) 
	@Digits(integer=9, fraction=2)
	private BigDecimal precio;

	@Column(name = "imagen", nullable = true, length = 100)
	private String imagen;
	
	@ManyToOne
	@JoinColumn(name = "idCategoria", referencedColumnName = "idCategoria", nullable = false, foreignKey=@ForeignKey(name = "fk_categorias_productos"))
	private CategoriaEntity categoria;
	
}

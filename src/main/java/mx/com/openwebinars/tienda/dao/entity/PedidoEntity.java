package mx.com.openwebinars.tienda.dao.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pedidos")
@EntityListeners(AuditingEntityListener.class)
public class PedidoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idPedido")
	private Long id;
	
	@Column(name = "cliente", nullable = false, length = 50)
	private String cliente;

	@Column(name = "fecha")
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date fecha;

	@OneToMany(fetch = FetchType.EAGER)

	@JoinTable(name = "lineaPedido",
			joinColumns = @JoinColumn(name = "idPedido"))
	private List<LineaPedidoEntity> lineas;


}

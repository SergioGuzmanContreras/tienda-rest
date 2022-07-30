package mx.com.openwebinars.tienda.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Categoria implements Serializable{

	private static final long serialVersionUID = 4545610345816006094L;

	private Long id;
	private String categoria;
}

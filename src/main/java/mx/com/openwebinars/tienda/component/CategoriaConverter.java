package mx.com.openwebinars.tienda.component;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.com.openwebinars.tienda.dao.entity.CategoriaEntity;
import mx.com.openwebinars.tienda.models.Categoria;


@Component
public class CategoriaConverter {

	@Autowired
	private ModelMapper modelMapper;

	public Categoria converterTo(CategoriaEntity categoria) {
		return modelMapper.map(categoria, Categoria.class);
	}

	public CategoriaEntity converterTo(Categoria categoria) {
		return modelMapper.map(categoria, CategoriaEntity.class);
	}

	public Categoria converterToObject(CategoriaEntity categoria) {
		return Categoria.builder()
				.id(categoria.getId())
				.categoria(categoria.getCategoria())
				.build();
	}

	public CategoriaEntity converterToEntity(Categoria categoria) {
		return CategoriaEntity.builder()
				.id(categoria.getId())
				.categoria(categoria.getCategoria())
			.build();
	}

}

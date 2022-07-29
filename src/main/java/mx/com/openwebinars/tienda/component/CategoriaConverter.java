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

	public Categoria converterTo(CategoriaEntity categoriaEntity) {
		return modelMapper.map(categoriaEntity, Categoria.class);
	}
}

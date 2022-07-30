package mx.com.openwebinars.tienda.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.com.openwebinars.tienda.dao.entity.CategoriaEntity;


public interface CategoriaDao extends JpaRepository<CategoriaEntity, Long>{

	CategoriaEntity findByCategoria(String categoria);

}

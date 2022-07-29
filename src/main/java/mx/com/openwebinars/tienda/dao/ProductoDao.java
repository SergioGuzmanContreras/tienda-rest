package mx.com.openwebinars.tienda.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import mx.com.openwebinars.tienda.dao.entity.ProductoEntity;


public interface ProductoDao extends JpaRepository<ProductoEntity, Long>, JpaSpecificationExecutor<ProductoEntity>{

	Page<ProductoEntity> findByProductoContainsIgnoreCase(@Param("nombre") String producto, Pageable pageable);

}

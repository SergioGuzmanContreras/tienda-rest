package mx.com.openwebinars.tienda.controller;

import java.math.BigDecimal;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.com.openwebinars.tienda.component.PaginationLinksUtils;
import mx.com.openwebinars.tienda.component.ProductoConverter;
import mx.com.openwebinars.tienda.dao.entity.ProductoEntity;
import mx.com.openwebinars.tienda.models.Producto;
import mx.com.openwebinars.tienda.service.ProductoService;
import mx.com.openwebinars.tienda.service.StorageService;
import mx.com.openwebinars.tienda.utils.exceptions.SearchProductoNotResultException;
import mx.com.openwebinars.tienda.views.ProductoViews;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/productos")
public class ProductoRestController {

	private final ProductoService service;	
	private final ProductoConverter converter;
	private final StorageService storage;
	private final PaginationLinksUtils pagination;
	
	@GetMapping
	@ResponseBody
	@JsonView(ProductoViews.Dto.class)
	public ResponseEntity<Page<Producto>> buscar(
			@RequestParam("producto") Optional<String> producto,
			@RequestParam("precio") Optional<BigDecimal> precio,
			 @PageableDefault(size = 10, page= 0) Pageable pageable, 
			 HttpServletRequest request){
		var data = this.service.findByArgs(producto, precio, pageable);
		if(data.isEmpty())
			throw new SearchProductoNotResultException();
		else {
			var response = data.map(this.converter::converterTo);		
			var uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());
			return ResponseEntity.ok().header("link", this.pagination.createLinkHeader(response, uriBuilder)).body(response);
		}			
	}
	
	@ResponseBody
	@GetMapping("/{id}")
	public ProductoEntity obtenerUno(@PathVariable Long id) {
		return this.service.findById(id);
	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody
	public ResponseEntity<Producto> nuevoProducto(@RequestPart("producto") Producto request, @RequestPart("file") final MultipartFile file) {		
		log.info("request {}", request);
		var convert = this.converter.converterTo(request);
		if(!file.isEmpty()) {
			String imagen = this.storage.store(file);
			var url = MvcUriComponentsBuilder.fromMethodName(StorageController.class, "serveFile", imagen, null).build().toString();
			convert.setImagen(url);
		}
		var data = this.service.save(convert);
		var response = this.converter.converterTo(data);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@ResponseBody
	@PutMapping("/{id}")
	public ResponseEntity<Producto> editarProducto(@RequestBody Producto request) {
		log.info("request {}", request);
		var convert = this.converter.converterTo(request);
		var data = this.service.update(convert);
		if(data == null)
			return ResponseEntity.notFound().build();
		var response = this.converter.converterTo(data);
		return ResponseEntity.ok(response);
	}

	@ResponseBody
	@DeleteMapping("/{id}")
	public ResponseEntity<ProductoEntity> borrarProducto(@PathVariable Long id) {
		this.service.delete(id);
		return ResponseEntity.noContent().build();
	}

}

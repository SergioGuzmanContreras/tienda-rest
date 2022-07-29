package mx.com.openwebinars.tienda.controller;

import java.math.BigDecimal;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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

import mx.com.openwebinars.tienda.component.PaginationLinksUtils;
import mx.com.openwebinars.tienda.component.ProductoConverter;
import mx.com.openwebinars.tienda.dao.entity.ProductoEntity;
import mx.com.openwebinars.tienda.models.Producto;
import mx.com.openwebinars.tienda.models.ProductoCreate;
import mx.com.openwebinars.tienda.service.ProductoService;
import mx.com.openwebinars.tienda.service.StorageService;
import mx.com.openwebinars.tienda.utils.exceptions.SearchProductoNotResultException;
import mx.com.openwebinars.tienda.views.ProductoViews;


@RestController
@RequestMapping("/productos")
public class ProductoRestController {

	@Autowired
	private ProductoService productoService;
	
	@Autowired
	private ProductoConverter productoConverter;
	
	@Autowired
	private StorageService storageService;
	
	@Autowired
	private PaginationLinksUtils paginationLinks;

//	@GetMapping
//	@ResponseBody
//	public ResponseEntity<Page<Producto>>  obtenerTodos(@PageableDefault(size = 10, page= 0) Pageable pageable, HttpServletRequest request) {
//		var data = this.productoService.findAll(pageable);
//		if(data.isEmpty())
//			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay productos registrados");
//		else {
//			var response = data.map(this.productoConverter::converterTo);		
//			var uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());
//			return ResponseEntity.ok().header("link", this.paginationLinks.createLinkHeader(response, uriBuilder)).body(response);
//		}
//	}
//
//	@GetMapping(params = "nombre")
//	@ResponseBody
//	public ResponseEntity<Page<Producto>>  obtenerTodos(@RequestParam("nombre") String message, @PageableDefault(size = 10, page= 0) Pageable pageable, HttpServletRequest request) {
//		var data = this.productoService.findByProductoContainsIgnoreCase(message ,pageable);
//		if(data.isEmpty())
//			throw new SearchProductoNotResultException(message);
//		else {
//			var response = data.map(this.productoConverter::converterTo);		
//			var uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());
//			return ResponseEntity.ok().header("link", this.paginationLinks.createLinkHeader(response, uriBuilder)).body(response);
//		}	
//	}
	
	@GetMapping
	@ResponseBody
	@JsonView(ProductoViews.Dto.class)
	public ResponseEntity<Page<Producto>> buscar(
			@RequestParam("producto") Optional<String> producto,
			@RequestParam("precio") Optional<BigDecimal> precio,
			 @PageableDefault(size = 10, page= 0) Pageable pageable, 
			 HttpServletRequest request){
		var data = this.productoService.findByArgs(producto, precio, pageable);
		if(data.isEmpty())
			throw new SearchProductoNotResultException();
		else {
			var response = data.map(this.productoConverter::converterTo);		
			var uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());
			return ResponseEntity.ok().header("link", this.paginationLinks.createLinkHeader(response, uriBuilder)).body(response);
		}			
	}
	
	@ResponseBody
	@GetMapping("/{id}")
	public ProductoEntity obtenerUno(@PathVariable Long id) {
		return this.productoService.findById(id);
	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody
	public ResponseEntity<Producto> nuevoProducto(@RequestPart("producto") ProductoCreate request, @RequestPart("file") final MultipartFile file) {		
		var convert = this.productoConverter.converterTo(request);
		if(!file.isEmpty()) {
			String imagen = this.storageService.store(file);
			var url = MvcUriComponentsBuilder.fromMethodName(StorageController.class, "serveFile", imagen, null).build().toString();
			convert.setImagen(url);
		}
		var data = this.productoService.save(convert);
		var response = this.productoConverter.converterTo(data);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@ResponseBody
	@PutMapping("/{id}")
	public ResponseEntity<ProductoEntity> editarProducto(@RequestBody ProductoEntity request, @PathVariable Long id) {
		request.setId(id);
		var response = this.productoService.update(request);
		if(response == null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(response);
	}

	@ResponseBody
	@DeleteMapping("/{id}")
	public ResponseEntity<ProductoEntity> borrarProducto(@PathVariable Long id) {
		this.productoService.delete(id);
		return ResponseEntity.noContent().build();
	}

}

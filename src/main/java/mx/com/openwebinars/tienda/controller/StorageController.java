package mx.com.openwebinars.tienda.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;
import mx.com.openwebinars.tienda.service.StorageService;

@Slf4j
@Controller
@RequestMapping("/files")
public class StorageController {

	@Autowired
	private StorageService storageService;
	
	@GetMapping(value="/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename, HttpServletRequest request) {
		Resource file = storageService.loadAsResource(filename);		
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(file.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.info("Could not determine file type.");
        }
        if(contentType == null)
            contentType = "application/octet-stream";
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(file);
	}

}

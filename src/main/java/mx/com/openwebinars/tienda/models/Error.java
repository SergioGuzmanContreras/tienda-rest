package mx.com.openwebinars.tienda.models;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Data;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class Error {

	@NonNull
	private HttpStatus status;

	@JsonFormat(shape = Shape.STRING, pattern = "yyyy/MM/dd hh:mm:ss")
	private LocalDateTime date;
	
	@NonNull
	private String message;

}

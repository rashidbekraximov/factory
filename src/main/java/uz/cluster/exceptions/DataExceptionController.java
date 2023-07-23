package uz.cluster.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DataExceptionController {
	private static final long serialVersionUID = 1L;

	@ExceptionHandler(value = DataNotfoundException.class)
	public ResponseEntity<Object> exception(DataNotfoundException exception) {
		return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
	}

}

package br.com.paulomoreira.starB2W.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.paulomoreira.starB2W.util.Constants;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class MethodArgumentNotValidExceptionHandler {

	@ResponseStatus(BAD_REQUEST)
	@ResponseBody
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();
		List<FieldError> fieldErrors = result.getFieldErrors();
		return processFieldErrors(fieldErrors);
	}

	private ResponseEntity<?> processFieldErrors(List<FieldError> fieldErrors) {

		List<String> messages = new ArrayList<>();

		for (int i = 0; i < fieldErrors.size(); i++) {

			String message = Constants.FIELD.concat(fieldErrors.get(i).getField()).concat(Constants.WHITE_SPACE)
					.concat(fieldErrors.get(i).getDefaultMessage());

			messages.add(message);
		}

		return ResponseEntity.badRequest().body(messages);

	}

}
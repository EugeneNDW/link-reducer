package ndw.eugene.controllers;

import ndw.eugene.dto.ExceptionDto;
import ndw.eugene.repository.LinkNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllersExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(LinkNotFoundException.class)
  public ResponseEntity<ExceptionDto> handleLinkNotFoundException(LinkNotFoundException e) {
    return new ResponseEntity<>(new ExceptionDto(HttpStatus.BAD_REQUEST.value(),
            e.getMessage()), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ExceptionDto> handleIllegalArgumentException(IllegalArgumentException e) {
    return new ResponseEntity<>(new ExceptionDto(HttpStatus.BAD_REQUEST.value(),
            e.getMessage()), HttpStatus.BAD_REQUEST);
  }

}

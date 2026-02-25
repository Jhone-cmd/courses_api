package br.com.jhonecmd.courses_api.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice()
public class ExceptionHandlerController {

    private MessageSource messageSource;

    public ExceptionHandlerController(MessageSource message) {
        this.messageSource = message;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorMessageDTO>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {

        List<ErrorMessageDTO> errorMessageDTO = new ArrayList<>();

        ex.getBindingResult().getFieldErrors().forEach(err -> {

            String message = messageSource.getMessage(err, LocaleContextHolder.getLocale());

            ErrorMessageDTO error = new ErrorMessageDTO(message, err.getField());

            errorMessageDTO.add(error);
        });

        return new ResponseEntity<>(errorMessageDTO, HttpStatus.BAD_REQUEST);
    }
}

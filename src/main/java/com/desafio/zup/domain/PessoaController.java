package com.desafio.zup.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PessoaController {

    @Autowired
    private PessoaRepository repository;

    @PostMapping("/pessoas")
    ResponseEntity<Pessoa> addPessoa(@RequestBody @Valid PessoaRequest pessoa) throws Throwable {


        Pessoa novaPessoa = repository.save(pessoa.toModel());
        System.out.println(novaPessoa);
        return ResponseEntity
                .created(new URI("/pessoa/" + novaPessoa.getId()))
                .body(novaPessoa);

    }

    @GetMapping("/pessoas")
    ResponseEntity<List<Pessoa>> getPessoas() throws Throwable {
        List<Pessoa> pessoas = (List<Pessoa>) repository.findAll();
        return ResponseEntity
                .ok()
                .body(pessoas);

    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Map<String, String> handleExceptions(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }
}


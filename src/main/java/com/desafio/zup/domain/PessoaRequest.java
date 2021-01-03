package com.desafio.zup.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class PessoaRequest {


    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "E-mail é obrigatório")
    @Email(message = "E-mail inválido")
    @UniqueValue(domainClass = Pessoa.class, fieldName = "email",message="E-mail já cadastrado")
    private String email;

    @NotBlank(message = "CPF é obrigatório")
    @CPF(message = "CPF inválido")
    @UniqueValue(domainClass = Pessoa.class, fieldName = "cpf", message="CPF já cadastrado")
    private String cpf;

    @CustomDateConstraint
    private String dataNascimento;

    private SimpleDateFormat sdfrmt = new SimpleDateFormat("dd/MM/yyyy");

    public PessoaRequest(String nome, String email, String cpf, String dataNascimento) {
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }

    public PessoaRequest(){
    }


    public Pessoa toModel() throws ParseException {
        return new Pessoa(nome,email, cpf, sdfrmt.parse(dataNascimento));
    }


    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getCpf() {
        return cpf;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }
}

package com.desafio.zup.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class PessoaControllerTest {

    @MockBean
    private PessoaRepository repository;



    @Autowired
    private MockMvc mockMvc;

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void retornaStatusCorretoComUmaPessoaValida() throws Exception {
        PessoaRequest pessoaRequest = new PessoaRequest("Felipe", "felipe@com.br", "39188414809", "30/12/1991");
        Pessoa pessoa = pessoaRequest.toModel();
        doReturn(pessoa).when(repository).save(any());
        mockMvc.perform(MockMvcRequestBuilders.post("/pessoas")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(pessoaRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome", is(pessoa.getNome())))
                .andExpect(jsonPath("$.cpf", is(pessoa.getCpf())))
                .andExpect(jsonPath("$.email", is(pessoa.getEmail())));
    }

    @Test
    public void cadastraUmaPessoaNova() throws Exception {
        PessoaRequest pessoaRequest = new PessoaRequest("Felipe", "felipe@com.br", "39188414809", "30/12/1991");
        Pessoa pessoa = pessoaRequest.toModel();
        doReturn(pessoa).when(repository).save(any());
        mockMvc.perform(MockMvcRequestBuilders.post("/pessoas")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(pessoaRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome", is(pessoa.getNome())))
                .andExpect(jsonPath("$.cpf", is(pessoa.getCpf())))
                .andExpect(jsonPath("$.email", is(pessoa.getEmail())));
    }

    @Test
    public void falhaAoCadastrarUmaPessoaComDadosInvalidos() throws Exception {
        PessoaRequest pessoaRequest = new PessoaRequest();
        mockMvc.perform(MockMvcRequestBuilders.post("/pessoas")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(pessoaRequest)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.cpf", is("CPF é obrigatório")))
                .andExpect(jsonPath("$.email", is("E-mail é obrigatório")))
                .andExpect(jsonPath("$.nome", is("Nome é obrigatório")))
                .andExpect(jsonPath("$.dataNascimento", is("Data inválida")));

    }

}
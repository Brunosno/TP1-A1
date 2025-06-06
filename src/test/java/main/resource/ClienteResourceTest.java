package main.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import main.dto.clienteDTO.ClienteDTO;
import main.dto.clienteDTO.ClienteResponseDTO;
import main.service.cliente.ClienteService;

@QuarkusTest
public class ClienteResourceTest {

    @Inject
    ClienteService clienteService;

    static final Long ID_TELEFONE = 1L;
    static final List<Long> ENDERECOS_VAZIOS = List.of();

    @Test
    void testBuscarTodos() {
        given()
            .when().get("/Clientes")
            .then()
                .statusCode(200);
    }

    @Test
    void testIncluir() {
        ClienteDTO dto = new ClienteDTO(
            "João Silva",
            "123.456.789-00",
            "joao@email.com",
            ID_TELEFONE,
            ENDERECOS_VAZIOS
        );

        given()
            .contentType(ContentType.JSON)
            .body(dto)
            .when().post("/Clientes")
            .then()
                .statusCode(201)
                .body(
                    "id", notNullValue(),
                    "nome", is("João Silva"),
                    "cpf", is("123.456.789-00"),
                    "email", is("joao@email.com")
                );
    }

    @Test
    void testAlterar() {
        ClienteDTO dto = new ClienteDTO(
            "Maria Souza",
            "987.654.321-00",
            "maria@email.com",
            ID_TELEFONE,
            ENDERECOS_VAZIOS
        );

        Long id = clienteService.create(dto).id();

        ClienteDTO atualizado = new ClienteDTO(
            "Maria Souza Atualizada",
            "987.654.321-00",
            "mariaatualizada@email.com",
            ID_TELEFONE,
            ENDERECOS_VAZIOS
        );

        given()
            .contentType(ContentType.JSON)
            .body(atualizado)
            .when().put("/Clientes/" + id)
            .then()
                .statusCode(200);

        ClienteResponseDTO response = clienteService.findById(id);
        assertThat(response.nome(), is("Maria Souza Atualizada"));
        assertThat(response.email(), is("mariaatualizada@email.com"));
    }

    @Test
    void testBuscarPorCPF() {
        ClienteDTO dto = new ClienteDTO(
            "Carlos Mendes",
            "111.222.333-44",
            "carlos@email.com",
            ID_TELEFONE,
            ENDERECOS_VAZIOS
        );

        clienteService.create(dto);

        given()
            .pathParam("cpf", "111.222.333-44")
            .when().get("/Clientes/cpf/{cpf}")
            .then()
                .statusCode(200)
                .body("nome", is("Carlos Mendes"));
    }

    @Test
    void testBuscarPorCPFInexistente() {
        given()
            .pathParam("cpf", "000.000.000-00")
            .when().get("/Clientes/cpf/{cpf}")
            .then()
                .statusCode(404);
    }

    @Test
    void testBuscarPorId() {
        ClienteDTO dto = new ClienteDTO(
            "Lucas Oliveira",
            "222.333.444-55",
            "lucas@email.com",
            ID_TELEFONE,
            ENDERECOS_VAZIOS
        );

        Long id = clienteService.create(dto).id();

        given()
            .pathParam("id", id)
            .when().get("/Clientes/{id}")
            .then()
                .statusCode(200)
                .body("nome", is("Lucas Oliveira"))
                .body("cpf", is("222.333.444-55"))
                .body("email", is("lucas@email.com"));
    }

    @Test
    void testBuscarPorIdInexistente() {
        Long idInexistente = 9999L;

        given()
            .pathParam("id", idInexistente)
            .when().get("/Clientes/{id}")
            .then()
                .statusCode(404);
    }

    @Test
    void testApagar() {
        ClienteDTO dto = new ClienteDTO(
            "Excluir Cliente",
            "555.666.777-88",
            "excluir@cliente.com",
            ID_TELEFONE,
            ENDERECOS_VAZIOS
        );

        Long id = clienteService.create(dto).id();

        given()
            .when().delete("/Clientes/" + id)
            .then()
                .statusCode(204);

        ClienteResponseDTO response = clienteService.findById(id);
        assertNull(response);
    }
}

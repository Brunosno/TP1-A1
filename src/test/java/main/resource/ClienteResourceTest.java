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
import io.quarkus.test.security.TestSecurity;
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
    @TestSecurity(user = "BRUNO_SNO", roles = {"Adm"}, authorizationEnabled = true)
    void testBuscarTodos() {
        try {
            given()
                .when().get("/clientes")
                .then()
                    .statusCode(200);
        } catch (Exception e) {
            throw new RuntimeException("Falha no teste testBuscarTodos: " + e.getMessage(), e);
        }
    }

    @Test
    void testIncluir() {
        try {
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
                .when().post("/clientes")
                .then()
                    .statusCode(201)
                    .body(
                        "id", notNullValue(),
                        "nome", is("João Silva"),
                        "cpf", is("123.456.789-00"),
                        "email", is("joao@email.com")
                    );
        } catch (Exception e) {
            throw new RuntimeException("Falha no teste testIncluir: " + e.getMessage(), e);
        }
    }

    @Test
    @TestSecurity(user = "BRUNO_SNO", roles = {"Adm", "User"}, authorizationEnabled = true)
    void testAlterar() {
        try {
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
                .when().put("/clientes/" + id)
                .then()
                    .statusCode(200);

            ClienteResponseDTO response = clienteService.findById(id);
            assertThat(response.nome(), is("Maria Souza Atualizada"));
            assertThat(response.email(), is("mariaatualizada@email.com"));
        } catch (Exception e) {
            throw new RuntimeException("Falha no teste testAlterar: " + e.getMessage(), e);
        }
    }

    @Test
    @TestSecurity(user = "BRUNO_SNO", roles = {"Adm"}, authorizationEnabled = true)
    void testBuscarPorCPF() {
        try {
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
                .when().get("/clientes/cpf/{cpf}")
                .then()
                    .statusCode(200)
                    .body("nome", is("Carlos Mendes"));
        } catch (Exception e) {
            throw new RuntimeException("Falha no teste testBuscarPorCPF: " + e.getMessage(), e);
        }
    }

    @Test
    @TestSecurity(user = "BRUNO_SNO", roles = {"Adm", "User"}, authorizationEnabled = true)
    void testBuscarPorCPFInexistente() {
        try {
            given()
                .pathParam("cpf", "000.000.000-00")
                .when().get("/clientes/cpf/{cpf}")
                .then()
                    .statusCode(404);
        } catch (Exception e) {
            throw new RuntimeException("Falha no teste testBuscarPorCPFInexistente: " + e.getMessage(), e);
        }
    }

    @Test
    @TestSecurity(user = "BRUNO_SNO", roles = {"Adm", "User"}, authorizationEnabled = true)
    void testBuscarPorId() {
        try {
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
                .when().get("/clientes/{id}")
                .then()
                    .statusCode(200)
                    .body("nome", is("Lucas Oliveira"))
                    .body("cpf", is("222.333.444-55"))
                    .body("email", is("lucas@email.com"));
        } catch (Exception e) {
            throw new RuntimeException("Falha no teste testBuscarPorId: " + e.getMessage(), e);
        }
    }

    @Test
    @TestSecurity(user = "BRUNO_SNO", roles = {"Adm", "User"}, authorizationEnabled = true)
    void testBuscarPorIdInexistente() {
        try {
            Long idInexistente = 9999L;

            given()
                .pathParam("id", idInexistente)
                .when().get("/clientes/{id}")
                .then()
                    .statusCode(404);
        } catch (Exception e) {
            throw new RuntimeException("Falha no teste testBuscarPorIdInexistente: " + e.getMessage(), e);
        }
    }

    @Test
    @TestSecurity(user = "BRUNO_SNO", roles = {"Adm", "User"}, authorizationEnabled = true)
    void testApagar() {
        try {
            ClienteDTO dto = new ClienteDTO(
                "Excluir Cliente",
                "555.666.777-88",
                "excluir@cliente.com",
                ID_TELEFONE,
                ENDERECOS_VAZIOS
            );

            Long id = clienteService.create(dto).id();

            given()
                .when().delete("/clientes/" + id)
                .then()
                    .statusCode(204);

            ClienteResponseDTO response = clienteService.findById(id);
            assertNull(response);
        } catch (Exception e) {
            throw new RuntimeException("Falha no teste testApagar: " + e.getMessage(), e);
        }
    }
}

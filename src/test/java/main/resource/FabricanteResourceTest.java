package main.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import main.dto.fabricanteDTO.FabricanteDTO;
import main.dto.fabricanteDTO.FabricanteResponseDTO;
import main.service.fabricante.FabricanteService;

@QuarkusTest
public class FabricanteResourceTest {

    @Inject
    FabricanteService fabricanteService;

    static final Long ID_TELEFONE = 1L;
    static final List<Long> ENDERECOS = List.of();

    @Test
    void testBuscarTodos() {
        try {
            given()
                .when().get("/fabricantes")
                .then()
                    .statusCode(200);
        } catch (Exception e) {
            fail("Erro ao buscar todos os fabricantes: " + e.getMessage());
        }
    }

    @Test
    @TestSecurity(user = "BRUNO_SNO", roles = {"Adm"}, authorizationEnabled = true)
    void testIncluir() {
        try {
            FabricanteDTO dto = new FabricanteDTO(
                "Alimentos Tocantins",
                "12.345.678/0001-90",
                "contato@alito.com",
                ID_TELEFONE,
                ENDERECOS
            );

            given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when().post("/fabricantes")
                .then()
                    .statusCode(201)
                    .body(
                        "id", notNullValue(),
                        "nome", is("Alimentos Tocantins"),
                        "cnpj", is("12.345.678/0001-90"),
                        "email", is("contato@alito.com")
                    );
        } catch (Exception e) {
            fail("Erro ao incluir fabricante: " + e.getMessage());
        }
    }

    @Test
    @TestSecurity(user = "BRUNO_SNO", roles = {"Adm", "User"}, authorizationEnabled = true)
    void testAlterar() {
        try {
            FabricanteDTO dto = new FabricanteDTO(
                "Fornecedor Teste",
                "55.666.777/0001-00",
                "fornecedor@teste.com",
                ID_TELEFONE,
                ENDERECOS
            );

            Long id = fabricanteService.create(dto).id();

            FabricanteDTO atualizado = new FabricanteDTO(
                "Fornecedor Atualizado",
                "55.666.777/0001-00",
                "novoemail@teste.com",
                ID_TELEFONE,
                ENDERECOS
            );

            given()
                .contentType(ContentType.JSON)
                .body(atualizado)
                .when().put("/fabricantes/" + id)
                .then()
                    .statusCode(204);

            FabricanteResponseDTO response = fabricanteService.findById(id);
            assertThat(response.nome(), is("Fornecedor Atualizado"));
            assertThat(response.email(), is("novoemail@teste.com"));
        } catch (Exception e) {
            fail("Erro ao alterar fabricante: " + e.getMessage());
        }
    }

    @Test
    @TestSecurity(user = "BRUNO_SNO", roles = {"Adm", "User"}, authorizationEnabled = true)
    void testBuscarPorCNPJ() {
        try {
            FabricanteDTO dto = new FabricanteDTO(
                "CNPJ Teste",
                "22.333.444/0001-88",
                "teste@cnpj.com",
                ID_TELEFONE,
                ENDERECOS
            );

            fabricanteService.create(dto);

            given()
                .pathParam("cnpj", "22.333.444/0001-88")
                .when().get("/fabricantes/cnpj/{cnpj}")
                .then()
                    .statusCode(200)
                    .body("nome", is("CNPJ Teste"));
        } catch (Exception e) {
            fail("Erro ao buscar fabricante por CNPJ: " + e.getMessage());
        }
    }

    @Test
    @TestSecurity(user = "BRUNO_SNO", roles = {"Adm", "User"}, authorizationEnabled = true)
    void testApagar() {
        try {
            FabricanteDTO dto = new FabricanteDTO(
                "Excluir Fabricante",
                "99.999.999/0001-99",
                "excluir@fabricante.com",
                ID_TELEFONE,
                ENDERECOS
            );

            Long id = fabricanteService.create(dto).id();

            given()
                .when().delete("/fabricantes/" + id)
                .then()
                    .statusCode(204);

            FabricanteResponseDTO response = fabricanteService.findById(id);
            assertNull(response);
        } catch (Exception e) {
            fail("Erro ao apagar fabricante: " + e.getMessage());
        }
    }

    @Test
    @TestSecurity(user = "BRUNO_SNO", roles = {"Adm", "User"}, authorizationEnabled = true)
    void testBuscarPorId() {
        try {
            FabricanteDTO dto = new FabricanteDTO(
                "Fabricante Por ID",
                "11.222.333/0001-44",
                "id@fabricante.com",
                ID_TELEFONE,
                ENDERECOS
            );

            Long id = fabricanteService.create(dto).id();

            given()
                .pathParam("id", id)
                .when().get("/fabricantes/{id}")
                .then()
                    .statusCode(200)
                    .body("nome", is("Fabricante Por ID"));
        } catch (Exception e) {
            fail("Erro ao buscar fabricante por ID: " + e.getMessage());
        }
    }

    @Test
    @TestSecurity(user = "BRUNO_SNO", roles = {"Adm", "User"}, authorizationEnabled = true)
    void testBuscarPorIdInexistente() {
        try {
            given()
                .pathParam("id", 9999L)
                .when().get("/fabricantes/{id}")
                .then()
                    .statusCode(404);
        } catch (Exception e) {
            fail("Erro ao buscar fabricante por ID inexistente: " + e.getMessage());
        }
    }
}

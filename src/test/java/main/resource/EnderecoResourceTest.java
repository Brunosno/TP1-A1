package main.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import jakarta.inject.Inject;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import main.dto.enderecoDTO.EnderecoDTO;
import main.dto.enderecoDTO.EnderecoResponseDTO;
import main.service.endereco.EnderecoService;

@QuarkusTest
public class EnderecoResourceTest {

    @Inject
    EnderecoService enderecoService;

    @Test
    @TestSecurity(user = "BRUNO_SNO", roles = {"Adm", "User"}, authorizationEnabled = true)
    void testBuscarTodos() {
        try {
            given()
                .when().get("/enderecos")
                .then()
                    .statusCode(200);
        } catch (Exception e) {
            fail("Erro ao buscar todos os endereços: " + e.getMessage());
        }
    }

    @Test
    void testIncluir() {
        try {
            EnderecoDTO dto = new EnderecoDTO(
                "Rua Teste",
                "123",
                "Bairro Teste",
                "Cidade Teste",
                "TO",
                "77001-234"
            );

            given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when().post("/enderecos")
                .then()
                    .statusCode(201)
                    .body("rua", is("Rua Teste"))
                    .body("numero", is("123"))
                    .body("bairro", is("Bairro Teste"))
                    .body("cidade", is("Cidade Teste"))
                    .body("estado", is("TO"))
                    .body("cep", is("77001-234"));
        } catch (Exception e) {
            fail("Erro ao incluir endereço: " + e.getMessage());
        }
    }

    @Test
    @TestSecurity(user = "BRUNO_SNO", roles = {"Adm", "User"}, authorizationEnabled = true)
    void testAlterar() {
        try {
            EnderecoDTO dto = new EnderecoDTO(
                "Rua Teste Atualizada",
                "456",
                "Bairro Atualizado",
                "Cidade Atualizada",
                "SP",
                "77002-345"
            );

            EnderecoResponseDTO enderecoResponse = enderecoService.create(dto);
            Long id = enderecoResponse.id();

            EnderecoDTO atualizado = new EnderecoDTO(
                "Rua Atualizada",
                "789",
                "Bairro Novo",
                "Cidade Nova",
                "RJ",
                "77003-456"
            );

            given()
                .contentType(ContentType.JSON)
                .body(atualizado)
                .when().put("/enderecos/" + id)
                .then()
                    .statusCode(200);

            EnderecoResponseDTO response = enderecoService.findById(id);
            assertThat(response.rua(), is("Rua Atualizada"));
            assertThat(response.bairro(), is("Bairro Novo"));
            assertThat(response.estado(), is("RJ"));
        } catch (Exception e) {
            fail("Erro ao alterar endereço: " + e.getMessage());
        }
    }

    @Test
    @TestSecurity(user = "BRUNO_SNO", roles = {"Adm", "User"}, authorizationEnabled = true)
    void testBuscarPorId() {
        try {
            EnderecoDTO dto = new EnderecoDTO(
                "Rua Unica",
                "101",
                "Bairro Unico",
                "Cidade Unica",
                "DF",
                "77004-567"
            );

            EnderecoResponseDTO enderecoResponse = enderecoService.create(dto);
            Long id = enderecoResponse.id();

            given()
                .when().get("/enderecos/" + id)
                .then()
                    .statusCode(200)
                    .body("rua", is("Rua Unica"))
                    .body("bairro", is("Bairro Unico"))
                    .body("cidade", is("Cidade Unica"))
                    .body("estado", is("DF"))
                    .body("cep", is("77004-567"));
        } catch (Exception e) {
            fail("Erro ao buscar endereço por ID: " + e.getMessage());
        }
    }

    @Test
    @TestSecurity(user = "BRUNO_SNO", roles = {"Adm"}, authorizationEnabled = true)
    void testBuscarPorCEP() {
        try {
            EnderecoDTO dto = new EnderecoDTO(
                "Rua Teste CEP",
                "302",
                "Bairro Teste CEP",
                "Cidade Teste CEP",
                "DF",
                "77004-560"
            );

            EnderecoResponseDTO enderecoResponse = enderecoService.create(dto);
            String cep = enderecoResponse.cep();

            given()
                .when().get("/enderecos/cep/" + cep)
                .then()
                    .statusCode(200)
                    .body("rua", is("Rua Teste CEP"))
                    .body("bairro", is("Bairro Teste CEP"))
                    .body("cidade", is("Cidade Teste CEP"))
                    .body("estado", is("DF"))
                    .body("cep", is("77004-560"));
        } catch (Exception e) {
            fail("Erro ao buscar endereço por CEP: " + e.getMessage());
        }
    }

    @Test
    @TestSecurity(user = "BRUNO_SNO", roles = {"Adm", "User"}, authorizationEnabled = true)
    void testApagar() {
        try {
            EnderecoDTO dto = new EnderecoDTO(
                "Rua para Exclusão",
                "999",
                "Bairro para Exclusão",
                "Cidade para Exclusão",
                "GO",
                "77005-678"
            );

            EnderecoResponseDTO enderecoResponse = enderecoService.create(dto);
            Long id = enderecoResponse.id();

            given()
                .when().delete("/enderecos/" + id)
                .then()
                    .statusCode(204);

            EnderecoResponseDTO response = enderecoService.findById(id);
            assertNull(response);
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), is("Endereço não encontrado com ID: " + e.getMessage().replaceAll("\\D", "")));
        } catch (Exception e) {
            fail("Erro ao excluir endereço: " + e.getMessage());
        }
    }
}

package main.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import main.dto.controleDTO.ControleDTO;
import main.dto.controleDTO.ControleResponseDTO;
import main.service.controle.ControleService;

@QuarkusTest
public class ControleResourceTest {

    @Inject
    ControleService controleService;

    static final Long ID_FABRICANTE = 2L;
    static final Integer ID_COR = 4;

    @Test
    void testBuscarTodos() {
        try {
            given()
                .when().get("/controles")
                .then()
                    .statusCode(200);
        } catch (Exception e) {
            fail("Erro ao buscar todos os controles: " + e.getMessage());
        }
    }

    @Test
    @TestSecurity(user = "BRUNO_SNO", roles = {"Adm", "User"}, authorizationEnabled = true)
    void testIncluir() {
        try {
            ControleDTO dto = new ControleDTO(
                "Controle DualSense",
                ID_FABRICANTE,
                ID_COR,
                450D,
                "Bluetooth",
                "Bateria",
                true,
                true,
                5
            );

            given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when().post("/controles")
                .then()
                    .statusCode(201)
                    .body(
                        "id", notNullValue(),
                        "nome", is("Controle DualSense"),
                        "conexao", is("Bluetooth"),
                        "alimentacao", is("Bateria"),
                        "touchpad", is(true),
                        "gatilhosAdaptaveis", is(true)
                    );
        } catch (Exception e) {
            fail("Erro ao incluir controle: " + e.getMessage());
        }
    }

    @Test
    @TestSecurity(user = "BRUNO_SNO", roles = {"Adm", "User"}, authorizationEnabled = true)
    void testAlterar() {
        try {
            ControleDTO dto = new ControleDTO(
                "Controle Teste",
                ID_FABRICANTE,
                ID_COR,
                300D,
                "USB",
                "Pilhas",
                false,
                false,
                12
            );

            Long id = controleService.create(dto).id();

            ControleDTO atualizado = new ControleDTO(
                "Controle Atualizado",
                ID_FABRICANTE,
                ID_COR,
                500D,
                "Bluetooth",
                "Bateria",
                true,
                true,
                250
            );

            given()
                .contentType(ContentType.JSON)
                .body(atualizado)
                .when().put("/controles/" + id)
                .then()
                    .statusCode(204);

            ControleResponseDTO response = controleService.findById(id);
            assertThat(response.nome(), is("Controle Atualizado"));
            assertThat(response.alimentacao(), is("Bateria"));
        } catch (Exception e) {
            fail("Erro ao alterar controle: " + e.getMessage());
        }
    }

    @Test
    @TestSecurity(user = "BRUNO_SNO", roles = {"Adm", "User"}, authorizationEnabled = true)
    void testBuscarPorId() {
        try {
            ControleDTO dto = new ControleDTO(
                "Controle Unico",
                ID_FABRICANTE,
                ID_COR,
                330D,
                "USB-C",
                "Bateria",
                true,
                false,
                80
            );

            Long id = controleService.create(dto).id();

            given()
                .when().get("/controles/" + id)
                .then()
                    .statusCode(200)
                    .body("nome", is("Controle Unico"));
        } catch (Exception e) {
            fail("Erro ao buscar controle por ID: " + e.getMessage());
        }
    }

    @Test
    @TestSecurity(user = "BRUNO_SNO", roles = {"Adm", "User"}, authorizationEnabled = true)
    void testBuscarPorIdInexistente() {
        try {
            Long idInexistente = 9999L;

            given()
                .pathParam("id", idInexistente)
                .when().get("/controles/{id}")
                .then()
                    .statusCode(404);
        } catch (Exception e) {
            fail("Erro ao testar busca por ID inexistente: " + e.getMessage());
        }
    }

    @Test
    void testBuscarPorFabricante() {
        try {
            ControleDTO dto = new ControleDTO(
                "Controle Marca Teste",
                ID_FABRICANTE,
                ID_COR,
                300D,
                "Wireless",
                "Bateria",
                false,
                false,
                60
            );

            controleService.create(dto);

            given()
                .pathParam("fabricante", "Sony")
                .when().get("/controles/fabricante/{fabricante}")
                .then()
                    .statusCode(200);
        } catch (Exception e) {
            fail("Erro ao buscar por fabricante: " + e.getMessage());
        }
    }

    @Test
    void testBuscarPorCor() {
        try {
            ControleDTO dto = new ControleDTO(
                "Controle Preto",
                ID_FABRICANTE,
                ID_COR,
                300D,
                "Bluetooth",
                "Bateria",
                true,
                false,
                30
            );

            controleService.create(dto);

            given()
                .pathParam("cor", "PRETO")
                .when().get("/controles/cor/{cor}")
                .then()
                    .statusCode(200);
        } catch (Exception e) {
            fail("Erro ao buscar por cor: " + e.getMessage());
        }
    }

    @Test
    @TestSecurity(user = "BRUNO_SNO", roles = {"Adm", "User"}, authorizationEnabled = true)
    void testApagar() {
        try {
            ControleDTO dto = new ControleDTO(
                "Controle para Exclusão",
                ID_FABRICANTE,
                ID_COR,
                300D,
                "USB",
                "Pilhas",
                false,
                false,
                90
            );

            Long id = controleService.create(dto).id();

            given()
                .when().delete("/controles/" + id)
                .then()
                    .statusCode(204);

            ControleResponseDTO response = controleService.findById(id);
            assertNull(response);
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), is("Controle não encontrado com ID: " + e.getMessage().replaceAll("\\D", "")));
        } catch (Exception e) {
            fail("Erro ao apagar controle: " + e.getMessage());
        }
    }
}

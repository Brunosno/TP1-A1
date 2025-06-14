package main.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

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
        given()
            .when().get("/controles")
            .then()
                .statusCode(200);
    }

    @Test
    @TestSecurity(user = "BRUNO_SNO", roles = {"Adm", "User"}, authorizationEnabled = true)
    void testIncluir() {
        ControleDTO dto = new ControleDTO(
            "Controle DualSense",
            ID_FABRICANTE,
            ID_COR,
            450,
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
    }

    @Test
    @TestSecurity(user = "BRUNO_SNO", roles = {"Adm", "User"}, authorizationEnabled = true)
    void testAlterar() {
        ControleDTO dto = new ControleDTO(
            "Controle Teste",
            ID_FABRICANTE,
            ID_COR,
            300,
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
            500,
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
    }

    @Test
    @TestSecurity(user = "BRUNO_SNO", roles = {"Adm", "User"}, authorizationEnabled = true)
    void testBuscarPorId() {
        ControleDTO dto = new ControleDTO(
            "Controle Unico",
            ID_FABRICANTE,
            ID_COR,
            330,
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
    }

    @Test
    @TestSecurity(user = "BRUNO_SNO", roles = {"Adm", "User"}, authorizationEnabled = true)
    void testBuscarPorIdInexistente() {
        Long idInexistente = 9999L;

        given()
            .pathParam("id", idInexistente)
            .when().get("/clientes/{id}")
            .then()
                .statusCode(404);
    }


    @Test
    void testBuscarPorFabricante() {
        ControleDTO dto = new ControleDTO(
            "Controle Marca Teste",
            ID_FABRICANTE,
            ID_COR,
            300,
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
    }


    @Test
    void testBuscarPorCor() {
        ControleDTO dto = new ControleDTO(
            "Controle Preto",
            ID_FABRICANTE,
            ID_COR,
            300,
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
    }


    @Test
    @TestSecurity(user = "BRUNO_SNO", roles = {"Adm", "User"}, authorizationEnabled = true)
    void testApagar() {
        ControleDTO dto = new ControleDTO(
            "Controle para Exclusão",
            ID_FABRICANTE,
            ID_COR,
            300,
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
    }
}

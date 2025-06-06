package main.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import main.dto.telefoneDTO.TelefoneDTO;
import main.dto.telefoneDTO.TelefoneResponseDTO;
import main.service.telefone.TelefoneService;

@QuarkusTest
public class TelefoneResourceTest {

    @Inject
    TelefoneService telefoneService;

    @Test
    void testBuscarTodos() {
        given()
            .when().get("/telefones")
            .then()
                .statusCode(200);
    }

    @Test
    void testIncluir() {
        TelefoneDTO dto = new TelefoneDTO("(63) 91234-5678");

        given()
            .contentType(ContentType.JSON)
            .body(dto)
            .when().post("/telefones")
            .then()
                .statusCode(201)
                .body(
                    "id", notNullValue(),
                    "numero", is("(63) 91234-5678")
                );
    }

    @Test
    void testBuscarPorId() {
        TelefoneDTO dto = new TelefoneDTO("(63) 93456-7890");
        Long id = telefoneService.create(dto).id();

        given()
            .when().get("/telefones/" + id)
            .then()
                .statusCode(200)
                .body("numero", is("(63) 93456-7890"));
    }

    @Test
    void testBuscarPorNumero() {
        TelefoneDTO dto = new TelefoneDTO("(63) 90000-0001");
        telefoneService.create(dto);

        given()
            .pathParam("numero", "(63) 90000-0001")
            .when().get("/telefones/numero/{numero}")
            .then()
                .statusCode(200)
                .body("numero", is("(63) 90000-0001"));
    }

    @Test
    void testAlterar() {
        TelefoneDTO dto = new TelefoneDTO("(63) 98888-0000");
        Long id = telefoneService.create(dto).id();

        TelefoneDTO atualizado = new TelefoneDTO("(63) 97777-1111");

        given()
            .contentType(ContentType.JSON)
            .body(atualizado)
            .when().put("/telefones/" + id)
            .then()
                .statusCode(200);

        TelefoneResponseDTO response = telefoneService.findById(id);
        assertThat(response.numero(), is("(63) 97777-1111"));
    }

    @Test
    void testApagar() {
        TelefoneDTO dto = new TelefoneDTO("(63) 96666-2222");
        Long id = telefoneService.create(dto).id();

        given()
            .when().delete("/telefones/" + id)
            .then()
                .statusCode(204);

        TelefoneResponseDTO response = telefoneService.findById(id);
        assertNull(response);
    }
}

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
import main.dto.fabricanteDTO.FabricanteDTO;
import main.dto.fabricanteDTO.FabricanteResponseDTO;
import main.service.fabricante.FabricanteService;

@QuarkusTest
public class FabricanteResourceTest {

    @Inject
    FabricanteService fabricanteService;

    static final Long ID_TELEFONE = 1L;
    static final List<Long> ENDERECOS_VAZIOS = List.of();

    @Test
    void testBuscarTodos() {
        given()
            .when().get("/Fabricantes")
            .then()
                .statusCode(200);
    }

    @Test
    void testIncluir() {
        FabricanteDTO dto = new FabricanteDTO(
            "Alimentos Tocantins", 
            "12.345.678/0001-90", 
            "contato@alito.com", 
            ID_TELEFONE,
            ENDERECOS_VAZIOS
        );

        given()
            .contentType(ContentType.JSON)
            .body(dto)
            .when().post("/Fabricantes")
            .then()
                .statusCode(201)
                .body(
                    "id", notNullValue(),
                    "nome", is("Alimentos Tocantins"),
                    "cnpj", is("12.345.678/0001-90"),
                    "email", is("contato@alito.com")
                );
    }

    @Test
    void testAlterar() {
        FabricanteDTO dto = new FabricanteDTO(
            "Fornecedor Teste", 
            "55.666.777/0001-00", 
            "fornecedor@teste.com", 
            ID_TELEFONE,
            ENDERECOS_VAZIOS
        );

        Long id = fabricanteService.create(dto).id();

        FabricanteDTO atualizado = new FabricanteDTO(
            "Fornecedor Atualizado", 
            "55.666.777/0001-00", 
            "novoemail@teste.com", 
            ID_TELEFONE,
            ENDERECOS_VAZIOS
        );

        given()
            .contentType(ContentType.JSON)
            .body(atualizado)
            .when().put("/Fabricantes/" + id)
            .then()
                .statusCode(200);

        FabricanteResponseDTO response = fabricanteService.findById(id);
        assertThat(response.nome(), is("Fornecedor Atualizado"));
        assertThat(response.email(), is("novoemail@teste.com"));
    }

    @Test
    void testBuscarPorCNPJ() {
        FabricanteDTO dto = new FabricanteDTO(
            "CNPJ Teste", 
            "22.333.444/0001-88", 
            "teste@cnpj.com", 
            ID_TELEFONE,
            ENDERECOS_VAZIOS
        );

        fabricanteService.create(dto);

        given()
            .pathParam("cnpj", "22.333.444/0001-88")
            .when().get("/Fabricantes/cnpj/{cnpj}")
            .then()
                .statusCode(200)
                .body("nome", is("CNPJ Teste"));
    }

    @Test
    void testApagar() {
        FabricanteDTO dto = new FabricanteDTO(
            "Excluir Fabricante", 
            "99.999.999/0001-99", 
            "excluir@fabricante.com", 
            ID_TELEFONE,
            ENDERECOS_VAZIOS
        );

        Long id = fabricanteService.create(dto).id();

        given()
            .when().delete("/Fabricantes/" + id)
            .then()
                .statusCode(204);

        FabricanteResponseDTO response = fabricanteService.findById(id);
        assertNull(response);
    }
}

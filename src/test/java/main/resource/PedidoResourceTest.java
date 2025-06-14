package main.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import main.dto.pedidoDTO.PedidoDTO;
import main.dto.pedidoDTO.PedidoResponseDTO;
import main.model.controle.Controle;
import main.service.pedido.PedidoService;
import main.repository.ControleRepository;

import org.junit.jupiter.api.Test;

import java.util.List;

@QuarkusTest
public class PedidoResourceTest {

    @Inject
    PedidoService pedidoService;

    @Inject
    ControleRepository controleRepository;

    static final Long ID_USUARIO = 1L;
    static final List<Long> IDS_CONTROLES = List.of(1L, 2L);

    @Test
    @TestSecurity(user = "BRUNO_SNO", roles = {"Adm", "User"}, authorizationEnabled = true)
    void testBuscarTodos() {
        given()
            .when().get("/pedidos")
            .then()
                .statusCode(200);
    }

    @Test
    @TestSecurity(user = "BRUNO_SNO", roles = {"Adm", "User"}, authorizationEnabled = true)
    void testIncluir() {
        PedidoDTO dto = new PedidoDTO(ID_USUARIO, IDS_CONTROLES, 3, 3L);

        int precoEsperado = IDS_CONTROLES.stream()
            .map(controleRepository::findById)
            .mapToInt(Controle::getPreco)
            .sum();

        given()
            .contentType(ContentType.JSON)
            .body(dto)
            .when().post("/pedidos")
            .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("id_usuario", is(ID_USUARIO.intValue()))
                .body("controles.size()", is(IDS_CONTROLES.size()))
                .body("preco", is(precoEsperado));
    }

    @Test
    @TestSecurity(user = "BRUNO_SNO", roles = {"Adm"}, authorizationEnabled = true)
    void testAlterar() {
        PedidoDTO dto = new PedidoDTO(ID_USUARIO, IDS_CONTROLES, 3,3L);
        PedidoResponseDTO pedidoResponse = pedidoService.create(dto);
        Long id = pedidoResponse.id();

        PedidoDTO atualizado = new PedidoDTO(ID_USUARIO, List.of(1L), 2,3L);

        given()
            .contentType(ContentType.JSON)
            .body(atualizado)
            .when().put("/pedidos/" + id)
            .then()
                .statusCode(200)
                .body("id", is(id.intValue()))
                .body("controles.size()", is(1));
    }

    @Test
    @TestSecurity(user = "BRUNO_SNO", roles = {"Adm", "User"}, authorizationEnabled = true)
    void testBuscarPorId() {
        PedidoDTO dto = new PedidoDTO(ID_USUARIO, IDS_CONTROLES, 3,3L);
        PedidoResponseDTO pedidoResponse = pedidoService.create(dto);
        Long id = pedidoResponse.id();

        given()
            .when().get("/pedidos/" + id)
            .then()
                .statusCode(200)
                .body("id", is(id.intValue()))
                .body("id_usuario", is(ID_USUARIO.intValue()))
                .body("controles.size()", is(IDS_CONTROLES.size()));
    }

    @Test
    @TestSecurity(user = "BRUNO_SNO", roles = {"Adm"}, authorizationEnabled = true)
    void testBuscarPorUsuario() {

        PedidoDTO dto = new PedidoDTO(ID_USUARIO, IDS_CONTROLES, 3,3L);
        pedidoService.create(dto);

        given()
            .when().get("/pedidos/usuario/" + ID_USUARIO)
            .then()
                .statusCode(200);
    }

    @Test
    @TestSecurity(user = "BRUNO_SNO", roles = {"Adm", "User"}, authorizationEnabled = true)
    void testApagar() {
        PedidoDTO dto = new PedidoDTO(ID_USUARIO, IDS_CONTROLES, 3,3L);
        PedidoResponseDTO pedidoResponse = pedidoService.create(dto);
        Long id = pedidoResponse.id();

        given()
            .when().delete("/pedidos/" + id)
            .then()
                .statusCode(204);

        try {
            PedidoResponseDTO response = pedidoService.findById(id);
            assertNull(response);
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), is("Pedido não encontrado com ID: " + id));
        }
    }
}

package main.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import main.dto.pedidoDTO.ItemPedidoDTO;
import main.dto.pedidoDTO.PedidoDTO;
import main.dto.pedidoDTO.PedidoResponseDTO;
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

    private List<ItemPedidoDTO> gerarItensParaTeste() {
        Long idProduto = 1L;
        int quantidade = 2;
        return List.of(new ItemPedidoDTO(idProduto, quantidade));
    }

    static final Long ID_USUARIO = 1L;

    @Test
    @TestSecurity(user = "BRUNO_SNO", roles = {"Adm", "User"}, authorizationEnabled = true)
    void testBuscarTodos() {
        try {
            given()
                .when().get("/pedidos")
                .then()
                    .statusCode(200);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar todos os pedidos", e);
        }
    }

    @Test
    @TestSecurity(user = "BRUNO_SNO", roles = {"Adm", "User"}, authorizationEnabled = true)
    void testIncluir() {
        try {
            List<ItemPedidoDTO> itens = gerarItensParaTeste();
            PedidoDTO dto = new PedidoDTO(ID_USUARIO, itens, 3, 3L);

            given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when().post("/pedidos")
                .then()
                    .statusCode(201)
                    .body("id", notNullValue())
                    .body("id_usuario", is(ID_USUARIO.intValue()))
                    .body("lista.size()", is(itens.size()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao incluir pedido", e);
        }
    }

    @Test
    @TestSecurity(user = "BRUNO_SNO", roles = {"Adm"}, authorizationEnabled = true)
    void testAlterar() {
        try {
            List<ItemPedidoDTO> itens = gerarItensParaTeste();
            PedidoDTO dto = new PedidoDTO(ID_USUARIO, itens, 3, 3L);
            PedidoResponseDTO pedidoResponse = pedidoService.create(dto);
            Long id = pedidoResponse.id();

            PedidoDTO atualizado = new PedidoDTO(ID_USUARIO, gerarItensParaTeste(), 2, 3L);

            given()
                .contentType(ContentType.JSON)
                .body(atualizado)
                .when().put("/pedidos/" + id)
                .then()
                    .statusCode(200)
                    .body("id", is(id.intValue()))
                    .body("lista.size()", is(1));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao alterar pedido", e);
        }
    }

    @Test
    @TestSecurity(user = "BRUNO_SNO", roles = {"Adm", "User"}, authorizationEnabled = true)
    void testBuscarPorId() {
        try {
            List<ItemPedidoDTO> itens = gerarItensParaTeste();
            PedidoDTO dto = new PedidoDTO(ID_USUARIO, itens, 3, 3L);
            PedidoResponseDTO pedidoResponse = pedidoService.create(dto);
            Long id = pedidoResponse.id();

            given()
                .when().get("/pedidos/" + id)
                .then()
                    .statusCode(200)
                    .body("id", is(id.intValue()))
                    .body("id_usuario", is(ID_USUARIO.intValue()))
                    .body("lista.size()", is(itens.size()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar pedido por ID", e);
        }
    }

    @Test
    @TestSecurity(user = "BRUNO_SNO", roles = {"Adm"}, authorizationEnabled = true)
    void testBuscarPorUsuario() {
        try {
            List<ItemPedidoDTO> itens = gerarItensParaTeste();
            PedidoDTO dto = new PedidoDTO(ID_USUARIO, itens, 3, 3L);
            pedidoService.create(dto);

            given()
                .when().get("/pedidos/usuario/" + ID_USUARIO)
                .then()
                    .statusCode(200);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar pedidos por usuário", e);
        }
    }

    @Test
    @TestSecurity(user = "BRUNO_SNO", roles = {"Adm", "User"}, authorizationEnabled = true)
    void testApagar() {
        try {
            
            PedidoResponseDTO pedido = pedidoService.findById(2L);

            Long id = pedido.id();

            given()
                .when().delete("/pedidos/" + id)
                .then()
                    .statusCode(204);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao apagar pedido", e);
        }
    }
}

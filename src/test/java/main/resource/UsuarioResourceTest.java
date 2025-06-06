package main.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import main.dto.clienteDTO.ClienteDTO;
import main.dto.clienteDTO.ClienteResponseDTO;
import main.dto.telefoneDTO.TelefoneDTO;
import main.dto.telefoneDTO.TelefoneResponseDTO;
import main.dto.usuarioDTO.UsuarioDTO;
import main.model.usuario.Perfil;
import main.service.cliente.ClienteService;
import main.service.telefone.TelefoneService;
import main.service.usuario.UsuarioService;

@QuarkusTest
public class UsuarioResourceTest {

    @Inject
    UsuarioService usuarioService;

    @Inject
    ClienteService clienteService;

    @Inject
    TelefoneService telefoneService;

    static final List<Long> ENDERECOS_VAZIOS = List.of();
    static Long idUsuario;

    private Long criarTelefone(String numero) {
        TelefoneDTO telefoneDTO = new TelefoneDTO(numero);
        TelefoneResponseDTO telefone = telefoneService.create(telefoneDTO);
        return telefone.id();
    }

    private Long criarCliente(String nome, String cpf, String email, Long numero) {
        ClienteDTO clienteDTO = new ClienteDTO(nome, cpf, email, numero, ENDERECOS_VAZIOS);
        ClienteResponseDTO cliente = clienteService.create(clienteDTO);
        return cliente.id();
    }

    @Test
    void testBuscarTodos() {
        given()
            .when().get("/usuarios")
            .then()
                .statusCode(200);
    }

    @Test
    void testCriar() {
        Long idCliente = criarCliente("Usuário Teste", "123.456.789-80", "usuario@email.com", criarTelefone("(63) 98444-1452"));

        UsuarioDTO dto = new UsuarioDTO("usuario_teste", "senha123", Perfil.USER.getId(), idCliente);

        given()
            .contentType(ContentType.JSON)
            .body(dto)
            .when().post("/usuarios")
            .then()
                .statusCode(201)
                .body(
                    "id", notNullValue(),
                    "username", is("usuario_teste")
                );
    }

    @Test
    void testAlterar() {
        Long idCliente = criarCliente("Usuário Alterar", "987.654.321-01", "usuario2@email.com", criarTelefone("(63) 98444-1451"));

        UsuarioDTO dtoOriginal = new UsuarioDTO("usuario_teste_alt", "senha123", Perfil.USER.getId(), idCliente);
        Long idUsuario = usuarioService.create(dtoOriginal).id();

        UsuarioDTO atualizado = new UsuarioDTO("usuario_atualizado", "novaSenha", Perfil.ADM.getId(), idCliente);

        given()
            .contentType(ContentType.JSON)
            .body(atualizado)
            .pathParam("id", idUsuario)
            .when().put("/usuarios/{id}")
            .then()
                .statusCode(204);

        given()
            .pathParam("id", idUsuario)
            .when().get("/usuarios/{id}")
            .then()
                .statusCode(200)
                .body(
                    "username", is("usuario_atualizado"),
                    "perfil", is("ADM")
                );
    }

    @Test
    void testApagar() {
        try {
            Long idCliente = criarCliente("Usuário Deletar", "555.666.777-88", "usuario3@email.com", criarTelefone("(63) 98444-1450"));
            UsuarioDTO dto = new UsuarioDTO("usuario_deletar", "senha123", Perfil.USER.getId(), idCliente);
            Long id = usuarioService.create(dto).id();

            given()
                .pathParam("id", id)
                .when().delete("/usuarios/{id}")
                .then()
                    .statusCode(204);

            given()
                .when().get("/usuarios/" + id)
                .then()
                    .statusCode(404);
        } catch (Exception e) {
            System.err.println("Erro inesperado em testApagar: " + e.getMessage());
            e.printStackTrace();
            fail("Falha inesperada ao apagar usuário: " + e.getMessage());
        }
    }


    @Test
    void testBuscarPorIdInexistente() {
        given()
            .pathParam("id", 9999)
            .when().get("/usuarios/{id}")
            .then()
                .statusCode(404);
    }
}

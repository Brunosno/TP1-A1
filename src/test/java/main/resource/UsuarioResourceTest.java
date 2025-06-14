package main.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import io.quarkus.test.security.TestSecurity;

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
@TestMethodOrder(OrderAnnotation.class)
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

    @Order(1)
    @Test
    @TestSecurity(user = "BRUNO_SNO", roles = {"Adm", "User"}, authorizationEnabled = false)
    void testCriar() throws Exception {
        try{
            Long idCliente = criarCliente("Usuário Teste", "123.456.789-80", "usuario@email.com", criarTelefone("(63) 98444-1452"));
            UsuarioDTO dto = new UsuarioDTO("usuario_teste", "senha123", Perfil.USER.getId(), idCliente);

            given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when().post("/usuarios")
                .then()
                    .statusCode(201)
                    .body("id", notNullValue())
                    .body("username", is("usuario_teste"));
        } catch (Exception e) {
            fail("Falha inesperada em testCriar: " + e.getMessage());
        }
    }
    
    @Order(2)
    @Test
    @TestSecurity(user = "BRUNO_SNO", roles = {"Adm"}, authorizationEnabled = true)
    void testBuscarTodos() {
        given()
            .when().get("/usuarios")
            .then()
                .statusCode(200);
    }

    @Order(3)
    @Test
    @TestSecurity(user = "BRUNO_SNO", roles = {"Adm", "User"}, authorizationEnabled = true)
    void testAlterar() {
        try {
            Long idCliente = 1L;

            Long idUsuario = 1L;
            UsuarioDTO atualizado = new UsuarioDTO("usuario_atualizado", "novaSenha", Perfil.USER.getId(), idCliente);

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
                        "username", is("usuario_atualizado")
                    );
        } catch (Exception e) {
            fail("Falha inesperada em testAlterar: " + e.getMessage());
        }
    }

    @Order(4)
    @Test
    @TestSecurity(user = "BRUNO_SNO", roles = {"Adm", "User"}, authorizationEnabled = true)
    void testApagar() {
        try {
            Long idUsuario = 2L;

            given()
                .pathParam("id", idUsuario)
                .when().delete("/usuarios/{id}")
                .then()
                    .statusCode(204);
        } catch (Exception e) {
            System.err.println("Erro inesperado em testApagar: " + e.getMessage());
            e.printStackTrace();
            fail("Falha inesperada ao apagar usuário: " + e.getMessage());
        }
    }


    @Order(5)
    @Test
    @TestSecurity(user = "BRUNO_SNO", roles = {"Adm", "User"}, authorizationEnabled = true)
    void testBuscarPorIdInexistente() {
        given()
            .pathParam("id", 9999)
            .when().get("/usuarios/{id}")
            .then()
                .statusCode(404);
    }
}

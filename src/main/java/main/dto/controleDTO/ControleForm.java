package main.dto.controleDTO;

import java.util.Arrays;
import java.util.List;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

public class ControleForm {

    @RestForm
    public String nome;

    @RestForm
    public Long idFabricante;

    @RestForm
    public List<String> idsPlataformas;

    @RestForm
    public Integer idCor;

    @RestForm
    public Double preco;

    @RestForm
    public String conexao;

    @RestForm
    public String alimentacao;

    @RestForm
    public Boolean touchpad;

    @RestForm
    public Boolean gatilhosAdaptaveis;

    @RestForm
    public List<Long> loteIds;

    @RestForm
    public List<FileUpload> imagens;

    public ControleDTO toDTO() {
        List<Long> plataformas = idsPlataformas != null
                ? idsPlataformas.stream()
                    .flatMap(s -> Arrays.stream(s.split(",")))
                    .filter(x -> !x.isBlank())
                    .map(Long::valueOf)
                    .toList()
                : null;

        return new ControleDTO(
            nome,
            preco,
            idFabricante,
            idCor,
            plataformas,
            conexao,
            alimentacao,
            touchpad != null ? touchpad : false,
            gatilhosAdaptaveis != null ? gatilhosAdaptaveis : false,
            loteIds,
            null
        );
    }
}

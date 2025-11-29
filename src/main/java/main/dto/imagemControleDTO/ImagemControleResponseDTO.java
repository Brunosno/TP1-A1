package main.dto.imagemControleDTO;

import main.model.imagemControle.ImagemControle;

public record ImagemControleResponseDTO(
    Long id,
    String url,
    String descricao  
) {
    public static ImagemControleResponseDTO valueOf(ImagemControle imagemControle){
        return new ImagemControleResponseDTO(imagemControle.getId(), imagemControle.getUrl(), imagemControle.getDescricao());
    }
}

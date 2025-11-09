package main.model.converterjpa;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import main.model.controle.Conexao;

@Converter(autoApply = true)
public class ConexaoConverter implements AttributeConverter<Conexao, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Conexao conexao) {
        return conexao == null ? null : conexao.getId();

    }

    @Override
    public Conexao convertToEntityAttribute(Integer id) {
        return Conexao.valueOf(id);
    }
    
}

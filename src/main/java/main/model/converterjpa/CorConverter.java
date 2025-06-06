package main.model.converterjpa;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import main.model.controle.Cor;

@Converter(autoApply = true)
public class CorConverter implements AttributeConverter<Cor, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Cor cor) {
        return cor == null ? null : cor.getId();

    }

    @Override
    public Cor convertToEntityAttribute(Integer id) {
        return Cor.valueOf(id);
    }
    
}

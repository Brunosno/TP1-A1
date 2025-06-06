package main.model.pagamento;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TipoPagamento {
    DEBITO(1, "Débito"),
    CREDITO(2, "Crédito"),
    PIX(3, "Pix"),
    BOLETO(4, "Boleto");

    private final int id;
    private final String nome;

    TipoPagamento(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public static TipoPagamento valueOf(Integer id) {
        if (id == null)
            return null;
        for (TipoPagamento tipo : TipoPagamento.values()) {
            if (tipo.getId() == id)
                return tipo;
        }
        return null;
    }
}
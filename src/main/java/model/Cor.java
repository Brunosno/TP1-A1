package model;


import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Cor {
    PRETO(1, "PRETO"), 
    BRANCO(2, "BRANCO"), 
    VERMELHO(3, "VERMELHO"), 
    AZUL(4, "AZUL"), 
    ROXO(5, "ROXO");

    private final int ID;
    private final String NOME;

    Cor(int id, String nome) {
        this.ID = id;
        this.NOME = nome;
    }

    public int getId() {
        return ID;
    }

    public String getNome() {
        return NOME;
    }

     public static Cor valueOf(Integer id) {
        if (id == null)
            return null;
        for (Cor r : Cor.values()) {
            if (r.getId() == id)
                return r;
        }
        return null;
     }

}

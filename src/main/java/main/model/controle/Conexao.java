package main.model.controle;


import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Conexao {
    WIRED(1, "COM FIO"), 
    WIRELESS(2, "SEM FIO");

    private final Integer ID;
    private final String NOME;

    Conexao(Integer id, String nome) {
        this.ID = id;
        this.NOME = nome;
    }

    public Integer getId() {
        return ID;
    }

    public String getNome() {
        return NOME;
    }

    public static Conexao valueOf(Integer id) {
        if (id == null)
            return null;
        for (Conexao r : Conexao.values()) {
            if (r.getId() == id)
                return r;
        }
        return null;
     }

}

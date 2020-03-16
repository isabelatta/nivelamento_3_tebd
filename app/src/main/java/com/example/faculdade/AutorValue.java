package com.example.faculdade;

public class AutorValue implements java.io.Serializable {
    private Long id;
    private String nome;
    private String endereco;

    public Long getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public String getEndereco() {
        return this.endereco;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String toString() {
        return this.nome;
    }
}

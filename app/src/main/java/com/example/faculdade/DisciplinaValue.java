package com.example.faculdade;

public class DisciplinaValue implements java.io.Serializable {
    private Long id;
    private String disciplina;

    public Long getId() {
        return id;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public String toString() {
        return disciplina;
    }
}

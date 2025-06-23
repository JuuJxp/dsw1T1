package br.ufscar.dc.dsw.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@SuppressWarnings("serial")
@Entity
@Table(name = "Administradores")
@PrimaryKeyJoinColumn(name = "id_usuario")
public class Administrador extends Usuario {

    @NotBlank(message = "{NotBlank.administrador.nome}")
    @Size(max = 255, message = "{Size.administrador.nome}")
    @Column(nullable = false, length = 255)
    private String nome;

    public Administrador() {
        setPapel(PapelUsuario.ADMIN);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
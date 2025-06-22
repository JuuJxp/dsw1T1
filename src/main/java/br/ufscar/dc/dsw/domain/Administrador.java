package br.ufscar.dc.dsw.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "Administradores")
public class Administrador extends Usuario {

    public Administrador() {
        setPapel(PapelUsuario.ADMIN);
    }
}
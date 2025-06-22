package br.ufscar.dc.dsw.domain;

import java.sql.Timestamp;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@SuppressWarnings("serial")
@Entity
@Table(name = "Usuarios")
@Inheritance(strategy = InheritanceType.JOINED) 
@AttributeOverride(name = "id", column = @Column(name = "id_usuario")) 
public abstract class Usuario extends AbstractEntity<Long> {

    // Atributo email (não vazio, tamanho máximo 255 caracteres, deve ser um email válido)
	@NotBlank(message = "{NotBlank.usuario.email}")
	@Size(max = 255, message = "{Size.usuario.email}")
    @Email(message = "{Email.usuario.email}")
	@Column(nullable = false, length = 255, unique = true)
	private String email;

    // Atributo senha (não vazio, tamanho máximo 255 caracteres)
	@NotBlank(message = "{NotBlank.usuario.senha}")
	@Size(max = 255, message = "{Size.usuario.senha}")
	@Column(nullable = false, length = 255)
	private String senha;

    // Atributo papel (não vazio, deve ser um dos valores do enum PapelUsuario)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PapelUsuario papel;

    @Column(nullable = false)
    private boolean ativo;
    
    // Campos de data/hora 
    @Column(name = "criado_em", updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp criadoEm;

    @Column(name = "atualizado_em", updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp atualizadoEm;

	public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public PapelUsuario getPapel() {
        return papel;
    }

    public void setPapel(PapelUsuario papel) {
        this.papel = papel;
    }

    public Timestamp getCriadoEm() {
        return criadoEm;
    }

    public Timestamp getAtualizadoEm() {
        return atualizadoEm;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Usuario() {
        this.ativo = true;
    }
}
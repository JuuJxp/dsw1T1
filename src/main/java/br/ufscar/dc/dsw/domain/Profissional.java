package br.ufscar.dc.dsw.domain;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@SuppressWarnings("serial")
@Entity
@Table(name = "Profissionais")
@AttributeOverride(name = "id", column = @Column(name = "id_profissional")) 
@PrimaryKeyJoinColumn(name = "id_usuario")
public class Profissional extends Usuario {

    @NotBlank(message = "{NotBlank.profissional.nome}")
    @Size(max = 255, message = "{Size.profissional.nome}")
    @Column(nullable = false, length = 255)
    private String nome;

    @NotBlank(message = "{NotBlank.profissional.cpf}")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}", message = "{Pattern.profissional.cpf}")
    @Column(nullable = false, length = 14, unique = true)
    private String cpf;

    @NotBlank(message = "{NotBlank.profissional.telefone}")
    @Size(max = 20, message = "{Size.profissional.telefone}")
    @Column(nullable = false, length = 20)
    private String telefone;

    @NotNull(message = "{NotNull.profissional.sexo}")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SexoProfissional sexo;

    @NotNull(message = "{NotNull.profissional.dataNascimento}")
    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @NotBlank(message = "{NotBlank.profissional.cidade}")
    @Size(max = 255, message = "{Size.profissional.cidade}")
    @Column(nullable = false, length = 255)
    private String cidade;

    @Size(max = 255, message = "{Size.profissional.estado}")
    @Column(length = 255)
    private String estado;

    @Size(max = 255, message = "{Size.profissional.pais}")
    @Column(length = 255)
    private String pais;

    @OneToMany(mappedBy = "profissional")
    private List<Candidatura> candidaturas;

    public Profissional() {
        setPapel(PapelUsuario.PROFISSIONAL);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public SexoProfissional getSexo() {
        return sexo;
    }

    public void setSexo(SexoProfissional sexo) {
        this.sexo = sexo;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public List<Candidatura> getCandidaturas() {
        return candidaturas;
    }

    public void setCandidaturas(List<Candidatura> candidaturas) {
        this.candidaturas = candidaturas;
    }
}
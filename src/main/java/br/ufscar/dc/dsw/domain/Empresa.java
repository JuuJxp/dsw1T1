package br.ufscar.dc.dsw.domain;

import java.util.List;

import br.ufscar.dc.dsw.validation.UniqueCNPJ;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@SuppressWarnings("serial")
@Entity
@Table(name = "Empresas")
@PrimaryKeyJoinColumn(name = "id_usuario")
public class Empresa extends Usuario {

    @UniqueCNPJ(message = "{Unique.empresa.cnpj}")
    @NotBlank(message = "{NotBlank.empresa.cnpj}")
    @Pattern(regexp = "\\d{2}\\.\\d{3}\\.\\d{3}\\/\\d{4}\\-\\d{2}", message = "{Pattern.empresa.cnpj}")
    @Column(nullable = false, length = 18, unique = true)
    private String cnpj;

    @NotBlank(message = "{NotBlank.empresa.nome}")
    @Size(max = 255, message = "{Size.empresa.nome}")
    @Column(nullable = false, length = 255)
    private String nome;

    @NotBlank(message = "{NotBlank.empresa.descricao}")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @NotBlank(message = "{NotBlank.empresa.cidade}")
    @Size(max = 255, message = "{Size.empresa.cidade}")
    @Column(nullable = false, length = 255)
    private String cidade;

     @Size(max = 255, message = "{Size.empresa.estado}")
     @Column(length = 255)
     private String estado;

     @Size(max = 255, message = "{Size.empresa.pais}")
     @Column(length = 255)
     private String pais;

    @OneToMany(mappedBy = "empresa")
    private List<Vaga> vagas;

    public Empresa() {
        setPapel(PapelUsuario.EMPRESA);
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public List<Vaga> getVagas() {
        return vagas;
    }

    public void setVagas(List<Vaga> vagas) {
        this.vagas = vagas;
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
}
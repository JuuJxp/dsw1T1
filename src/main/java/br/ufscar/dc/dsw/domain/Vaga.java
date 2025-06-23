package br.ufscar.dc.dsw.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.List;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@SuppressWarnings("serial")
@Entity
@Table(name = "Vagas")
@AttributeOverride(name = "id", column = @Column(name = "id_vaga")) 
public class Vaga extends AbstractEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "id_empresa", nullable = false)
    private Empresa empresa;

    @NotBlank(message = "{NotBlank.vaga.titulo}")
    @Size(max = 255, message = "{Size.vaga.titulo}")
    @Column(nullable = false, length = 255)
    private String titulo; 

    @NotBlank(message = "{NotBlank.vaga.descricao}")
    @Size(max = 255, message = "{Size.vaga.descricao}")
    @Column(nullable = false, length = 255)
    private String descricao; 

    @PositiveOrZero(message = "{PositiveOrZero.vaga.remuneracao}")
    @Column(nullable = true, precision = 10, scale = 2)
    private BigDecimal remuneracao;

    @NotNull(message = "{NotNull.vaga.dataLimiteInscricao}")
    @FutureOrPresent(message = "{FutureOrPresent.vaga.dataLimiteInscricao}")
    @Column(name = "data_limite_inscricao", nullable = false)
    private Date dataLimiteInscricao;

    @NotBlank(message = "{NotBlank.vaga.cidade}")
    @Size(max = 255, message = "{Size.vaga.cidade}")
    @Column(nullable = false, length = 255)
    private String cidade;

    @NotBlank(message = "{NotBlank.vaga.estado}")
    @Size(max = 255, message = "{Size.vaga.estado}")
    @Column(nullable = false, length = 255)
    private String estado;

    @NotBlank(message = "{NotBlank.vaga.pais}")
    @Size(max = 255, message = "{Size.vaga.pais}")
    @Column(nullable = false, length = 255)
    private String pais;

    @Column(name = "criado_em", updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp criadoEm;

    @Column(nullable = false)
    private boolean ativa;

    @OneToMany(mappedBy = "vaga")
    private List<Candidatura> candidaturas;

    public Vaga() {}

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Timestamp getCriadoEm() {
        return criadoEm;
    }

    public BigDecimal getRemuneracao() {
        return remuneracao;
    }

    public void setRemuneracao(BigDecimal remuneracao) {
        this.remuneracao = remuneracao;
    }

    public Date getDataLimiteInscricao() {
        return dataLimiteInscricao;
    }

    public void setDataLimiteInscricao(Date dataLimiteInscricao) {
        this.dataLimiteInscricao = dataLimiteInscricao;
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

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public List<Candidatura> getCandidaturas() {
        return candidaturas;
    }

    public void setCandidaturas(List<Candidatura> candidaturas) {
        this.candidaturas = candidaturas;
    }
}
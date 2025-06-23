package br.ufscar.dc.dsw.domain;

import java.sql.Timestamp;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Entity
@Table(name = "Candidaturas", uniqueConstraints = {@UniqueConstraint(columnNames = {"id_profissional", "id_vaga"})})
@AttributeOverride(name = "id", column = @Column(name = "id_candidatura")) 
public class Candidatura extends AbstractEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "id_profissional", nullable = false)
    private Profissional profissional;

    @ManyToOne
    @JoinColumn(name = "id_vaga", nullable = false)
    private Vaga vaga;

    @NotNull(message = "{NotNull.candidatura.status}")
    @Enumerated(EnumType.STRING)
    @Column(name = "status_vaga", nullable = false, length = 30)
    private StatusCandidatura statusVaga;

    @Lob
    @Column(columnDefinition="LONGBLOB", nullable = true)
    private byte[] curriculo;
    
    @Column(name = "data_candidatura", updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp dataCandidatura;

    public Profissional getProfissional() {
        return profissional;
    }

    public void setProfissional(Profissional profissional) {
        this.profissional = profissional;
    }

    public byte[] getCurriculo() {
        return curriculo;
    }

    public void setCurriculo(byte[] curriculo) {
        this.curriculo = curriculo;
    }

    public Vaga getVaga() {
        return vaga;
    }

    public void setVaga(Vaga vaga) {
        this.vaga = vaga;
    }

    public StatusCandidatura getStatusVaga() {
        return statusVaga;
    }

    public void setStatusVaga(StatusCandidatura statusVaga) {
        this.statusVaga = statusVaga;
    }

    public Timestamp getDataCandidatura() {
        return dataCandidatura;
    }
}
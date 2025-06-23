package br.ufscar.dc.dsw.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;

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

    @Column(name = "data_candidatura", updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp dataCandidatura;

    public Profissional getProfissional() {
        return profissional;
    }

    public void setProfissional(Profissional profissional) {
        this.profissional = profissional;
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
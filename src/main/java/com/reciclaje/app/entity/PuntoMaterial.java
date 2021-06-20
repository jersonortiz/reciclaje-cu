/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reciclaje.app.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jerso
 */
@Entity
@Table(name = "punto_material")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PuntoMaterial.findAll", query = "SELECT p FROM PuntoMaterial p"),
    @NamedQuery(name = "PuntoMaterial.findById", query = "SELECT p FROM PuntoMaterial p WHERE p.id = :id")})

public class PuntoMaterial implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "id_material", referencedColumnName = "id")
    @ManyToOne(optional = false)

    private Material idMaterial;
    @JoinColumn(name = "id_punto", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private PuntoReciclaje idPunto;

    public PuntoMaterial() {
    }

    public PuntoMaterial(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Material getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(Material idMaterial) {
        this.idMaterial = idMaterial;
    }

    public PuntoReciclaje getIdPunto() {
        return idPunto;
    }

    public void setIdPunto(PuntoReciclaje idPunto) {
        this.idPunto = idPunto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PuntoMaterial)) {
            return false;
        }
        PuntoMaterial other = (PuntoMaterial) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.reciclaje.app.entity.PuntoMaterial[ id=" + id + " ]";
    }

}

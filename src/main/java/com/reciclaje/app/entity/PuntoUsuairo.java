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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jerso
 */
@Entity
@Table(name = "punto_usuairo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PuntoUsuairo.findAll", query = "SELECT p FROM PuntoUsuairo p"),
    @NamedQuery(name = "PuntoUsuairo.findById", query = "SELECT p FROM PuntoUsuairo p WHERE p.id = :id")})
public class PuntoUsuairo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "id_punto", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private PuntoReciclaje idPunto;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario idUsuario;

    public PuntoUsuairo() {
    }

    public PuntoUsuairo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PuntoReciclaje getIdPunto() {
        return idPunto;
    }

    public void setIdPunto(PuntoReciclaje idPunto) {
        this.idPunto = idPunto;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
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
        if (!(object instanceof PuntoUsuairo)) {
            return false;
        }
        PuntoUsuairo other = (PuntoUsuairo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.reciclaje.app.entity.PuntoUsuairo[ id=" + id + " ]";
    }
    
}

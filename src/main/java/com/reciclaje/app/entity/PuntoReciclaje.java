/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reciclaje.app.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jerso
 */
@Entity
@Table(name = "punto_reciclaje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PuntoReciclaje.findAll", query = "SELECT p FROM PuntoReciclaje p"),
    @NamedQuery(name = "PuntoReciclaje.findById", query = "SELECT p FROM PuntoReciclaje p WHERE p.id = :id"),
    @NamedQuery(name = "PuntoReciclaje.findByNombre", query = "SELECT p FROM PuntoReciclaje p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "PuntoReciclaje.findByDireccion", query = "SELECT p FROM PuntoReciclaje p WHERE p.direccion = :direccion"),
    @NamedQuery(name = "PuntoReciclaje.findByLatitud", query = "SELECT p FROM PuntoReciclaje p WHERE p.latitud = :latitud"),
    @NamedQuery(name = "PuntoReciclaje.findByLongitud", query = "SELECT p FROM PuntoReciclaje p WHERE p.longitud = :longitud")})
public class PuntoReciclaje implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "direccion")
    private String direccion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "latitud")
    private double latitud;
    @Basic(optional = false)
    @NotNull
    @Column(name = "longitud")
    private double longitud;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPunto")
    private List<PuntoUsuairo> puntoUsuairoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPunto")
    private List<PuntoMaterial> puntoMaterialList;
    @JoinColumn(name = "id_ciudad", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Ciudad idCiudad;

    public PuntoReciclaje() {

        this.puntoUsuairoList = new ArrayList<PuntoUsuairo>();
        this.puntoMaterialList = new ArrayList<PuntoMaterial>();
    }

    public PuntoReciclaje(Integer id) {
        this.id = id;
        this.puntoUsuairoList = new ArrayList<PuntoUsuairo>();
        this.puntoMaterialList = new ArrayList<PuntoMaterial>();
    }

    public PuntoReciclaje(Integer id, String nombre, String direccion, double latitud, double longitud) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.puntoUsuairoList = new ArrayList<PuntoUsuairo>();
        this.puntoMaterialList = new ArrayList<PuntoMaterial>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    @XmlTransient
    public List<PuntoUsuairo> getPuntoUsuairoList() {
        return puntoUsuairoList;
    }

    public void setPuntoUsuairoList(List<PuntoUsuairo> puntoUsuairoList) {
        this.puntoUsuairoList = puntoUsuairoList;
    }

    @XmlTransient
    public List<PuntoMaterial> getPuntoMaterialList() {
        return puntoMaterialList;
    }

    public void setPuntoMaterialList(List<PuntoMaterial> puntoMaterialList) {
        this.puntoMaterialList = puntoMaterialList;
    }

    public Ciudad getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(Ciudad idCiudad) {
        this.idCiudad = idCiudad;
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
        if (!(object instanceof PuntoReciclaje)) {
            return false;
        }
        PuntoReciclaje other = (PuntoReciclaje) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.reciclaje.app.entity.PuntoReciclaje[ id=" + id + " ]";
    }

}

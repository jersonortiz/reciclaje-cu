package com.reciclaje.app.entity;

import com.reciclaje.app.entity.Ciudad;
import com.reciclaje.app.entity.PuntoMaterial;
import com.reciclaje.app.entity.PuntoUsuairo;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2021-06-18T10:32:26")
@StaticMetamodel(PuntoReciclaje.class)
public class PuntoReciclaje_ { 

    public static volatile ListAttribute<PuntoReciclaje, PuntoUsuairo> puntoUsuairoList;
    public static volatile SingularAttribute<PuntoReciclaje, Double> latitud;
    public static volatile SingularAttribute<PuntoReciclaje, Double> longitud;
    public static volatile SingularAttribute<PuntoReciclaje, String> direccion;
    public static volatile ListAttribute<PuntoReciclaje, PuntoMaterial> puntoMaterialList;
    public static volatile SingularAttribute<PuntoReciclaje, Integer> id;
    public static volatile SingularAttribute<PuntoReciclaje, String> nombre;
    public static volatile SingularAttribute<PuntoReciclaje, Ciudad> idCiudad;

}
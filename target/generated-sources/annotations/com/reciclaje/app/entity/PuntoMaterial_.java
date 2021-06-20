package com.reciclaje.app.entity;

import com.reciclaje.app.entity.Material;
import com.reciclaje.app.entity.PuntoReciclaje;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2021-06-18T10:32:26")
@StaticMetamodel(PuntoMaterial.class)
public class PuntoMaterial_ { 

    public static volatile SingularAttribute<PuntoMaterial, PuntoReciclaje> idPunto;
    public static volatile SingularAttribute<PuntoMaterial, Material> idMaterial;
    public static volatile SingularAttribute<PuntoMaterial, Integer> id;

}
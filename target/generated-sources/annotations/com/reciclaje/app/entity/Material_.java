package com.reciclaje.app.entity;

import com.reciclaje.app.entity.PuntoMaterial;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2021-06-18T10:32:26")
@StaticMetamodel(Material.class)
public class Material_ { 

    public static volatile SingularAttribute<Material, String> descripcion;
    public static volatile ListAttribute<Material, PuntoMaterial> puntoMaterialList;
    public static volatile SingularAttribute<Material, Integer> id;
    public static volatile SingularAttribute<Material, String> nombre;

}
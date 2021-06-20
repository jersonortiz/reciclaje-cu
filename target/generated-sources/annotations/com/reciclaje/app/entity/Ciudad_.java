package com.reciclaje.app.entity;

import com.reciclaje.app.entity.Departamento_1;
import com.reciclaje.app.entity.PuntoReciclaje;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2021-06-18T10:32:26")
@StaticMetamodel(Ciudad.class)
public class Ciudad_ { 

    public static volatile SingularAttribute<Ciudad, Departamento_1> idDepartamento;
    public static volatile SingularAttribute<Ciudad, Integer> id;
    public static volatile ListAttribute<Ciudad, PuntoReciclaje> puntoReciclajeList;
    public static volatile SingularAttribute<Ciudad, String> nombre;

}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reciclaje.app.dto;

import java.util.ArrayList;
import lombok.Data;
import lombok.ToString;

/**
 *
 * @author jerso
 */
@Data
@ToString
public class PuntoReciclajeJson {

    private String id;
    private String nombre;
    private String direccion;
    private Double latitud;
    private Double longitud;
    private ArrayList<String> materiales;

}

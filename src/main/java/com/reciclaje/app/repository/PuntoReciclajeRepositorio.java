/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reciclaje.app.repository;

import com.reciclaje.app.entity.PuntoReciclaje;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author jerso
 */
public interface PuntoReciclajeRepositorio extends CrudRepository<PuntoReciclaje, Long> {

    PuntoReciclaje findById(Integer id);

    List<PuntoReciclaje> findByIdCiudad(Integer id);
    
    void deleteById(Integer id);
}

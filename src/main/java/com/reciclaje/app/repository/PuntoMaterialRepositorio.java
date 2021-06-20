/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reciclaje.app.repository;

import com.reciclaje.app.entity.Material;
import com.reciclaje.app.entity.PuntoMaterial;
import com.reciclaje.app.entity.PuntoReciclaje;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author jerso
 */
public interface PuntoMaterialRepositorio extends CrudRepository<PuntoMaterial, Long> {

    PuntoMaterial findById(Integer id);

    List<PuntoMaterial> findByIdPunto(PuntoReciclaje punto);

    List<PuntoMaterial> findByIdMaterial(Material material);
    
    PuntoMaterial findByIdMaterialAndIdPunto(Material material,PuntoReciclaje punto);
}

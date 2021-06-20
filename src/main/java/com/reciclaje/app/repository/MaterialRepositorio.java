/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reciclaje.app.repository;

import com.reciclaje.app.entity.Material;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author jerso
 */
public interface MaterialRepositorio extends CrudRepository<Material, Long> {

    Material findById(Integer id);
    
    Material findByNombre(String nombre);

    void deleteById(Integer id);
    
}

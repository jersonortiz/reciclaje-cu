/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reciclaje.app.repository;

import com.reciclaje.app.entity.PuntoReciclaje;
import com.reciclaje.app.entity.PuntoUsuairo;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author jerso
 */
public interface PuntoUsuarioRepositorio extends CrudRepository<PuntoUsuairo, Long> {
    PuntoUsuairo findById(Integer id);

    List<PuntoReciclaje> findByIdPunto(Integer id);

    List<PuntoUsuairo> findByIdUsuario(Integer id);
}

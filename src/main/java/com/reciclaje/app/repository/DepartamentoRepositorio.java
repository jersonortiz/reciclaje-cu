/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reciclaje.app.repository;

import com.reciclaje.app.entity.Departamento_1;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author jerso
 */
public interface DepartamentoRepositorio extends CrudRepository<Departamento_1, Long> {

    Departamento_1 findById(Integer id);

    void deleteById(Integer id);
}

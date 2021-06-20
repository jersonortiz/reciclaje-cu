/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reciclaje.app.repository;

import com.reciclaje.app.entity.Usuario;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author jerso
 */
public interface UsuarioRepositorio extends CrudRepository<Usuario, Long> {

    Usuario findById(Integer id);

    Usuario findByNombre(String nombre);
}

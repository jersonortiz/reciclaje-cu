/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reciclaje.app.controller;

import com.reciclaje.app.dto.MensajeJson;
import com.reciclaje.app.dto.UsuarioJson;
import com.reciclaje.app.dto.UsuarioLoginJson;
import com.reciclaje.app.entity.Usuario;
import com.reciclaje.app.repository.UsuarioRepositorio;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author jerso
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UsuarioController {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UsuarioLoginJson user) {

        Usuario usuario = usuarioRepositorio.findByNombre(user.getNombre());

        if (usuario == null) {
            return new ResponseEntity("Usuario no valido", HttpStatus.UNAUTHORIZED);
        }

        if (!usuario.getContraseña().equals(user.getContraseña())) {
            return new ResponseEntity("No coincide la password", HttpStatus.UNAUTHORIZED);
        }
        String token = getJWTToken(user.getNombre());
        return ResponseEntity.ok(JSON(token));
    }

    @PostMapping("/registro")
    public ResponseEntity registro(@RequestBody UsuarioJson user) {

        Usuario usuario = usuarioRepositorio.findByNombre(user.getNombre());

        if (usuario == null) {
            Usuario usr = new Usuario();
            usr.setNombre(user.getNombre());
            usr.setContraseña(user.getContraseña());
            usuarioRepositorio.save(usr);
            MensajeJson msg = new MensajeJson();
            msg.setMsg("ok");
            return ResponseEntity.ok(msg);
        }
        MensajeJson msg = new MensajeJson();
        msg.setMsg("no");
        return ResponseEntity.ok(msg);

    }

    @PostMapping("/editar")
    public ResponseEntity editar(@RequestBody UsuarioJson user) {
        int usrid = Integer.parseInt(user.getId());
        Usuario usuario = new Usuario();
        usuario.setId(usrid);
        usuario.setNombre(user.getNombre());
        usuario.setContraseña(user.getContraseña());

        try {
            usuarioRepositorio.save(usuario);
            MensajeJson msg = new MensajeJson();
            msg.setMsg("ok");
            return ResponseEntity.ok(msg);
        } catch (Exception e) {
            MensajeJson msg = new MensajeJson();
            msg.setMsg("no");
            return ResponseEntity.ok(msg);
        }

    }

    @PostMapping("/listar")
    public ResponseEntity listar() {

        ArrayList<Usuario> users = (ArrayList<Usuario>) usuarioRepositorio.findAll();
        ArrayList<UsuarioJson> lista = new ArrayList<UsuarioJson>();

        for (Usuario x : users) {
            UsuarioJson userj = new UsuarioJson();
            userj.setNombre(x.getNombre());
            userj.setId("" + x.getId());
            lista.add(userj);
        }
        return ResponseEntity.ok(lista);

    }

    @PostMapping("/eliminar")
    public ResponseEntity eliminar(@RequestBody UsuarioJson user) {

        Usuario usr = new Usuario();
        usr.setId(Integer.parseInt(user.getId()));

        try {
            usuarioRepositorio.delete(usr);
            MensajeJson msg = new MensajeJson();
            msg.setMsg("ok");
            return ResponseEntity.ok(msg);
        } catch (Exception e) {
            MensajeJson msg = new MensajeJson();
            msg.setMsg("no");
            return new ResponseEntity(msg, HttpStatus.BAD_REQUEST);
        }

    }

    private Map<String, String> JSON(String token) {
        Map<String, String> response = new HashMap();
        response.put("token", token);
        return response;
    }

    private String getJWTToken(String username) {
        //String tipoUsuario = tipoUsuario(usuario);
        String secretKey = "mySecretKey";
        //List<GrantedAuthority> grantedAuthorities = AuthorityUtils
        //.commaSeparatedStringToAuthorityList("ROLE_USER," + tipoUsuario);

        //.claim("authorities",
        //                grantedAuthorities.stream()
        //                        .map(GrantedAuthority::getAuthority)
        //                        .collect(Collectors.toList()))
        String token = Jwts.builder()
                .setId("softtekJWT")
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();
        return "Bearer " + token;
    }

}

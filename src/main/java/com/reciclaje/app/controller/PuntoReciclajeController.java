/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reciclaje.app.controller;

import com.reciclaje.app.dto.MaterialJson;
import com.reciclaje.app.dto.MensajeJson;
import com.reciclaje.app.dto.PuntoReciclajeJson;
import com.reciclaje.app.entity.Ciudad;
import com.reciclaje.app.entity.Material;
import com.reciclaje.app.entity.PuntoMaterial;
import com.reciclaje.app.entity.PuntoReciclaje;
import com.reciclaje.app.repository.CiudadRepositorio;
import com.reciclaje.app.repository.MaterialRepositorio;
import com.reciclaje.app.repository.PuntoMaterialRepositorio;
import com.reciclaje.app.repository.PuntoReciclajeRepositorio;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author jerso
 */
@RestController
@RequestMapping("/punto")
@CrossOrigin
//@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class PuntoReciclajeController {

    @Autowired
    private PuntoReciclajeRepositorio puntoReciclajeRepositorio;
    @Autowired
    private CiudadRepositorio ciudadRepositorio;
    @Autowired
    private MaterialRepositorio materialRepositorio;
    @Autowired
    private PuntoMaterialRepositorio puntoMaterialRepositorio;

    @PostMapping("/nuevo")
    public ResponseEntity nuevo(@RequestBody PuntoReciclajeJson puntoreciclaje) {

        if (puntoreciclaje != null) {
            if ((puntoreciclaje.getNombre() != null)
                    || (puntoreciclaje.getDireccion() != null)
                    || (puntoreciclaje.getLatitud() != null)
                    || (puntoreciclaje.getLongitud() != null)) {

                Ciudad ciud = ciudadRepositorio.findById(1);
                PuntoReciclaje punto = new PuntoReciclaje();
                punto.setNombre(puntoreciclaje.getNombre());
                punto.setDireccion(puntoreciclaje.getDireccion());
                punto.setIdCiudad(ciud);
                punto.setLatitud(puntoreciclaje.getLatitud());
                punto.setLongitud(puntoreciclaje.getLongitud());
                punto = puntoReciclajeRepositorio.save(punto);

                ArrayList<String> materiales = puntoreciclaje.getMateriales();
                for (String x : materiales) {
                    Material m = materialRepositorio.findByNombre(x);

                    PuntoMaterial pm = new PuntoMaterial();
                    pm.setIdPunto(punto);
                    pm.setIdMaterial(m);
                    pm = puntoMaterialRepositorio.save(pm);

                }

                return ResponseEntity.ok("correcto");
            }
        }
        return new ResponseEntity("error no valido", HttpStatus.BAD_REQUEST);

    }

    @PostMapping("/editar")
    public ResponseEntity editar(@RequestBody PuntoReciclajeJson puntoreciclaje) {

        if (puntoreciclaje != null) {
            if ((puntoreciclaje.getId() != null)
                    || (puntoreciclaje.getNombre() != null)
                    || (puntoreciclaje.getDireccion() != null)
                    || (puntoreciclaje.getLatitud() != null)
                    || (puntoreciclaje.getLongitud() != null)) {

                Ciudad ciud = new Ciudad();
                ciud.setId(1);
                PuntoReciclaje punto = new PuntoReciclaje();
                punto.setId(Integer.parseInt(puntoreciclaje.getId()));
                punto.setNombre(puntoreciclaje.getNombre());
                punto.setDireccion(puntoreciclaje.getDireccion());
                punto.setIdCiudad(ciud);
                punto.setLatitud(puntoreciclaje.getLatitud());
                punto.setLongitud(puntoreciclaje.getLongitud());
                System.out.println("antes");
                punto = puntoReciclajeRepositorio.save(punto);
                System.out.println("despues");
                MensajeJson msg = new MensajeJson();
                msg.setMsg("ok");
                return ResponseEntity.ok(msg);
            }
        }
        MensajeJson msg = new MensajeJson();
        msg.setMsg("no");
        return new ResponseEntity(msg, HttpStatus.BAD_REQUEST);

    }

    @PostMapping("/borrar")
    public ResponseEntity borrar(@RequestBody PuntoReciclajeJson puntoreciclaje) {

        if (puntoreciclaje != null) {
            if (puntoreciclaje.getId() != null) {
                int idp = Integer.parseInt(puntoreciclaje.getId());

                PuntoReciclaje punto = new PuntoReciclaje();
                punto.setId(idp);
                try {
                    puntoReciclajeRepositorio.delete(punto);
                    MensajeJson msg = new MensajeJson();
                    msg.setMsg("ok");
                    return ResponseEntity.ok(msg);
                } catch (Exception e) {
                    MensajeJson msg = new MensajeJson();
                    msg.setMsg("no");
                    return new ResponseEntity(msg, HttpStatus.BAD_REQUEST);
                }

            }

        }
        return new ResponseEntity("error no valido", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/especifico")
    public ResponseEntity especifico(@RequestBody PuntoReciclajeJson puntoreciclaje) {

        if (puntoreciclaje != null) {
            if (puntoreciclaje.getId() != null) {
                int idp = Integer.parseInt(puntoreciclaje.getId());

                PuntoReciclaje pr = puntoReciclajeRepositorio.findById(idp);
                if (pr != null) {

                    PuntoReciclajeJson prj = new PuntoReciclajeJson();
                    prj.setNombre(pr.getNombre());
                    prj.setDireccion(pr.getDireccion());
                    prj.setId("" + pr.getId());
                    prj.setLatitud(pr.getLatitud());
                    prj.setLongitud(pr.getLongitud());

                    return ResponseEntity.ok(prj);
                }
                return new ResponseEntity("no existe", HttpStatus.BAD_REQUEST);

            }

        }
        return new ResponseEntity("error no valido", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/listar")
    public ResponseEntity listar() {

        ArrayList<PuntoReciclaje> puntos = (ArrayList<PuntoReciclaje>) puntoReciclajeRepositorio.findAll();
        ArrayList<PuntoReciclajeJson> lista = new ArrayList<PuntoReciclajeJson>();

        for (PuntoReciclaje pr : puntos) {
            PuntoReciclajeJson prj = new PuntoReciclajeJson();
            prj.setNombre(pr.getNombre());
            prj.setDireccion(pr.getDireccion());
            prj.setId("" + pr.getId());
            prj.setLatitud(pr.getLatitud());
            prj.setLongitud(pr.getLongitud());
            lista.add(prj);
        }
        return ResponseEntity.ok(lista);

    }

}

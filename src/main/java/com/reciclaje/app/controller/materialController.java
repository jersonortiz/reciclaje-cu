/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reciclaje.app.controller;

import com.reciclaje.app.dto.ListaJson;
import com.reciclaje.app.dto.MaterialJson;
import com.reciclaje.app.dto.MensajeJson;
import com.reciclaje.app.dto.PuntoMaterialJson;
import com.reciclaje.app.dto.PuntoReciclajeJson;
import com.reciclaje.app.entity.Ciudad;
import com.reciclaje.app.entity.Material;
import com.reciclaje.app.entity.PuntoMaterial;
import com.reciclaje.app.entity.PuntoReciclaje;
import com.reciclaje.app.repository.MaterialRepositorio;
import com.reciclaje.app.repository.PuntoMaterialRepositorio;
import com.reciclaje.app.repository.PuntoReciclajeRepositorio;
import java.util.ArrayList;
import java.util.List;
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
@RequestMapping("/material")
@CrossOrigin
//@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class materialController {

    @Autowired
    private MaterialRepositorio materialRepositorio;
    @Autowired
    private PuntoMaterialRepositorio puntoMaterialRepositorio;
    @Autowired
    private PuntoReciclajeRepositorio puntoReciclajeRepositorio;

    @PostMapping("/nuevo")
    public ResponseEntity nuevo(@RequestBody MaterialJson material) {
        if (material != null) {
            if ((material.getNombre() != null) || (material.getDescripcion() != null)) {
                Material m = new Material();
                m.setNombre(material.getNombre());
                m.setDescripcion(material.getDescripcion());
                m = materialRepositorio.save(m);
                MensajeJson msg = new MensajeJson();
                msg.setMsg("ok");
                return ResponseEntity.ok(msg);

            }
        }
        MensajeJson msg = new MensajeJson();
        msg.setMsg("no");
        return new ResponseEntity(msg, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/listar")
    public ResponseEntity listar() {
        ArrayList<Material> materiales = (ArrayList<Material>) materialRepositorio.findAll();
        ListaJson<MaterialJson> l = new ListaJson<MaterialJson>();
        ArrayList<MaterialJson> arr = new ArrayList<MaterialJson>();
        for (Material m : materiales) {
            MaterialJson ma = new MaterialJson();
            ma.setId("" + m.getId());
            ma.setNombre(m.getNombre());
            ma.setDescripcion(m.getDescripcion());
            arr.add(ma);
        }
        l.setLista(arr);
        return ResponseEntity.ok(l);
    }

    @PostMapping("/borrar")
    public ResponseEntity borrar(@RequestBody MaterialJson material) {
        if (material != null) {
            if ((material.getNombre() != null)) {
                int mval = Integer.parseInt(material.getId());
                Material ma = new Material();
                ma.setId(mval);
                materialRepositorio .delete(ma);
                MensajeJson msg = new MensajeJson();
                msg.setMsg("ok");
                return ResponseEntity.ok(msg);
            }
        }
        MensajeJson msg = new MensajeJson();
        
        msg.setMsg("no");
        return new ResponseEntity(msg, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/modificar")
    public ResponseEntity modificar(@RequestBody MaterialJson material) {
        if (material != null) {
            if ((material.getNombre() != null) || (material.getDescripcion() != null)) {
                int mval = Integer.parseInt(material.getId());
                Material m = new Material();
                m.setId(mval);
                m.setNombre(material.getNombre());
                m.setDescripcion(material.getDescripcion());
                materialRepositorio.save(m);
                MensajeJson msg = new MensajeJson();
                msg.setMsg("ok");
                return ResponseEntity.ok(msg);

            }
        }
        MensajeJson msg = new MensajeJson();
        msg.setMsg("no");
        return new ResponseEntity(msg, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/consulta")
    public ResponseEntity consulta(@RequestBody MaterialJson material) {
        if (material != null) {
            if (material.getId() != null) {
                int mval = Integer.parseInt(material.getId());

                Material m = materialRepositorio.findById(mval);
                MaterialJson ma = new MaterialJson();
                ma.setNombre(m.getNombre());
                ma.setDescripcion(m.getDescripcion());
                ma.setId("" + m.getId());

                return ResponseEntity.ok(ma);

            }
        }
        MensajeJson msg = new MensajeJson();
        msg.setMsg("no");
        return new ResponseEntity(msg, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/asignar")
    public ResponseEntity asignar(@RequestBody PuntoReciclajeJson puntoreciclaje) {

        if (puntoreciclaje != null) {
            if (puntoreciclaje.getId() != null) {
                PuntoReciclaje punto = new PuntoReciclaje();
                int idp = Integer.parseInt(puntoreciclaje.getId());
                punto.setId(idp);
                ArrayList<String> materiales = puntoreciclaje.getMateriales();

                for (String x : materiales) {
                    int mval = Integer.parseInt(x);
                    Material m = new Material() ;
                    m.setId(mval);
                    PuntoMaterial pm = new PuntoMaterial();
                    pm.setIdPunto(punto);
                    pm.setIdMaterial(m);
                    System.out.println(x);
                    pm = puntoMaterialRepositorio.save(pm);

                }

                MensajeJson msg = new MensajeJson();
                msg.setMsg("ok");
                return ResponseEntity.ok(msg);
            }
        }
        MensajeJson msg = new MensajeJson();
        msg.setMsg("no");
        return new ResponseEntity(msg, HttpStatus.BAD_REQUEST);

    }

    @PostMapping("/desasignar")
    public ResponseEntity desasignar(@RequestBody PuntoMaterialJson puntoMaterial) {

        if (puntoMaterial != null) {
            if ((puntoMaterial.getIdpunto() != null) && (puntoMaterial.getIdmaterial()) != null) {

                PuntoReciclaje punto = new PuntoReciclaje();
                Material material = new Material();
                int idp = Integer.parseInt(puntoMaterial.getIdpunto());
                int idm = Integer.parseInt(puntoMaterial.getIdmaterial());
                punto.setId(idp);
                material.setId(idm);

                PuntoMaterial pm = puntoMaterialRepositorio.findByIdMaterialAndIdPunto(material, punto);

                if (pm != null) {
                    if (pm.getId() != null) {
                        puntoMaterialRepositorio.delete(pm);
                        MensajeJson msg = new MensajeJson();
                        msg.setMsg("ok");
                        return ResponseEntity.ok(msg);
                    }
                }

            }
        }
        MensajeJson msg = new MensajeJson();
        msg.setMsg("no");
        return new ResponseEntity(msg, HttpStatus.BAD_REQUEST);

    }

    @PostMapping("/porpunto")
    public ResponseEntity porpunto(@RequestBody PuntoReciclajeJson puntoreciclaje) {

        if (puntoreciclaje != null) {
            if (puntoreciclaje.getId() != null) {
                int idp = Integer.parseInt(puntoreciclaje.getId());

                ArrayList<PuntoMaterial> ptmat;
                PuntoReciclaje prc = new PuntoReciclaje();
                prc.setId(idp);
                ptmat = (ArrayList<PuntoMaterial>) puntoMaterialRepositorio.findByIdPunto(prc);

                ArrayList<MaterialJson> lista = new ArrayList<MaterialJson>();
                for (PuntoMaterial x : ptmat) {

                    Material m = x.getIdMaterial();
                    MaterialJson mj = new MaterialJson();
                    mj.setNombre(m.getNombre());
                    mj.setId("" + m.getId());
                    mj.setDescripcion(m.getDescripcion());
                    lista.add(mj);

                }
                return ResponseEntity.ok(lista);
            }
        }

        return new ResponseEntity("error no valido", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/listarporanadir")
    public ResponseEntity listarañadirpunto(@RequestBody PuntoReciclajeJson puntoreciclaje) {

        if (puntoreciclaje != null) {
            if (puntoreciclaje.getId() != null) {
                int idp = Integer.parseInt(puntoreciclaje.getId());

                ArrayList<PuntoMaterial> ptmat;
                PuntoReciclaje prc = new PuntoReciclaje();
                prc.setId(idp);
                ptmat = (ArrayList<PuntoMaterial>) puntoMaterialRepositorio.findByIdPunto(prc);

                ArrayList<Material> materiales = (ArrayList<Material>) materialRepositorio.findAll();
                materiales.removeAll(ptmat);

                ListaJson<MaterialJson> l = new ListaJson<MaterialJson>();
                ArrayList<MaterialJson> arr = new ArrayList<MaterialJson>();
                for (Material m : materiales) {
                    MaterialJson ma = new MaterialJson();
                    ma.setId("" + m.getId());
                    ma.setNombre(m.getNombre());
                    ma.setDescripcion(m.getDescripcion());
                    arr.add(ma);
                }
                l.setLista(arr);
                return ResponseEntity.ok(l);
            }
        }
        MensajeJson msg = new MensajeJson();
        msg.setMsg("no");
        return ResponseEntity.ok(msg);
    }

}

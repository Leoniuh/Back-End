package com.proyectoap.elias.Controller;

import com.proyectoap.elias.Dto.DtoProyecto;
import com.proyectoap.elias.Entity.Proyecto;
import com.proyectoap.elias.Security.Controller.Mensaje;
import com.proyectoap.elias.Service.SProyecto;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("proyecto")
@CrossOrigin(origins = {"https://proyecto-frontend-elias.web.app","http://localhost:4200"})
public class CProyecto {
    @Autowired
    SProyecto sProyecto;

    @GetMapping("/lista")
    public ResponseEntity<List<Proyecto>> list() {
        List<Proyecto> list = sProyecto.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody DtoProyecto dtoProyecto) {
        if (StringUtils.isBlank(dtoProyecto.getNombre())) {
            return new ResponseEntity(new Mensaje("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        }
        if (sProyecto.existsByNombre(dtoProyecto.getNombre())) {
            return new ResponseEntity(new Mensaje("El proyecto que intenta añadir ya existe"), HttpStatus.BAD_REQUEST);
        }

        Proyecto pro = new Proyecto(dtoProyecto.getNombre(), dtoProyecto.getDescripcion());
        sProyecto.save(pro);

        return new ResponseEntity(new Mensaje("El proyecto ah sido agregado"), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody DtoProyecto dtoProyecto) {
        //Validación de existencia del ID
        if (!sProyecto.existsById(id)) {
            return new ResponseEntity(new Mensaje("El id no existe"), HttpStatus.BAD_REQUEST);
        }
        //Comparación de los nombres de los Proyectos
        if (sProyecto.existsByNombre(dtoProyecto.getNombre()) && sProyecto.getByNombre(dtoProyecto.getNombre()).get().getId() != id) {
            return new ResponseEntity(new Mensaje("Ese proyecto ya existe"), HttpStatus.BAD_REQUEST);
        }
        //Los campos no pueden estar vacíos
        if (StringUtils.isBlank(dtoProyecto.getNombre())) {
            return new ResponseEntity(new Mensaje("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        }

        Proyecto pro = sProyecto.getOne(id).get();
        pro.setNombre(dtoProyecto.getNombre());
        pro.setDescripcion((dtoProyecto.getDescripcion()));

        sProyecto.save(pro);
        return new ResponseEntity(new Mensaje("El proyecto ah sido actualizado"), HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Proyecto> getById(@PathVariable("id") int id) {
        if (!sProyecto.existsById(id)) {
            return new ResponseEntity(new Mensaje("Id no existente"), HttpStatus.NOT_FOUND);
        }

        Proyecto pro = sProyecto.getOne(id).get();
        return new ResponseEntity(pro, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        if (!sProyecto.existsById(id)) {
            return new ResponseEntity(new Mensaje("Id no existente"), HttpStatus.NOT_FOUND);
        }
        sProyecto.delete(id);
        return new ResponseEntity(new Mensaje("El proyecto seleccionado ah sido eliminado"), HttpStatus.OK);
    }
}

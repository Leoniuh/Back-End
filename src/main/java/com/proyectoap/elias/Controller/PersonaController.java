package com.proyectoap.elias.Controller;

import com.proyectoap.elias.Dto.DtoPersona;
import com.proyectoap.elias.Entity.Persona;
import com.proyectoap.elias.Security.Controller.Mensaje;
import com.proyectoap.elias.Service.ImpPersonaService;
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
@RequestMapping("personas")
@CrossOrigin(origins = {"http://localhost:4200"})
public class PersonaController {
    @Autowired
    ImpPersonaService impPersonaService;
    
    @GetMapping("/lista")
    public ResponseEntity<List<Persona>> list(){
        List<Persona> list = impPersonaService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }
    
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody DtoPersona dtoPersona){
        if(StringUtils.isBlank(dtoPersona.getNombre()))
            return new ResponseEntity(new Mensaje("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(dtoPersona.getApellido()))
            return new ResponseEntity(new Mensaje("El apellido es obligatorio"), HttpStatus.BAD_REQUEST);
        if(impPersonaService.existsByNombre(dtoPersona.getNombre()))
            return new ResponseEntity(new Mensaje("Esa persona ya existe"), HttpStatus.BAD_REQUEST);
        
        Persona persona = new Persona(dtoPersona.getNombre(), dtoPersona.getApellido(), dtoPersona.getDescripcion());
        impPersonaService.save(persona);
        
        return new ResponseEntity(new Mensaje("Persona agregada"), HttpStatus.OK);
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody DtoPersona dtoPersona){
        //Validación de existencia del ID
        if(!impPersonaService.existsById(id))
            return new ResponseEntity(new Mensaje("El id no existe"), HttpStatus.BAD_REQUEST);
        //Comparación de los nombres de las personas
        if(impPersonaService.existsByNombre(dtoPersona.getNombre()) && impPersonaService.getByNombre(dtoPersona.getNombre()).get().getId() != id)
            return new ResponseEntity(new Mensaje("Esa Persona ya existe"), HttpStatus.BAD_REQUEST);
        if(impPersonaService.existsByApellido(dtoPersona.getApellido()) && impPersonaService.getByApellido(dtoPersona.getApellido()).get().getId() != id)
            return new ResponseEntity(new Mensaje("El apellido es obligatorio"), HttpStatus.BAD_REQUEST);
        //Los campos no pueden estar vacíos
        if(StringUtils.isBlank(dtoPersona.getNombre()))
            return new ResponseEntity(new Mensaje("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(dtoPersona.getApellido()))
            return new ResponseEntity(new Mensaje("El apellido es obligatorio"), HttpStatus.BAD_REQUEST);
        
        Persona persona = impPersonaService.getOne(id).get();
        persona.setNombre(dtoPersona.getNombre());
        persona.setApellido(dtoPersona.getApellido());
        persona.setDescripcion((dtoPersona.getDescripcion()));
        persona.setImg(dtoPersona.getImg());
        
        impPersonaService.save(persona);
        return new ResponseEntity(new Mensaje("Persona actualizada"), HttpStatus.OK);
    }
    
    @GetMapping("/detail/{id}")
    public ResponseEntity<Persona> getById(@PathVariable("id") int id){
        if(!impPersonaService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        
        Persona persona = impPersonaService.getOne(id).get();
        return new ResponseEntity(persona, HttpStatus.OK);
    }
    
     @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        if (!impPersonaService.existsById(id)) {
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        }
        impPersonaService.delete(id);
        return new ResponseEntity(new Mensaje("La persona seleccionada ah sido eliminada"), HttpStatus.OK);
    }
}

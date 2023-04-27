package com.proyectoap.elias.Interface;

import com.proyectoap.elias.Entity.Persona;
import java.util.List;


public interface IPersonaService {
   //Traer lista de personas
    public List<Persona> getPersona();
    
    //Guardar objeto tipo persona
    public void savePersona(Persona persona);
    
    //Eliminar objeto tipo persona
    public void deletePersona(Long id);
    
    //Buscr personas mediante id
    public Persona findPersona(Long id);
}
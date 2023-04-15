package com.proyecto.elias.Interface;

import com.proyecto.elias.Entity.Persona;
import java.util.List;

public interface IPersonaService {
    //Traer una lista de persona
    public List<Persona> getPersona();
    
    //Guardar un objeto de tipo persona
    public void savePersona(Persona persona);
    
    //Eliminar un objeto buscandolo por ID
    public void deletePersona(Long id);
    
    //Buscar personas mediante ID
    public Persona findPersona(Long id);
    
}

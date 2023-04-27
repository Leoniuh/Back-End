package com.proyectoap.elias.Security.Repository;

import com.proyectoap.elias.Security.Entity.Rol;
import com.proyectoap.elias.Security.Enums.RolNombre;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface iRolRepository extends JpaRepository<Rol, Integer>{
    Optional<Rol> findByRolNombre(RolNombre rolNombre);
}

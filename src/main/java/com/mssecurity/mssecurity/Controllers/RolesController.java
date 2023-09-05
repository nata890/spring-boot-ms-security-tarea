package com.mssecurity.mssecurity.Controllers;

import com.mssecurity.mssecurity.Models.Role;
import com.mssecurity.mssecurity.Repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/roles")
public class RolesController {
    @Autowired
    private RoleRepository theRoleRepository;

    //metodo para listar
    @GetMapping("")
    public List<Role> index(){
        return this.theRoleRepository.findAll();
    }

    //metodo para almacenar
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Role store(@RequestBody Role newRole){
        return this.theRoleRepository.save(newRole);
    }

    //metodo para ver uno
    @GetMapping("{id}")
    public Role show(@PathVariable String id){
        Role theRole=this.theRoleRepository
                .findById(id)
                .orElse(null);
        return theRole;
    }

     //metodo para la actualizacion
    @PutMapping("{id}")
    public Role update(@PathVariable String id,@RequestBody Role theNewRole){
        Role theActualRole=this.theRoleRepository
                .findById(id)
                .orElse(null);
        if (theActualRole!=null){
            theActualRole.setName(theNewRole.getName());
            theActualRole.setDescription(theNewRole.getDescription());
            return this.theRoleRepository.save(theActualRole);
        }else{
            return null;
        }
    }

    //metodo de eliminacion
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void destroy(@PathVariable String id){
        Role theRole=this.theRoleRepository
                .findById(id)
                .orElse(null);
        if (theRole!=null){
            this.theRoleRepository.delete(theRole);
        }
    }

}

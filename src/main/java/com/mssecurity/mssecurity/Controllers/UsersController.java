package com.mssecurity.mssecurity.Controllers;

import com.mssecurity.mssecurity.Models.Role;
import com.mssecurity.mssecurity.Models.User;
import com.mssecurity.mssecurity.Repositories.RoleRepository;
import com.mssecurity.mssecurity.Repositories.UserRepository;

import com.mssecurity.mssecurity.Services.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/users")

public class UsersController {

    @Autowired
    //inyeccion de dependencias (dependo de una interfaz para actuar)
    private UserRepository theUserRepository;

    @Autowired
    private RoleRepository theRoleRepository;

    @Autowired
    private EncryptionService encryptionService;


    //metodo para listar
    @GetMapping("")
    public List<User> index(){
        //el controlador se esta conectando con el repositorio
        // finAll --> me lista todos los usuarios
        return this.theUserRepository.findAll();
    }

    //metodo para crear
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public User store(@RequestBody User newUser){
        newUser.setPassword(encryptionService.convertirSHA256(newUser.getPassword()));
        return this.theUserRepository.save(newUser);
    }

    //metodo para ver uno
    @GetMapping("{id}")
    public User show(@PathVariable String id){
        User theUser=this.theUserRepository
                .findById(id)
                .orElse(null);
        return theUser;
    }

    //metodo para la actualizacion
    @PutMapping("{id}")
    public User update(@PathVariable String id,@RequestBody User theNewUser){
        User theActualUser=this.theUserRepository
                .findById(id)
                .orElse(null);
        if (theActualUser!=null){
            theActualUser.setName(theNewUser.getName());
            theActualUser.setEmail(theNewUser.getEmail());
            theActualUser.setPassword(encryptionService.convertirSHA256(theNewUser.getPassword()));
            return this.theUserRepository.save(theActualUser);
        }else{
            return null;
        }
    }

    //metodo de eliminacion
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void destroy(@PathVariable String id){
        User theUser=this.theUserRepository
                .findById(id)
                .orElse(null);
        if (theUser!=null){
            this.theUserRepository.delete(theUser);
        }
    }

    //ruta para agregar un rol al usuario
    @PutMapping ("{user_id}/role/{role_id}")
    public User matchUserRole(@PathVariable String user_id, @PathVariable String role_id){
        User theActualUser=this.theUserRepository
                .findById(user_id)
                .orElse(null);
        Role theActualRole= this.theRoleRepository
                .findById(role_id)
                .orElse(null);

        if (theActualUser!=null && theActualRole!= null){
            theActualUser.setRole(theActualRole);
            return this.theUserRepository.save(theActualUser);
        }else{
            return null;
        }
    }

    @PutMapping ("{user_id}/role")
    public User unMatchUserRole(@PathVariable String user_id){
        User theActualUser=this.theUserRepository
                .findById(user_id)
                .orElse(null);

        if (theActualUser!=null ){
            theActualUser.setRole(null);
            return this.theUserRepository.save(theActualUser);
        }else{
            return null;
        }
    }



}

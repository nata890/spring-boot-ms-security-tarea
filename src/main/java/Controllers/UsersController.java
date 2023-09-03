package Controllers;

import Models.User;
import Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/users")

public class UsersController {

    @Autowired
    //inyeccion de dependencias (dependo de una interfaz para actuar)
    private UserRepository theUserRepository;

    //metodo para listar
    @GetMapping("")
    public List<User> index(){
        //el controlador se esta conectando con el repositorio
        // finAll --> me lista todos los usuarios
        return this.theUserRepository.findAll();
    }

    //metodo para almacenar
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public User store(@RequestBody User newUser){
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
            theActualUser.setPassword(theNewUser.getPassword());
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
}

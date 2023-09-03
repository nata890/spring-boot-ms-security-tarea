package Controllers;

import Models.Role;
import Repositories.RoleRepository;
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

}

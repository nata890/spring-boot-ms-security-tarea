package com.mssecurity.mssecurity.Controllers;

import com.mssecurity.mssecurity.Models.User;
import com.mssecurity.mssecurity.Repositories.UserRepository;
import com.mssecurity.mssecurity.Services.EncryptionService;
import com.mssecurity.mssecurity.Services.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/security")

public class SecurityController {

    //Metodo login
    @Autowired
    private UserRepository theUserRepository;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private EncryptionService encryptionService;

    @PostMapping("login")
    // en caso de que el login no sea exitoso httpServletResponse es para mandar el 401
    public String login(@RequestBody User theUser, final HttpServletResponse response)  throws IOException {
        String token="";
        User actualUser=this.theUserRepository.getUserByEmail(theUser.getEmail());
        if(actualUser!=null && actualUser.getPassword().equals(encryptionService.convertirSHA256(theUser.getPassword()))){
            //generar token
            //crear un objeto
            token= jwtService.generateToken(actualUser);
        }else {
            //manejar el problema se encarga de levantar el 401
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
        return token;
    }
    //metodo logout
    //metodo reset pass




}

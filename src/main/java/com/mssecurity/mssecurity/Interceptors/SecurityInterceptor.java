package com.mssecurity.mssecurity.Interceptors;

import com.mssecurity.mssecurity.Services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SecurityInterceptor implements HandlerInterceptor {

    //final es una constante, no se puede cambiar
    private static final String BEARER_PREFIX = "Bearer ";

    @Autowired
    private JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler)
            throws Exception {

        boolean success=true;

        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null &&
                //startsWith --> inicia con, el prefijo de
                authorizationHeader.startsWith(BEARER_PREFIX)) {

            //que corte la cadena del bearer en adelante y nos quedamos con el token
            String token = authorizationHeader.substring(BEARER_PREFIX.length());
// Verifica el token aquí, por ejemplo, con un servicio de autenticación
// Si el token es válido, puedes permitir que la solicitud continúe
// Si no es válido, puedes rechazar la solicitud o realizar otra acción
// apropiada.
            System.out.println("Bearer Token: " + token);
            success=jwtService.validateToken(token);
        } else {
            success=false;
        }
        return success;
    }
}

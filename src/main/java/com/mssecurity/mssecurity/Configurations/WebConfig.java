package com.mssecurity.mssecurity.Configurations;

import com.mssecurity.mssecurity.Interceptors.SecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    //hacer mas uso de las inyecciones de dependencias
    @Autowired
    private SecurityInterceptor securityInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(securityInterceptor)
                //se active cuando cualquier ruta (privado)
                .addPathPatterns("/api/**")
                //se excluya t0do lo de security por lo cual es publico
                .excludePathPatterns("/api/public/**");; // Asegúrate de que las rutas sean las correctas
    }
}

package com.backend.proyectofinal.login;

import com.backend.proyectofinal.entity.Usuario;
import com.backend.proyectofinal.repository.impl.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class DataLoader implements ApplicationRunner {
    private IUsuarioRepository usuarioRepository;


    @Autowired
    public DataLoader(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String contrasenia = bCryptPasswordEncoder.encode("contrasenia");
        usuarioRepository.save(new Usuario("Administrador", "Admin", "admin@clinica.com", contrasenia, UsuarioRoles
                .ADMIN));

    }
}

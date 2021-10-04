package com.backend.proyectofinal.entity;

import com.backend.proyectofinal.login.UsuarioRoles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "usuarios")

public class Usuario implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_sequence")
    @SequenceGenerator(name = "usuario_sequence", sequenceName = "usuario_sequence")
    private Long id;

    private String nombre;

    @Column(unique = true)
    @NotNull
    @Size(min = 2, max = 10, message = "El usuario debe contener entre 2 y 10 caracteres")
    private String nombreUsuario;
    private String mail;
    @NotNull
    //@Size(min = 2, max = 10, message = "La contraseña debe contener entre 2 y 10 caracteres")
    private String contrasenia;

    @Enumerated(EnumType.STRING)
    private UsuarioRoles usuarioRoles;


    public Usuario(String nombre, String nombreUsuario, String mail, String contrasenia, UsuarioRoles usuarioRoles) {
        this.nombre = nombre;
        this.nombreUsuario = nombreUsuario;
        this.mail = mail;
        this.contrasenia = contrasenia;
        this.usuarioRoles = usuarioRoles;
    }

    public Usuario() {
    }


    public Long getId() {
        return id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public UsuarioRoles getUsuarioRoles() {
        return usuarioRoles;
    }

    public void setUsuarioRoles(UsuarioRoles usuarioRoles) {
        this.usuarioRoles = usuarioRoles;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public String toString() {
        return "Usuario: " +
                "Id: " + id + "\n" +
                "Nombre de usuario: " + nombreUsuario + "\n" +
                "Contraseña: " + contrasenia;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(usuarioRoles.name());
        return Collections.singletonList(grantedAuthority);
    }

    @Override
    public String getPassword() {
        return contrasenia;
    }

    @Override
    public String getUsername() {
        return nombreUsuario;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

package com.backend.proyectofinal.service;



    import com.backend.proyectofinal.entity.Usuario;
    import com.backend.proyectofinal.repository.impl.IUsuarioRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.core.userdetails.UserDetailsService;
    import org.springframework.security.core.userdetails.UsernameNotFoundException;
    import org.springframework.stereotype.Service;


    import java.util.List;
    import java.util.Optional;
    import java.util.logging.Logger;

    @Service
    public class UsuarioService implements UserDetailsService {



        private IUsuarioRepository usuarioRepository;
        private Logger logger = Logger.getLogger(String.valueOf(UsuarioService.class));




        @Autowired
        public UsuarioService(IUsuarioRepository usuarioRepository) {
            this.usuarioRepository = usuarioRepository;
        }



        public Optional<Usuario> findUsuarioById(Long id){
            return usuarioRepository.findById(id);
        }

        public List<Usuario> findAllUsuarios() {
            return usuarioRepository.findAll();
        }


        public Optional<Usuario> findUsuarioByName(String nombreUsuario) {
            return usuarioRepository.findUsuarioByName(nombreUsuario);
        }



        public Usuario save(Usuario usuario) {
                return usuarioRepository.save(usuario);
            }


        public String update(Usuario usuario) {
            Usuario usuarioTmp = usuarioRepository.findById(usuario.getId()).get();
            usuarioTmp.setNombreUsuario(usuario.getNombreUsuario());
            usuarioTmp.setContrasenia(usuario.getContrasenia());
            usuarioRepository.save(usuarioTmp);
            return "Usuario con ID: " + usuario.getId() + " ha sido actualizado";
        }


        @Override
        public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
            return usuarioRepository.findUsuarioByName(nombreUsuario).orElseThrow((() -> new UsernameNotFoundException(
                    "Usuario no encontrado")));
        }


    }

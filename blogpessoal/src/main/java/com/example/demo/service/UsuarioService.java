package com.example.demo.service;

import java.nio.charset.Charset;
import java.util.Optional;
import org.apache.commons.codec.binary.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.UserLogin;
import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	public Optional<Usuario> CadastrarUsuario(Usuario usuario) {
		
		if (usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent())
			return Optional.empty();
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String senhaEncoder = encoder.encode(usuario.getSenha());
		usuario.setSenha(senhaEncoder); //iremos encriptar a senha que entrar
		
		return Optional.of(usuarioRepository.save(usuario));  // iremos SALVAR a senha que foi encriptada
	}	

	// == tudo que se refere a logar ==/
	//iremos retornar para o usuario o nome, usuario, senha e token
	
	public Optional<UserLogin> Logar(Optional<UserLogin> user){
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Optional<Usuario> usuario = usuarioRepository.findByUsuario(user.get().getUsuario()); 
		
		if(usuario.isPresent()) { //se houver algo no usuario, ira comparar a senha do objeto
			
			if(encoder.matches(user.get().getSenha(), usuario.get().getSenha()))	{ //com a senha que o usuario digitou
				
				String auth = user.get().getUsuario() + ":" + user.get().getSenha();  //se a for for igual retorna true
				byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCIII"))); //formato de byte que queremos
				String authHeader = "Basic " + new String(encodedAuth);
				
						
				user.get().setNome(usuario.get().getNome());
				user.get().setSenha(usuario.get().getSenha());
				
				return user;
			}
			
		} return null;
	}
	
	
	
}

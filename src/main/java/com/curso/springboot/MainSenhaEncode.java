package com.curso.springboot;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class MainSenhaEncode {

	public static void main(String[] args) {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String resultadoEncoder = encoder.encode("admin");
		System.out.println(resultadoEncoder);
	}

}

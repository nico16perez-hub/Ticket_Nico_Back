package com.slmas.Sl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		// Obtener la hora actual
		LocalDateTime now = LocalDateTime.now();

		// Formatear la hora actual
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String formattedDateTime = now.format(formatter);

		// Imprimir la hora actual en la consola
		System.out.println("La hora actual es: " + formattedDateTime);

		PasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println(encoder.encode("$sanluis13tv$"));
	}

}

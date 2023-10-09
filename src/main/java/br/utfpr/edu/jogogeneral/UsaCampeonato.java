package br.utfpr.edu.jogogeneral;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
@SpringBootApplication
public class UsaCampeonato {
	public static void main(String[] args) {

		// Inicialize o aplicativo Spring Boot
		SpringApplication.run(UsaCampeonato.class, args);

		menu();
	}

	public static void  menu () {

		String url = "http://localhost:8080/";

		// Verifica o sistema operacional
		String osName = System.getProperty("os.name").toLowerCase();

		try {
			Process process;
			if (osName.contains("win")) {
				// Sistema Windows
				process = Runtime.getRuntime().exec("cmd /c start " + url);
			} else if (osName.contains("nix") || osName.contains("nux") || osName.contains("mac")) {
				// Sistemas Linux e macOS
				process = Runtime.getRuntime().exec("xdg-open " + url);
			} else {
				// Outros sistemas operacionais (trate de acordo com suas necessidades)
				System.out.println("Sistema operacional não suportado.");
				return;
			}

			// Espere o processo terminar (opcional)
			int exitCode = process.waitFor();
			if (exitCode != 0) {
				System.out.println("Erro ao abrir o navegador.");
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}

package br.com.sistema.web.util.random;

import br.com.sistema.repository.modelo.Autor;

public class Random {

	static String vogais = "aeiou";
	static String consoantes = "qwrtypsdfghjklzxcvbnm";

	public static Autor gerarAutor() {
		Autor autor = new Autor();

		autor.setNome(Random.gerarNomeCompleto());
		autor.setEmail(Random.gerarPalavra() + "@" + Random.gerarPalavra() + "." + Random.gerarSilaba());

		return autor;
	}

	public static String gerarNomeCompleto() {
		int quantidadeMax = 6;
		int quantidadeMin = 3;

		int quantidadeQueTera = (int) (quantidadeMin + Math.random() * quantidadeMax);

		StringBuilder nome = new StringBuilder();

		for (int i = quantidadeMin; i <= quantidadeQueTera; i++) {
			String palavra = gerarPalavra();
			String primeiraLetra = String.valueOf(palavra.charAt(0));
			palavra = palavra.replaceFirst(primeiraLetra, primeiraLetra.toUpperCase());

			if (!nome.toString().isEmpty())
				nome.append(" ");

			nome.append(palavra);
		}

		return nome.toString();
	}

	public static String gerarPalavra() {
		int quantidadeSilabasMax = 6;
		int quantidadeSilabasMin = 1;

		int quantidadeQueTera = (int) (quantidadeSilabasMin + Math.random() * quantidadeSilabasMax);

		StringBuilder palavra = new StringBuilder();

		for (int i = quantidadeSilabasMin; i <= quantidadeQueTera; i++) {
			palavra.append(gerarSilaba());
		}
		return palavra.toString();
	}

	public static char gerarVogal() {
		return vogais.charAt((int) (Math.random() * vogais.length()));
	}

	public static char gerarConsoante() {
		return consoantes.charAt((int) (Math.random() * consoantes.length()));
	}

	public static String gerarSilaba() {
		return "" + gerarConsoante() + gerarVogal();
	}

}

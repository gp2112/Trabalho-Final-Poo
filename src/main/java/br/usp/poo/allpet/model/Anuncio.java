package br.usp.poo.allpet.model;

import java.util.Base64;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import br.usp.poo.allpet.enums.Animal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Anuncio {
	//entidade referente às tabelas do banco de dados
	//com as informaçãoes de cada coluna
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 60, nullable = false)
	private String titulo;
	
	@Column(length = 1000, nullable = false)
	private String descricao;
	
	@Column(length = 120, nullable = false)
	private String endereco;
	
	@Column(length = 100, nullable = false)
	private String cidade;
	
	@Column(length = 30, nullable = false)
	private String telefone;
	
	@Column(length = 64, nullable = false)
	private String email;

	@Column(nullable = false)
	private Animal animal;
	
	@Column(nullable = false)
	private Short usuario;
	
	//Salva a imagem como um array de bytes
	@Column(columnDefinition = "MEDIUMBLOB", nullable=false)
	private byte[] bytes;
	
	//Para obter a imagem em si usa-se esse método que decodifica os bytes e retorna uma string em base64
	public String getImagem() {
		//Obtém a string da imagem apos decodificar para base64
        byte[] imgBytes = Base64.getEncoder().encode (this.getBytes());
        String img = new String (imgBytes);
        //Salva como string e adiciona o cabeçalho necessário para reconhecer que essa string é arquivo
		img = "data:image/png;base64," + img;
		
		return img;
	}
	
}

package com.indracompany.treinamento.model.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClienteDTO {
	
	private String nome;
	
	private String email;
	

}

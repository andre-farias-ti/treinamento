package com.indracompany.treinamento.model.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.indracompany.treinamento.exception.AplicacaoException;
import com.indracompany.treinamento.exception.ExceptionValidacoes;
import com.indracompany.treinamento.model.dto.ClienteDTO;
import com.indracompany.treinamento.model.entity.Cliente;
import com.indracompany.treinamento.model.repository.ClienteRepository;
import com.indracompany.treinamento.util.CpfUtil;

@Service
public class ClienteService extends GenericCrudService<Cliente, Long, ClienteRepository>{

	public ClienteDTO buscarClientePorCPF(String cpf) {
		boolean cpfValido = cpf != null && CpfUtil.validaCPF(cpf);
		
		if(!cpfValido) {
			throw new AplicacaoException(ExceptionValidacoes.ERRO_CPF_INVALIDO);
		}
		
		try {
			Cliente c = repository.findByCpf(cpf);
			
			if(c == null) {
				throw new AplicacaoException(ExceptionValidacoes.ALERTA_NENHUM_REGISTRO_ENCONTRADO);
			}
			
			return ClienteDTO.builder()
					.nome(c.getNome())
					.email(c.getEmail())
					.build();
			
		}catch (Exception e) {

			throw new AplicacaoException(ExceptionValidacoes.ALERTA_VARIOS_REGISTRO_ENCONTRADO);
		}
		
	}
	
	public List<ClienteDTO> buscarClientePorNome(String nome) {
		boolean nomeValido = nome != null;
		
		if(!nomeValido) {
			throw new AplicacaoException(ExceptionValidacoes.ERRO_CAMPO_OBRIGATORIO);
		}
		
		if(nome.length() < 3) {
			throw new AplicacaoException(ExceptionValidacoes.ALERTA_STRING_MENOR_QUE_TRES);
		}
		
		try {
			List<Cliente> c = repository.findByNomeContaining(nome);
			
			if(c == null) {
				throw new AplicacaoException(ExceptionValidacoes.ALERTA_NENHUM_REGISTRO_ENCONTRADO);
			}
			
			List<ClienteDTO> listaCliente = c.stream()
					.map(p -> ClienteDTO.builder().nome(p.getNome()).email(p.getEmail()).build())
					.collect(Collectors.toList());
			
			return listaCliente;
			
		}catch (Exception e) {

			throw new AplicacaoException(ExceptionValidacoes.ALERTA_VARIOS_REGISTRO_ENCONTRADO);
		}
		
	}
	  
}

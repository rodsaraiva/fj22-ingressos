package br.com.caelum.ingresso.validacao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import br.com.caelum.ingresso.model.Sessao;

public class GerenciadorDeSessao {

	private List<Sessao> sessoesDaSala;
	
	public GerenciadorDeSessao(List<Sessao> sessoesDaSala) {
		this.sessoesDaSala = sessoesDaSala;
	}
	
	private boolean horarioIsConflitante (Sessao sessaoNova, Sessao sessaoExistente) {
		
		LocalDate hoje = LocalDate.now();
		
		LocalDateTime incicioSessaoNova = sessaoNova.getHorario().atDate(hoje);
		LocalDateTime terminoSessaoNova = incicioSessaoNova.plus(sessaoNova.getFilme().getDuracao());
				
		LocalDateTime incioSessaoExistente = sessaoExistente.getHorario().atDate(hoje);
		LocalDateTime terminoSessaoExistente = incioSessaoExistente.plus(sessaoExistente.getFilme().getDuracao()); 
		
		boolean novaTerminaAntes = terminoSessaoNova.isBefore(incioSessaoExistente);
		boolean novaComecaDepois = incioSessaoExistente.isAfter(terminoSessaoExistente);
		
		if(novaTerminaAntes || novaComecaDepois) {
			return false;
		}
		
		return true;

	}
	
	public boolean cabe(Sessao sessaoNova) {
		
		return this.sessoesDaSala.stream().noneMatch(sessaoExistente -> horarioIsConflitante(sessaoNova, sessaoExistente));
	}
	
}

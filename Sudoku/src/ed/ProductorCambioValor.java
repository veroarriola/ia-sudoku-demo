/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed;

/**
 *
 * @author blackzafiro
 */
public interface ProductorCambioValor {
	
	/**
	 * Registra un escucha interesado en ser notificado cuando un valor cambie.
	 * @param escucha 
	 */
	void registraEscuchaCambioValor(EscuchaCambioPrioridad escucha);
	
	/**
	 * Remueve a un escucha si estaba registrado.
	 * @param escucha
	 */
	void remueveEscuchaCambioValor(EscuchaCambioPrioridad escucha);
	
}

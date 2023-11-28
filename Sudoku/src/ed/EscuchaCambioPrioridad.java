/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed;

/**
 * TODO: renombrar para reflejar que también escucha a quien quiere ser removido.
 * @author blackzafiro
 */
public interface EscuchaCambioPrioridad<P extends Comparable<P>> {
	
	/**
	 * Avisa que ha cambiado la prioridad del elemento e indica cuál es.
	 * @param nueva nuevo valor de la prioridad.
	 */
	void prioridadModificada(P nueva);
        
        /**
         * Avisa que este elemento desea ser removido de la cola.
         */
        void remueveme();
}

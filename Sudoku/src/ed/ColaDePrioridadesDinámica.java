/*
 * Codigo utilizado para el curso de Estructuras de Datos.
 * Se permite consultarlo para fines didacticos en forma personal,
 * pero no esta permitido transferirlo resuelto a estudiantes actuales o potenciales.
 */
package ed;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Cola de prioridades implementada como un minheap, con operaciones especiales
 * para su uso en IA.
 *
 * @author blackzafiro
 */
public class ColaDePrioridadesDinámica<P extends Comparable<P>, E extends ProductorCambioValor>
	extends ColaDePrioridades<P, E> {

	/**
	 * Clase interna para almacenar un dato con su prioridad.
	 */
	protected class Nodo extends ColaDePrioridades.Nodo implements EscuchaCambioPrioridad<P> {

		public Nodo(P prioridad, E dato) {
			super(prioridad, dato);
			dato.registraEscuchaCambioValor(this);
		}

		public Nodo(P prioridad, E dato, Nodo padre) {
			super(prioridad, dato, padre);
			dato.registraEscuchaCambioValor(this);
		}

		@Override
		public void prioridadModificada(P nueva) {
			actualizaPrioridad(nueva);
		}

		@Override
		public void remueveme() {
			remueveme();
		}

	}

	@Override
	public void inserta(P prioridad, E dato) {
		if (estáVacía()) {
			raíz = últimoInsertado = new Nodo(prioridad, dato);
			return;
		}
		if (raíz.esHoja()) {
			raíz.hijoI = últimoInsertado = new Nodo(prioridad, dato, (Nodo) raíz);
			últimoInsertado.burbujeaArriba();
			return;
		}

		// Ya había datos
		if (últimoInsertado.esHijoIzquierdo()) {
			// Insertar a su derecha
			últimoInsertado.padre.hijoD = últimoInsertado = new Nodo(
				prioridad, dato, (Nodo) últimoInsertado.padre);
		} else if (últimoInsertado.esHijoDerecho()) {
			Nodo padre = (Nodo) encuentraSiguientePadre();
			padre.hijoI = últimoInsertado = new Nodo(prioridad, dato, padre);
		} else {
			System.err.print(últimoInsertado);
			throw new IllegalStateException("Inconsistencia en el estado de los nodos");
		}

		últimoInsertado.burbujeaArriba();
	}

	public static void main(String[] args) {
		ColaDePrioridades<Integer, String> cola = new ColaDePrioridades<>();
		cola.inserta(1, "Muy importante");
		cola.inserta(10, "Lo puedo dejar al último");
		cola.inserta(9, "Tampoco urge mucho");
		cola.inserta(1, "Tan importante como lo más");

		cola.imprime();

		while (!cola.estáVacía()) {
			System.out.println("Sacando: " + cola.remueve());
		}

	}
}

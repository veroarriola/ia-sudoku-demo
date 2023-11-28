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
 * @author blackzafiro
 */
public class ColaDePrioridades<P extends Comparable<P>, E> {
	
	/**
	 * Clase interna para almacenar un dato con su prioridad.
	 */
	protected class Nodo {
		protected P prioridad;
		protected E dato;
		protected Nodo hijoI;
		protected Nodo hijoD;
		protected Nodo padre;
		
		public Nodo(P prioridad, E dato) {
			this.prioridad = prioridad;
			this.dato = dato;
		}
		
		public Nodo(P prioridad, E dato, Nodo padre) {
			this.prioridad = prioridad;
			this.dato = dato;
			this.padre = padre;
		}
		
		/**
		 * Constructor copia.
		 * @param n nodo a copiar.
		 */
		public Nodo(Nodo n) {
			this(n.prioridad, n.dato, n.padre);
			hijoI = n.hijoI;
			hijoD = n.hijoD;
		}
		
		public void monticuliza() {
			if (esHoja()) return;
			if (hijoD == null) {
				// Sólo hay hijo izquierdo
				if (comparator.compare(prioridad, hijoI.prioridad) <= 0) {
					hijoI.intercambia(this);
					//hijoI.monticuliza();
					monticuliza();
				}
			} else {
				// Hay dos hijos, escoger el menor.
				Nodo min = this;
				if (comparator.compare(prioridad, hijoI.prioridad) <= 0) {
					min = hijoI;
				}
				if (comparator.compare(min.prioridad, hijoD.prioridad) <= 0) {
					min = hijoD;
				}
				if (min != this) {
					min.intercambia(this);
					//min.monticuliza();
					monticuliza();
				}
			}
		}
		
		/**
		 * Despaza al dato hacia la posición en el heap
		 * correspondiente a la nueva proridad.
		 */
		public void actualizaPrioridad(P nuevaPrioridad) {
			this.prioridad = nuevaPrioridad;
			if ((hijoI != null && comparator.compare(prioridad, hijoI.prioridad) <= 0) ||
					(hijoD != null && comparator.compare(prioridad, hijoD.prioridad) <= 0)) {
				monticuliza();
			} else {
				burbujeaArriba();
			}
		}
                
                /**
                 * Remueve este nodo de la cola.
                 */
                public void remueveme() {
                    throw new UnsupportedOperationException("TODO");
                }
		
		protected void burbujeaArriba() {
			// Subir hasta alcanzar su prioridad
			Nodo temp = this;
			/*
			if(temp == últimoInsertado) {
				if (temp.padre != null &&
					comparator.compare(
						temp.prioridad,
						temp.padre.prioridad) > 0) {
					últimoInsertado = temp.padre;
					temp.intercambia(temp.padre);
					//temp = temp.padre;
				}
			}*/
			while (temp.padre != null &&
					comparator.compare(
						temp.prioridad,
						temp.padre.prioridad) > 0) {
				temp.intercambia(temp.padre);
				//temp = temp.padre;
			}
		}
		
		/**
		 * Intercambia los datos almacenados en este nodo con los de
		 * <code>otro</code>.
		 * @param otro 
		 */
		/*
		public void intercambia(Nodo otro) {
			P pTemp = prioridad;
			this.prioridad = otro.prioridad;
			otro.prioridad = pTemp;
			
			E dTemp = dato;
			this.dato = otro.dato;
			otro.dato = dTemp;
		}*/
		/**
		 * Intercambia la posición de este nodo con la de <code>otro</code>.
		 * @param otro nodo con quien se realiza el intercambio.
		 */
		public void intercambia(Nodo otro) {
			if (this == otro) throw new IllegalArgumentException("¿Por qué quieres intercambiar a un nodo consigo mismo?");
			Nodo copiaTemp = new Nodo(this);
			boolean soyHijoIzquierdo = esHijoIzquierdo();
			boolean soyHijoDerecho = esHijoDerecho();
			
			// Padre
			if(otro.padre == this) {
				this.padre = otro;
			} else {
				padre = otro.padre;
				if(padre != null) {
					if (otro.esHijoIzquierdo()) {
						otro.padre.hijoI = this;
					} else if (otro.esHijoDerecho()) {
						otro.padre.hijoD = this;
					} // else es raíz.
				}
			}
			if (this == otro.hijoI) {
				hijoI = otro;
			} else {
				hijoI = otro.hijoI;
				if (hijoI != null) hijoI.padre = this;
			}
			if (this == otro.hijoD) {
				hijoD = otro;
			} else {
				hijoD = otro.hijoD;
				if (hijoD != null) hijoD.padre = this;
			}
			
			// Padre de otro
			if (copiaTemp.padre == otro) {
				otro.padre = this;
			} else {
				otro.padre = copiaTemp.padre;
				if (soyHijoIzquierdo) {
					otro.padre.hijoI = otro;
				} else if (soyHijoDerecho) {
					otro.padre.hijoD = otro;
				}  // else es raíz.
			}
			if (otro == copiaTemp.hijoI) {
				otro.hijoI = this;
			} else {
				otro.hijoI = copiaTemp.hijoI;
				if (otro.hijoI != null) otro.hijoI.padre = otro;
			}
			if (otro == copiaTemp.hijoD) {
				otro.hijoD = this;
			} else {
				otro.hijoD = copiaTemp.hijoD;
				if (otro.hijoD != null) otro.hijoD.padre = otro;
			}
			
			if (this == últimoInsertado) últimoInsertado = otro;
			else if (otro == últimoInsertado) últimoInsertado = this;
			if (this == raíz) raíz = otro;
			else if (otro == raíz) raíz = this;
		}
		
		/**
		 * Remueve a este nodo de su padre.
		 */
		public void orfanar() {
			if (padre != null) {
				if(this.esHijoIzquierdo()) {
					padre.hijoI = null;
				} else {
					padre.hijoD = null;
				}
				padre = null;
			}
		}
		
		/**
		 * Indica si este nodo no tiene hijos.
		 */
		public boolean esHoja() {
			return this.hijoI == null && this.hijoD == null;
		}
		
		/**
		 * Indica si este nodo es hijo derecho de otro nodo.
		 * Si no tiene padre regresa <code>false</code>.
		 * @return ¿hijo derecho?
		 */
		public boolean esHijoDerecho() {
			return padre != null ? this.padre.hijoD == this : false;
		}
		
		/**
		 * Indica si este nodo es hijo izquierdo de otro nodo.
		 * Si no tiene padre regresa <code>false</code>.
		 * @return ¿hijo izquierdo?
		 */
		public boolean esHijoIzquierdo() {
			return padre != null ? this.padre.hijoI == this : false;
		}
		
		public String simpleString() {
			return this.prioridad.toString() + ":" + this.dato;
		}
		@Override
		public String toString() {
			return simpleString() +
					"\t[I]:" + (hijoI == null ? "_" : hijoI.simpleString()) +
					"\t[D]:" + (hijoD == null ? "_" : hijoD.simpleString()) +
					"\t[P]:" + (padre == null ? "_" : padre.simpleString());
		}
	}
	
	/**
	 * Montículo mínimo.
	 * 
	 * Devuelve un número positivo si pr1 es menor que pr2, cero si son iguales
	 * y un número menor que cero si es mayor.
	 * @param pr1 Prioridad 1
	 * @param pr2 Prioirdad 2
	 * @return orden de prioridad
	 */
	private Comparator<P> comparator = (P pr1, P pr2) -> pr2.compareTo(pr1);
	protected Nodo raíz;
	protected Nodo últimoInsertado;
	
	public E mira() {
		if (raíz == null) return null;
		return raíz.dato;
	}
	
	public void inserta(P prioridad, E dato) {
		if(estáVacía()) {
			raíz = últimoInsertado = new Nodo(prioridad, dato);
			return;
		}
		if(raíz.esHoja()) {
			raíz.hijoI = últimoInsertado = new Nodo(prioridad, dato, raíz);
			últimoInsertado.burbujeaArriba();
			return;
		}
		
		// Ya había datos
		if (últimoInsertado.esHijoIzquierdo()) {
			// Insertar a su derecha
			últimoInsertado = últimoInsertado.padre.hijoD = new Nodo(
					prioridad, dato, últimoInsertado.padre);
		} else if (últimoInsertado.esHijoDerecho()) {
			Nodo padre = encuentraSiguientePadre();
			últimoInsertado = padre.hijoI = new Nodo(prioridad, dato, padre);
		} else {
			System.err.print(últimoInsertado);
			throw new
			IllegalStateException("Inconsistencia en el estado de los nodos");
		}
		
		últimoInsertado.burbujeaArriba();
	}
	
	/** 
	 * Remueve al elemento siguiente con la mayor prioridad.
	 * @return elemento con mayor prioridad, al frente de la cola.
	 */
	public E remueve() {
		E resultado = raíz.dato;
		if (raíz.esHoja()) {
			raíz = últimoInsertado = null;
			return resultado;
		}
		//print();
		raíz.intercambia(últimoInsertado); // actualiza solito a últimoInsertado
		//print();
		Nodo aRemover = últimoInsertado;
		últimoInsertado = encuentraInsertadoAnterior();
		aRemover.orfanar();
		raíz.monticuliza();
		//recuperaRaíz();
		
		//System.out.println("Recuperando la raíz -> " + raíz.toString());
		return resultado;
	}
	/*
	protected void recuperaRaíz() {
		while(raíz.padre != null) {
			//System.out.println("Recuperando la raíz -> " + raíz.toString());
			raíz = raíz.padre;
		}
	}*/
	
	/** 
	 * Dado el último nodo insertado, siendo éste un hijo derecho,
	 * calcula quien es el padre del nodo nuevo.
	 * Su complejidad debe ser a lo más 2log(n).
	 * @return 
	 */
	protected Nodo encuentraSiguientePadre() {
		Nodo temp = últimoInsertado;
		while(temp.esHijoDerecho()) {
			temp = temp.padre;
		}
		if (temp == raíz) {
			// El árbol ya está completo, toca iniciar nivel.
			while(temp.hijoI != null) {
				temp = temp.hijoI;
			}
		} else {
			temp = temp.padre;
			while(temp.hijoI != null) {
				temp = temp.hijoI;
			}
		}
		return temp;
	}
	
	/**
	 * Encuentra al penúltimo nodo que fue insertado, siempre que el árbol tenga
	 * más de un elemento.
	 * @return penúltimo insertado.
	 */
	private Nodo encuentraInsertadoAnterior() {
		if (últimoInsertado.esHijoDerecho()) {
			return últimoInsertado.padre.hijoI;
		} else {
			Nodo temp = últimoInsertado.padre;
			while(temp.esHijoIzquierdo()) {
				temp = temp.padre;
			}
			if (temp == raíz) {
				// Se removió el último nodo restante en el nivel.
				while(temp.hijoD != null) {
					temp = temp.hijoD;
				}
			} else {
				// System.out.println("Buscando >>> " + temp);
				temp = temp.padre.hijoI;
				while(temp.hijoD != null) {
					temp = temp.hijoD;
				}
			}
			return temp;
		}
	}
	
	public boolean estáVacía() {
		return raíz == null;
	}
	
	public void imprime() {
		Queue<Nodo> cola = new LinkedList<Nodo>();
		if (raíz == null) {
			System.out.println("Cola vacía");
			return;
		}
		System.out.println("Contenido de la cola:");
		cola.add(raíz);
		while(!cola.isEmpty()) {
			Nodo visitando = cola.remove();
			if (visitando.hijoI != null) cola.add(visitando.hijoI);
			if (visitando.hijoD != null) cola.add(visitando.hijoD);
			if (visitando == raíz) {
				System.out.println("\t<R>  " + visitando);
			} else if (visitando == últimoInsertado) {
				System.out.println("\t<UI> " + visitando);
			} else {
				System.out.println("\t     " + visitando);
			}
			System.out.flush();
		}
		System.out.println();
		System.out.flush();
	}
	
}

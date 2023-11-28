/*
 * Código utilizado para el curso de Estructuras de Datos.
 * Se permite consultarlo para fines didácticos en forma personal,
 * pero no esta permitido transferirlo resuelto a estudiantes actuales o potenciales.
 */
package sudoku;

import ed.ProductorCambioValor;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import ed.EscuchaCambioPrioridad;
import java.util.Iterator;
// import ed.ColaDePrioridadesDinámica;

/**
 * Variable que puede recibir valores dependiendo de los valores de sus vecinas.
 *
 * @author blackzafiro
 */
public class Casilla implements ProductorCambioValor {

    private EstadoValor origenValor;
    private Set<Integer> valoresPosibles = new TreeSet<>();
    private Set<Integer> valoresImposibles = new TreeSet<>();
    private List<EscuchaCambioPrioridad> escuchas = new LinkedList<>();

    /**
     * Crea una casilla con todos los valores posibles.
     */
    public Casilla() {
        origenValor = EstadoValor.SIN_ASIGNAR;
        for (int i = 1; i < 10; i++) {
            valoresPosibles.add(i);
        }
    }

    /**
     * Crea una variable copia del parámetro.
     *
     * @param otra variable que se desea copiar.
     */
    public Casilla(Casilla otra) {
        origenValor = otra.origenValor;
        valoresPosibles.addAll(otra.valoresPosibles);
        valoresImposibles.addAll(otra.valoresImposibles);
        escuchas.addAll(otra.escuchas); // OJO: ¿no querría clonar los escuchas?
    }

    /**
     * Indica que el valor de esta casilla es fijo.
     * 
     * @param valor desde el inicio del juego.
     */
    public void asignaInicial(int valor) {
        origenValor = EstadoValor.INICIO;
        asigna(valor);
    }

    /**
     * Sólo se queda con <code>valor<code> como único valor posible.
     *
     * @param valor
     * @return false si se asignó el único valor que le quedaba, true si se
     * eliminaron valores posibles.
     */
    public boolean asigna(int valor) {
        switch(origenValor) {
            case SIN_ASIGNAR:
                origenValor = EstadoValor.RESUELTO;
                //break;
            /*case INICIO: // Se usa al crear el Estado.
                return false;*/
            default:
                System.out.println("Asignando a casilla " + valor + " en estado: " + origenValor);
                
        }
        if (valoresPosibles.size() == 1 && valoresPosibles.contains(valor)) {
            return false;
        }
        boolean contained = false;
        Iterator<Integer> it = valoresPosibles.iterator();
        Integer i;
        while (it.hasNext()) {
            i = it.next();
            if (i.equals(valor)) {
                contained = true;
            } else {
                valoresImposibles.add(i);
                it.remove();
            }
        }
        if (!contained) {
            throw new IllegalStateException("El valor asignado " + valor + " no estaba entre los valores posibles.");
        }
        notifica();
        return true;
    }

    /** Intenta remover <code>valor</code> de los valores posibles.
     *  Si no está o la casilla no es editable, devuelve <code>false</code>.
     * @param valor
     * @return 
     */
    public boolean remueveValor(int valor) {
        if (origenValor != EstadoValor.SIN_ASIGNAR) return false;
        boolean remueve = valoresPosibles.remove(valor);
        if (remueve) {
            valoresImposibles.add(valor);
            notifica();
        }
        return remueve;
    }

    public EstadoValor origenValor() {
        return origenValor;
    }

    /**
     * Indica con su estado que esta variable está siendo estudiada. Sólo se
     * trata de una función informativa.
     */
    public void activa() {
        switch (origenValor) {
            case INICIO:
                origenValor = EstadoValor.INICIAL_EN_INSPECCIÓN;
                break;
            case SIN_ASIGNAR:
                origenValor = EstadoValor.EN_INSPECCIÓN;
                break;
            case RESUELTO:
                origenValor = EstadoValor.EN_INSPECCIÓN;
                break;
                //throw new IllegalStateException("Esta variable ya fue resuelta, no puede ser activada.");
            default:
                origenValor = EstadoValor.EN_INSPECCIÓN;
        }
    }

    /**
     * Indica con su estado que esta variable está siendo estudiada. Sólo se
     * trata de una función informativa.
     */
    public void desactiva() {
        switch (origenValor) {
            case INICIAL_EN_INSPECCIÓN:
                origenValor = EstadoValor.INICIO;
                break;
            case EN_INSPECCIÓN:
                origenValor = EstadoValor.RESUELTO;
                break;
            default:
                throw new IllegalStateException(
                        "¿En qué estado estaba " + valoresPosibles + "? " + origenValor);
        }
    }

    /*
	public void formaVariable(ColaDePrioridadesDinámica<Integer, Variable> cola) {
		cola.inserta(var.numPendientes(), var);
	}*/

    public Integer únicoPosible() {
        if (valoresPosibles.size() != 1) {
            throw new IllegalStateException("Aún quedan " + valoresPosibles.size());
        }
        return this.valoresPosibles.iterator().next();
    }

    /**
     * Permite ver los valores posibles, no debe ser utilizado para modificar la
     * estructura.
     *
     * @return
     */
    public Set<Integer> valoresPosibles() {
        return valoresPosibles;
    }

    /**
     * Permite ver los valores imposibles, no debe ser utilizado para modificar
     * la estructura.
     *
     * @return
     */
    public Set<Integer> valoresImposibles() {
        return valoresImposibles;
    }

    /**
     * Indica cuantos valores posibles faltan por probar.
     *
     * @return
     */
    public int numPendientes() {
        return valoresPosibles.size();
    }

    /**
     * Devuelve el valor de esta variable cuando sólo queda un valor posible.
     *
     * @return
     */
    public int valorFinal() {
        if (valoresPosibles.size() != 1) {
            throw new IllegalStateException("Aún hay varios valores posibles");
        }
        return valoresPosibles.iterator().next();
    }

    /**
     * Registra objetos interesados en ejecutar código cuando cambien los
     * valores posibles/imposibles de esta variable.
     *
     * @param escucha
     */
    @Override
    public void registraEscuchaCambioValor(EscuchaCambioPrioridad escucha) {
        escuchas.add(escucha);
    }

    @Override
    public void remueveEscuchaCambioValor(EscuchaCambioPrioridad escucha) {
        escuchas.remove(escucha);
    }

    private void notifica() {
        for (EscuchaCambioPrioridad escucha : escuchas) {
            escucha.prioridadModificada(valoresPosibles.size());
        }
    }
}

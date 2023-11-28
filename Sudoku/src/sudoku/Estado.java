/*
 * Código utilizado para el curso de Estructuras de Datos.
 * Se permite consultarlo para fines didácticos en forma personal,
 * pero no esta permitido transferirlo resuelto a estudiantes actuales o potenciales.
 */
package sudoku;

import ed.ColaDePrioridadesDinámica;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author blackzafiro
 */
public class Estado {

    Casilla[][] juego = new Casilla[9][9];
    
    /** Contiene las casillas para las cuales hay que buscar varlor. */
    private ColaDePrioridadesDinámica<Integer, Casilla> colap = new ColaDePrioridadesDinámica<>();
    
    /**
     * Contiene las asignaciones que ya resultaron de la propagación de
     * restricciones.
     */
    private Queue<Asigna> cola = new LinkedList<>(); // Tal vez esta no sea necesaria y baste con colap.

    public Estado(int[][] valores) {
        Casilla nueva;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                nueva = new Casilla();
                juego[i][j] = nueva;  // TODO: Falta usar colap.
            }
        }
        
        // Se agrega esta información por motivos estéticos.
        // Se forman las casillas con valor fijo para iniciar propagación.
        int r, c, val;
        for (int i = 0; i < valores.length; i++) {
            r = valores[i][0];
            c = valores[i][1];
            val = valores[i][2];
            juego[r][c].asignaInicial(val);
            cola.add(new Asigna(r, c, val));
        }
        
    }

    /**
     * Asigna un valor para la casilla, asegurándose de remover los valores que
     * serán incompatibles para otras casillas como resultado de esta operación.
     *
     * @param i renglón
     * @param j columna
     * @param valor valor para la casilla
     */
    //public void asigna(int i, int j, int valor) {
    public Integer[] asigna() {
        /*
		if (juego[i][j].origenValor() == EstadoValor.RESUELTO) {
			throw new IllegalStateException("Tratando de asignar valor a variable resuelta.");
		}
         */
        // TODO: ¡No he cambiado por la cola de prioridades!
        //Queue<Asigna> cola = new LinkedList<>();
        //cola.add(new Asigna(i, j, valor));
        Integer[] explorado = null;
        if (!cola.isEmpty()) {
            Asigna temp = cola.remove(); // TODO: Remover de colap también.
            int renglon = temp.renglon();
            int columna = temp.columna();
            int valor = temp.valor();
            juego[renglon][columna].asigna(valor);
            explorado = new Integer [] {renglon, columna, valor};

            // Coordenadas del cuadrante
            int y = (renglon / 3) * 3;
            int x = (columna / 3) * 3;
            int imod;
            int idiv;
            for (int ii = 0; ii < 9; ii++) {
                if (ii != columna) {
                    remueve(renglon, ii, valor);
                }
                if (ii != renglon) {
                    remueve(ii, columna, valor);
                }
                imod = ii % 3;	// columna del cuadrante
                idiv = ii / 3;	// renglón del cuadrante
                if (renglon != y + idiv || columna != x + imod) {
                    remueve(y + idiv, x + imod, valor);
                }
            }
        }
        
        // TODO: Cuando la cola esté vacía, ver si colap recomienda un nodo.
        return explorado;
    }

    /**
     * Remueve un valor de la lista de valores posibles. Si queda un único
     * valor, se lo asigna.
     *
     * @param renglon
     * @param columna
     * @param valor
     */
    private void remueve(int renglon, int columna, int valor) {
        if (juego[renglon][columna].remueveValor(valor)) {
            if (juego[renglon][columna].numPendientes() == 1) {
                // Realiza nuevas asignaciones hasta que las asignaciones
                // anteriores hayan terminado de propagar sus efectos.
                System.out.println("Sólo queda uno " + juego[renglon][columna].únicoPosible());
                cola.add(new Asigna(renglon, columna,
                        juego[renglon][columna].únicoPosible()));
            }
        }
    }

    /**
     * Indica la variable cuyos efectos están siendo analizados.
     *
     * @param i renglón
     * @param j columna
     */
    public void indicaInspección(int i, int j) {
        juego[i][j].activa();
    }

    /**
     * Indica la variable cuyos efectos terminaron de ser analizados.
     *
     * @param i renglón
     * @param j columna
     */
    public void terminaInspección(int i, int j) {
        juego[i][j].desactiva();
    }

}

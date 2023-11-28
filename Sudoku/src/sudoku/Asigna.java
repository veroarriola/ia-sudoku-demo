/*
 * Codigo utilizado para el curso de Estructuras de Datos.
 * Se permite consultarlo para fines didacticos en forma personal,
 * pero no esta permitido transferirlo resuelto a estudiantes actuales o potenciales.
 */
package sudoku;

/**
 * Clase que representa la acción de asignar un valor v, en la casilla (i,j).
 * @author blackzafiro
 */
public class Asigna {
    private int i;
    private int j;
    private int v;
    
    /**
     * Constructor
     * @param i renglón
     * @param j columna
     * @param valor valor para la casilla.
     */
    public Asigna(int i, int j, int valor) {
        this.i = i;
        this.j = j;
        this.v = valor;
    }
    
    /** 
     * Devuelve el índice del renglón de la casilla.
     * @return renglón.
     */
    public int renglon() {
        return i;
    }
    
    /** 
     * Devuelve el índice de la columna de la casilla.
     * @return columna.
     */
    public int columna() {
        return j;
    }
    
    /** 
     * Devuelve el valor a asignar en la casilla.
     * @return valor.
     */
    public int valor() {
        return v;
    }
}

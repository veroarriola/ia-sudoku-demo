/*
 * Código utilizado para el curso de Estructuras de Datos.
 * Se permite consultarlo para fines didácticos en forma personal,
 * pero no esta permitido transferirlo resuelto a estudiantes actuales o potenciales.
 */
package sudoku;

import java.util.Arrays;
import java.util.LinkedList;
import processing.core.PApplet;
import processing.core.PFont;
import java.util.Queue;

/**
 *
 * @author blackzafiro
 */
public class Sudoku extends PApplet {

    // Interfaz
    private PFont fuente;
    private int tamMosaico = 90;
    private int altoBarraInfo = 40;

    private boolean sobrePropaga = false;

    // Juego
    private Estado estadoActual;
    private Integer[] ultimoExplorado;

    public void settings() {
        size(tamMosaico * 9, tamMosaico * 9 + altoBarraInfo);
    }

    // renglón, columna, valor
    private int[][] sudoku58Easy = {
        {0, 1, 7},
        {1, 0, 5},
        {1, 1, 3},
        {0, 5, 1},
        {2, 5, 8},
        {1, 7, 6},
        {2, 8, 7},
        {3, 0, 7},
        {4, 1, 4},
        {3, 4, 5},
        {4, 3, 1},
        {5, 3, 6},
        {3, 7, 9},
        {4, 6, 5},
        {5, 6, 3},
        {5, 7, 2},
        {6, 1, 6},
        {7, 0, 8},
        {7, 2, 3},
        {8, 0, 9},
        {6, 3, 3},
        {7, 3, 4},
        {8, 5, 7},
        {7, 6, 7},
        {7, 8, 1}
    };

    /**
     * Rellena los valores asignados al inicio de un juego de Sudoku.
     *
     * @param valores arreglo de valores iniciales y sus coordenadas.
     */
    private void initSudoku(int[][] valores) {
        estadoActual = new Estado(valores);
    }

    private void propaga() {
        // TODO: Ojo, no se si último explorado siga siendo válido cuando
        // clone y cambie el estado actual.
        if (ultimoExplorado != null) {
            this.estadoActual.terminaInspección(ultimoExplorado[0],
                ultimoExplorado[1]);
        }
        ultimoExplorado = estadoActual.asigna();
        if(ultimoExplorado != null) {
            estadoActual.indicaInspección(ultimoExplorado[0],
                    ultimoExplorado[1]);
        }
    }

    /**
     * Crea el tablero de Sudoku.
     */
    @Override
    public void setup() {
        fuente = createFont("Arial", 12, true);
        initSudoku(sudoku58Easy);
    }

    @Override
    public void draw() {
        update(mouseX, mouseY);

        // Botón "Propaga"
        stroke(200, 150, 255);
        fill(200, 150, 255);
        rect(tamMosaico * 7, tamMosaico * 9, tamMosaico * 2, altoBarraInfo);
        fill(0, 0, 0);
        textFont(fuente, 30);
        text("Propaga", (int) (7.3 * tamMosaico), tamMosaico * 9 + altoBarraInfo - fuente.getSize());

        strokeWeight(1);
        textFont(fuente, 12);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                dibujaCasilla(estadoActual.juego[i][j], j * tamMosaico, i * tamMosaico);
            }
        }
        strokeWeight(3);
        for (int i = 0; i <= 9; i += 3) {
            line(0, i * tamMosaico, 9 * tamMosaico, i * tamMosaico);
            line(i * tamMosaico, 0, i * tamMosaico, 9 * tamMosaico);
        }
    }

    /**
     * Dibuja la casilla a partir de las coordenadas x y y.
     */
    private void dibujaCasilla(Casilla c, int x, int y) {
        stroke(0, 0, 0);
        fill(255, 255, 255);
        rect(x, y, tamMosaico, tamMosaico);
        java.util.Set<Integer> valoresPosibles = c.valoresPosibles();
        if (valoresPosibles.size() == 1) {
            fill(0, 0, 0);
            textFont(fuente, 30);
            switch (c.origenValor()) {
                case INICIO:
                    fill(0, 0, 0);
                    break;
                case TEMPORAL:
                    fill(100, 100, 255);
                    break;
                case RESUELTO:
                    fill(150, 150, 150);
                    break;
                case INICIAL_EN_INSPECCIÓN:
                    fill(200, 100, 255);
                    break;
                case EN_INSPECCIÓN:
                    fill(50, 50, 255);
                    break;
                default:
                    fill(255, 0, 0);
            }
            text("" + c.valorFinal(), x + tamMosaico / 2 - 10, y + 2 * tamMosaico / 3);
            textFont(fuente, 12);
            return;
        }

        // Valores posibles
        int i = 0, j = 0;
        fill(75, 200, 255);
        for (int n : valoresPosibles) {
            text("" + n, x + 5 + i * 15, y + 15 + 15 * j);
            i++;
            if (i == 5) {
                j++;
                i = 0;
            }
        }
        i = 0;
        j = 0;
        fill(255, 100, 0);
        for (int n : c.valoresImposibles()) {
            text("" + n, x + 5 + i * 15, y + tamMosaico - 18 + 15 * j);
            i++;
            if (i == 5) {
                j++;
                i = 0;
            }
        }
    }

    /**
     * Detecta si el ratón está sobre algún botón.
     *
     * @param x
     * @param y
     */
    private void update(int x, int y) {
        if (x >= tamMosaico * 7 && x < tamMosaico * 9
                && y >= tamMosaico * 9) {
            sobrePropaga = true;
        } else {
            sobrePropaga = false;
        }
    }

    @Override
    public void mousePressed() {
        if (sobrePropaga) {
            propaga();
        }
    }

    static public void main(String args[]) {
        PApplet.main(new String[]{"sudoku.Sudoku"});
    }
}

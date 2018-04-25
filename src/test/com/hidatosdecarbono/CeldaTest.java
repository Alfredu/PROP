package com.hidatosdecarbono;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CeldaTest {

    @Test
    void createCeldaFijaConValor() throws IllegalArgumentException{
        Celda c = new Celda(TipoCelda.FIJA,5);
        int value = c.getValor();
        assertEquals(5,value);

        TipoCelda type = c.getTipo();
        assertEquals(TipoCelda.FIJA,type);
    }

    @Test
    void lanzaExcepcionSiCeldaEsFijaYNoTieneValor() throws IllegalArgumentException{
        assertThrows(IllegalArgumentException.class, ()->
                {
                    Celda c = new Celda(TipoCelda.FIJA);
                });
    }

    @Test
    void lanzaExcepcionSiCeldaNoEsFijaYTieneValor() throws IllegalArgumentException{
        assertThrows(IllegalArgumentException.class, ()->{
            Celda c = new Celda(TipoCelda.AGUJERO, 5);
        });
    }

    @Test
    void tieneValorDevuelveTrueSiLaCeldaTieneValor() {
        Celda c = new Celda(TipoCelda.FIJA,5);
        assertTrue(c.tieneValor());
    }

    @Test
    void copiaCeldaFunciona(){
        Celda c = new Celda(TipoCelda.FIJA, 6);
        Celda copia = c.copiaCelda();
        copia.setValor(10);
        assertEquals(10, copia.getValor());
        assertEquals(6, c.getValor());
    }

    @Test
    void esValidaDevuelveTrueSoloEnCeldasFijasOVariables(){
        Celda fija = new Celda(TipoCelda.FIJA, 6);
        Celda variable = new Celda(TipoCelda.VARIABLE, 4);
        Celda agujero = new Celda(TipoCelda.AGUJERO);

        assertTrue(fija.esValida());
        assertTrue(variable.esValida());
        assertTrue(!agujero.esValida());
    }

}
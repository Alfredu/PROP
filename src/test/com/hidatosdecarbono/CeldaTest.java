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

}
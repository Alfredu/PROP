package com.hidatosdecarbono;

public class DomainFactory {
    private Jugador jugador;
    private CreadorHidatosCTRL creador;
    private JugarHidatosCTRL juega;

    public CreadorHidatosCTRL getControladorCreador() {
        creador = new CreadorHidatosCTRL();
        return creador;
    }

    public JugarHidatosCTRL getControladorJugar(String s){
        if(s == "Creado") {
            juega = new JugarHidatosCTRL();
            juega.inicializa(creador.getHidatoCreado());
            return juega;
        }
        return new JugarHidatosCTRL();
    }
}

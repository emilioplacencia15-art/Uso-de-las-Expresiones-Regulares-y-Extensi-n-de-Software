package com.mycompany.lenguajes.IGU;

import java.util.ArrayList;
import java.util.Stack;

public class AlgoritmoThompson {

    public static Automata convertir(String er) {
        // Por ahora, devolvamos un autómata simple para probar que tu botón funciona
        ArrayList<Estado> estados = new ArrayList<>();
        ArrayList<Transicion> trans = new ArrayList<>();
        
        Estado q0 = new Estado(100, 100, "q0");
        Estado q1 = new Estado(250, 100, "q1");
        q0.esInicial = true;
        q1.esFinal = true;
        
        estados.add(q0);
        estados.add(q1);
        trans.add(new Transicion(q0, q1, er)); // Muestra la ER como símbolo para probar
        
        return new Automata(estados, trans, q0);
    }
}
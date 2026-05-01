package com.mycompany.lenguajes.IGU;

import java.util.ArrayList;
import java.util.List;

public class ConvertidorER {
    private Automata automata;

    public ConvertidorER(Automata original) {
        // Clonamos para no destruir el original mientras eliminamos
        this.automata = new Automata(new ArrayList<>(original.estados), 
                                     new ArrayList<>(original.transiciones), 
                                     original.estadoInicial);
    }
    public Automata getAutomataActual() {
    return this.automata; // Asegúrate de que tu variable se llame 'automata'
}

    public void eliminarEstado(Estado k) {
        for (Estado i : automata.estados) {
            if (i == k) continue;
            for (Estado j : automata.estados) {
                if (j == k) continue;

                // Obtenemos los 4 componentes de la fórmula de Kleene
                String rik = getEtiqueta(i, k);
                String rkk = getEtiqueta(k, k);
                String rkj = getEtiqueta(k, j);
                String rij = getEtiqueta(i, j);

                if (!rik.isEmpty() && !rkj.isEmpty()) {
                    // Fórmula: Rij = Rij | (Rik . (Rkk)* . Rkj)
                    String bloque = "(" + rik + ")";
                    if (!rkk.isEmpty()) bloque += "(" + rkk + ")*";
                    bloque += "(" + rkj + ")";
                    
                    String nuevaER = rij.isEmpty() ? bloque : "(" + rij + "|" + bloque + ")";
                    actualizarOcrearTransicion(i, j, nuevaER);
                }
            }
        }
        automata.estados.remove(k);
        automata.transiciones.removeIf(t -> t.origen == k || t.destino == k);
    }

    public String getEtiqueta(Estado orig, Estado dest) {
        List<String> simbolos = new ArrayList<>();
        for (Transicion t : automata.transiciones) {
            if (t.origen == orig && t.destino == dest) simbolos.add(t.simbolo);
        }
        if (simbolos.isEmpty()) return "";
        return simbolos.size() == 1 ? simbolos.get(0) : "(" + String.join("|", simbolos) + ")";
    }

    private void actualizarOcrearTransicion(Estado i, Estado j, String nuevaER) {
        automata.transiciones.removeIf(t -> t.origen == i && t.destino == j);
        automata.transiciones.add(new Transicion(i, j, nuevaER));
    }

    public Automata getAutomata() { return this.automata; }
}
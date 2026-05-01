/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.lenguajes.IGU;
import javax.swing.*;
import java.awt.*;

public class PanelPasoAPaso extends javax.swing.JFrame {
    private ConvertidorER convertidor;
    private Lienzo lienzoPaso;
    
    private JLabel lblStatus;

    public PanelPasoAPaso(Automata inicial) {
        setTitle("Simulación Paso a Paso: Teorema de Kleene");
        setSize(1000, 700);
        setLayout(new BorderLayout());
        
        convertidor = new ConvertidorER(inicial);
        lienzoPaso = new Lienzo();
        lienzoPaso.setAutomata(convertidor.getAutomataActual());

        // Interfaz estilo la que mandaste
        JPanel panelControl = new JPanel();
        lblStatus = new JLabel("Listo para ejecutar");
        btnSiguiente = new JButton("Siguiente");

        btnSiguiente.addActionListener(e -> {
    Automata aut = convertidor.getAutomata(); // O getAutomataActual() según tu clase
    Estado aEliminar = null;

    // 1. Buscamos si queda algún estado que se pueda eliminar (que no sea inicial ni final)
    for (Estado edo : aut.estados) {
        if (!edo.esInicial && !edo.esFinal) {
            aEliminar = edo;
            break;
        }
    }

    if (aEliminar != null) {
        // 2. Si hay uno, lo eliminamos y actualizamos la vista
        lblStatus.setText("Eliminando estado: " + aEliminar.nombre);
        convertidor.eliminarEstado(aEliminar);
        lienzoPaso.setAutomata(convertidor.getAutomata());
        lienzoPaso.repaint();
    } else {
        // 3. Si ya no hay más para eliminar, mostramos el resultado final
        mostrarResultadoFinal();
    }
});

        panelControl.add(lblStatus);
        panelControl.add(btnSiguiente);

        add(new JScrollPane(lienzoPaso), BorderLayout.CENTER);
        add(panelControl, BorderLayout.SOUTH);
        setLocationRelativeTo(null);
    }

    private void mostrarResultadoFinal() {
        Automata f = convertidor.getAutomataActual();
        Estado i = f.estadoInicial;
        // Aquí aplicamos la clausura final R(k) que faltaba en tu imagen
        String rii = convertidor.getEtiqueta(i, i);
        String finalER = "";
        
        for(Estado j : f.estados) {
            if(j.esFinal) {
                String rij = convertidor.getEtiqueta(i, j);
                String rjj = convertidor.getEtiqueta(j, j);
                String rji = convertidor.getEtiqueta(j, i);
                
                // Fórmula simplificada R = (rii)* rij (rjj | rji(rii)*rij)*
                String parteA = rii.isEmpty() ? "" : "(" + rii + ")*";
                String parteC = (rjj.isEmpty() && rji.isEmpty()) ? "" : "(" + rjj + (rji.isEmpty() ? "" : "|" + rji + parteA + rij) + ")*";
                finalER += (finalER.isEmpty() ? "" : "|") + parteA + "(" + rij + ")" + parteC;
            }
        }

        lblStatus.setText("ER Final: " + finalER);
        btnSiguiente.setEnabled(false);
        
        // Limpiamos el lienzo para mostrar solo la flecha final
        f.transiciones.clear();
        Estado fin = f.estados.stream().filter(e -> e.esFinal).findFirst().orElse(i);
        f.transiciones.add(new Transicion(i, fin, finalER));
        lienzoPaso.repaint();
        
        JOptionPane.showMessageDialog(this, "Proceso terminado.\nExpresión: " + finalER);
    }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnSiguiente = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnSiguiente.setText("Siguiente");
        btnSiguiente.addActionListener(this::btnSiguienteActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(97, 97, 97)
                .addComponent(btnSiguiente)
                .addContainerGap(190, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(143, Short.MAX_VALUE)
                .addComponent(btnSiguiente)
                .addGap(127, 127, 127))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteActionPerformed
    Automata aut = convertidor.getAutomata();
        Estado aEliminar = null;

        // 1. Buscar el primer estado que no sea crítico
        for (Estado e : aut.estados) {
            if (!e.esInicial && !e.esFinal) {
                aEliminar = e;
                break;
            }
        }

        if (aEliminar != null) {
            // 2. Eliminar un estado y actualizar el dibujo
            lblStatus.setText("Eliminando estado: " + aEliminar.nombre);
            convertidor.eliminarEstado(aEliminar);
            lienzoPaso.setAutomata(convertidor.getAutomata());
            lienzoPaso.repaint();
        } else {
            // 3. FINALIZACIÓN: Solo quedan Inicial y Finales
            Estado ini = aut.estadoInicial;
            StringBuilder erFinal = new StringBuilder();

            for (Estado fin : aut.estados) {
                if (fin.esFinal) {
                    String rii = convertidor.getEtiqueta(ini, ini);
                    String rij = convertidor.getEtiqueta(ini, fin);
                    String rjj = convertidor.getEtiqueta(fin, fin);
                    String rji = convertidor.getEtiqueta(fin, ini);

                    // Fórmula Maestra: (rii)* . rij . (rjj | rji . (rii)* . rij)*
                    String parteA = rii.isEmpty() ? "" : "(" + rii + ")*";
                    String parteB = "(" + rij + ")";
                    String interiorC = rjj;
                    if (!rji.isEmpty()) {
                        interiorC += (interiorC.isEmpty() ? "" : "|") + rji + parteA + parteB;
                    }
                    String parteC = interiorC.isEmpty() ? "" : "(" + interiorC + ")*";

                    if (erFinal.length() > 0) erFinal.append("|");
                    erFinal.append(parteA).append(parteB).append(parteC);
                }
            }

            String resultado = erFinal.toString().isEmpty() ? "λ" : erFinal.toString();
            lblStatus.setText("Expresión Final Alcanzada");

            // Limpiar lienzo para mostrar la flecha final única
            aut.transiciones.clear();
            Estado edoDestino = aut.estados.stream().filter(e -> e.esFinal).findFirst().orElse(ini);
            aut.transiciones.add(new Transicion(ini, edoDestino, resultado));
            lienzoPaso.repaint();

            JOptionPane.showMessageDialog(this, "La ER es: " + resultado);
            btnSiguiente.setEnabled(false);
        }
    }//GEN-LAST:event_btnSiguienteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSiguiente;
    // End of variables declaration//GEN-END:variables
}


package com.mycompany.lenguajes.IGU;

import javax.swing.*;
import java.awt.*;

public class VentanaComparacionER extends JFrame {
    private Lienzo lienzoER;

    public VentanaComparacionER(Automata automataER, String erFinal) {
        setTitle("Resultado AFD -> ER (Teorema de Kleene)");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel superior para mostrar la ER en texto grande
        JPanel panelNorte = new JPanel();
        panelNorte.setBackground(new Color(240, 240, 240));
        JLabel lblER = new JLabel("Expresión Regular Resultante: " + erFinal);
        lblER.setFont(new Font("Monospaced", Font.BOLD, 16));
        lblER.setForeground(new Color(0, 102, 204));
        panelNorte.add(lblER);
        add(panelNorte, BorderLayout.NORTH);

        // Lienzo para mostrar el autómata de 2 estados con la ER
        lienzoER = new Lienzo();
        lienzoER.setAutomata(automataER);
        add(new JScrollPane(lienzoER), BorderLayout.CENTER);
    }
}
package br.com.dio.ui.custom.frame;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame(final Dimension dimension, final JPanel mainPanel){
        super("Sudoku");
        this.setSize(dimension);
        this.setPreferredSize(dimension);
        //para sair com o x
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        //centraliza na tela
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.add(mainPanel);
    }
}

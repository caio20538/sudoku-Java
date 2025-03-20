package br.com.dio.ui.custom.buttons;

import javax.swing.*;
import java.awt.event.ActionListener;

public class Reset extends JButton{
    public Reset(final ActionListener action) {
        setText("Reiniciar Jogo");
        this.addActionListener(action);
    }

}

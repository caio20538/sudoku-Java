package br.com.dio.ui.custom.buttons;

import javax.swing.*;
import java.awt.event.ActionListener;

public class CheckGameStatus extends JButton {

    public CheckGameStatus(final ActionListener action) {
        setText("Verificar Jogo");
        this.addActionListener(action);
    }
}

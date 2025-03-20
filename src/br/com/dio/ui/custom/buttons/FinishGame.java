package br.com.dio.ui.custom.buttons;

import javax.swing.*;
import java.awt.event.ActionListener;

public class FinishGame extends JButton {
    public FinishGame(final ActionListener action) {
        setText("Concluir");
        this.addActionListener(action);
    }
}

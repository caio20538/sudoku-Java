package br.com.dio.ui.custom.input;

import br.com.dio.model.Space;
import br.com.dio.service.EventEnum;
import br.com.dio.service.EventListenner;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class NumberText extends JTextField implements EventListenner {
    private final Space space;

    public NumberText(Space space) {
        this.space = space;
        var dimension = new Dimension(50, 50);
        setSize(dimension);
        setPreferredSize(dimension);
        setVisible(true);
        setFont(new Font("Arial", Font.PLAIN, 20));
        setHorizontalAlignment(CENTER);
        setDocument(new NumberTextLimit());
        setEnabled(!space.isFixed());

        if (space.isFixed()){
            setText(space.getActual().toString());
        }

        getDocument().addDocumentListener(new DocumentListener() {
           private void changeSpace(){
               if (!getText().isEmpty())
                   space.setActual(Integer.parseInt(getText()));

               space.clearSpace();
           }


            @Override
            public void insertUpdate(DocumentEvent e) {
                changeSpace();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changeSpace();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                changeSpace();
            }
        });
    }

    @Override
    public void update(EventEnum eventType) {
        if (eventType.equals(EventEnum.CLEAR_SPACE) && (this.isEnabled())){
            this.setText("");
        }
    }
}

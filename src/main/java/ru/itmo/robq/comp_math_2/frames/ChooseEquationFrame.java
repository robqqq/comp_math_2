package ru.itmo.robq.comp_math_2.frames;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ru.itmo.robq.comp_math_2.equations.Function;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

@org.springframework.stereotype.Component
public class ChooseEquationFrame extends JFrame {

    private final ChooseEquationInputDataFrame chooseInputDataFrame;

    private final Function[] functions;

    @Autowired
    public ChooseEquationFrame(@Qualifier("chooseEquationInputDataFrame") ChooseEquationInputDataFrame chooseInputDataFrame,
                               @Qualifier("firstFunction") Function firstFunction,
                               @Qualifier("secondFunction") Function secondFunction,
                               @Qualifier("thirdFunction") Function thirdFunction) {
        this.chooseInputDataFrame = chooseInputDataFrame;
        this.functions = new Function[3];
        functions[0] = firstFunction;
        functions[1] = secondFunction;
        functions[2] = thirdFunction;
        initUI();
    }

    private void initUI() {
        setTitle(ResourceBundle.getBundle("title").getString("title"));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(600, 600));
        setLocationRelativeTo(null);
        JLabel chooseEquationLabel = new JLabel(ResourceBundle.getBundle("labels").getString("choose_equation"));
        JButton[] equationsButtons = new JButton[3];
        for (int i = 0; i < 3; i++) {
            equationsButtons[i] = new JButton(functions[i].toString());
        }
        equationsButtons[0].addActionListener((ActionEvent event) -> onEquationButtonClick(0));
        equationsButtons[1].addActionListener((ActionEvent event) -> onEquationButtonClick(1));
        equationsButtons[2].addActionListener((ActionEvent event) -> onEquationButtonClick(2));
        JButton cancelButton = new JButton(ResourceBundle.getBundle("buttons").getString("cancel"));
        cancelButton.addActionListener((ActionEvent event) -> setVisible(false));
        BoxLayout bl = new BoxLayout(getContentPane(), BoxLayout.Y_AXIS);
        getContentPane().setLayout(bl);
        chooseEquationLabel.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        getContentPane().add(Box.createRigidArea(new Dimension(0, 10)));
        getContentPane().add(chooseEquationLabel);
        for (int i = 0; i < 3; i++) {
            getContentPane().add(Box.createRigidArea(new Dimension(0, 10)));
            equationsButtons[i].setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
            getContentPane().add(equationsButtons[i]);
            getContentPane().add(Box.createRigidArea(new Dimension(0, 10)));
        }
        getContentPane().add(Box.createRigidArea(new Dimension(0, 10)));
        cancelButton.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        getContentPane().add(cancelButton);
    }

    private void onEquationButtonClick(int i) {
        chooseInputDataFrame.setFunction(functions[i]);
        setVisible(false);
        chooseInputDataFrame.setVisible(true);
    }
}

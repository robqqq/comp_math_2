package ru.itmo.robq.comp_math_2.frames;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.itmo.robq.comp_math_2.methods.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

@Component
public class ChooseMethodFrame extends JFrame {

    private final ChooseEquationFrame chooseEquationFrame;
    private final ChooseEquationInputDataFrame chooseEquationInputDataFrame;
    private final ChooseSystemInputDataFrame chooseSystemInputDataFrame;

    private final EquationMethod equationMethod1;
    private final EquationMethod equationMethod2;
    private final SystemMethod systemMethod;

    @Autowired
    public ChooseMethodFrame(ChooseEquationFrame chooseEquationFrame,
                             ChooseEquationInputDataFrame chooseEquationInputDataFrame,
                             ChooseSystemInputDataFrame chooseSystemInputDataFrame,
                             @Qualifier("newtonEquationMethod") EquationMethod equationMethod1,
                             @Qualifier("simpleIterationEquationMethod") EquationMethod equationMethod2,
                             SystemMethod systemMethod) {
        this.chooseEquationFrame = chooseEquationFrame;
        this.chooseEquationInputDataFrame = chooseEquationInputDataFrame;
        this.chooseSystemInputDataFrame = chooseSystemInputDataFrame;
        this.equationMethod1 = equationMethod1;
        this.equationMethod2 = equationMethod2;
        this.systemMethod = systemMethod;
        initUI();
    }

    private void initUI() {
        setTitle(ResourceBundle.getBundle("title").getString("title"));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(600, 600));
        setLocationRelativeTo(null);
        JLabel chooseMethodLabel = new JLabel(ResourceBundle.getBundle("labels").getString("choose_method"));
        JButton[] methodsButtons = new JButton[3];
        methodsButtons[0] = new JButton(equationMethod1.toString());
        methodsButtons[1] = new JButton(equationMethod2.toString());
        methodsButtons[2] = new JButton(systemMethod.toString());
        methodsButtons[0].addActionListener((ActionEvent event) -> onNewtonEquationClick());
        methodsButtons[1].addActionListener((ActionEvent event) -> onSimpleIterationEquationClick());
        methodsButtons[2].addActionListener((ActionEvent event) -> onSimpleIterationSystemClick());
        JButton exitButton = new JButton(ResourceBundle.getBundle("buttons").getString("exit"));
        exitButton.addActionListener((ActionEvent event) -> System.exit(0));
        BoxLayout bl = new BoxLayout(getContentPane(), BoxLayout.Y_AXIS);
        getContentPane().setLayout(bl);
        chooseMethodLabel.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        getContentPane().add(Box.createRigidArea(new Dimension(0, 10)));
        getContentPane().add(chooseMethodLabel);
        for (int i = 0; i < 3; i++) {
            getContentPane().add(Box.createRigidArea(new Dimension(0, 10)));
            methodsButtons[i].setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
            getContentPane().add(methodsButtons[i]);
        }
        getContentPane().add(Box.createRigidArea(new Dimension(0, 10)));
        exitButton.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        getContentPane().add(exitButton);
    }

    private void onNewtonEquationClick() {
        chooseEquationInputDataFrame.setMethod(equationMethod1);
        chooseEquationFrame.setVisible(true);
    }

    private void onSimpleIterationEquationClick() {
        chooseEquationInputDataFrame.setMethod(equationMethod2);
        chooseEquationFrame.setVisible(true);
    }

    private void onSimpleIterationSystemClick() {
        chooseSystemInputDataFrame.setMethod(systemMethod);
        chooseSystemInputDataFrame.setVisible(true);
    }
}

package ru.itmo.robq.comp_math_2.frames;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itmo.robq.comp_math_2.equations.FirstSystem;
import ru.itmo.robq.comp_math_2.equations.System;
import ru.itmo.robq.comp_math_2.exceptions.CountIteratioNException;
import ru.itmo.robq.comp_math_2.exceptions.SufficientConditionException;
import ru.itmo.robq.comp_math_2.methods.EquationInputData;
import ru.itmo.robq.comp_math_2.methods.SystemInputData;
import ru.itmo.robq.comp_math_2.methods.SystemMethod;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ResourceBundle;

@Component
public class ChooseSystemInputDataFrame extends JFrame{
    private final SystemResultsFrame systemResultsFrame;

    @Setter
    private SystemMethod method;

    private System system;

    private JTextField x1TextField;
    private JTextField x2TextField;
    private JTextField epsTextField;

    @Autowired
    public ChooseSystemInputDataFrame(SystemResultsFrame systemResultsFrame, System system) {
        this.systemResultsFrame = systemResultsFrame;
        this.system = system;
        initUI();
    }

    private void initUI() {
        setTitle(ResourceBundle.getBundle("title").getString("title"));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(600, 600));
        setLocationRelativeTo(null);
        String systemString = system.toString();
        int newLinePosition = systemString.indexOf('\n');
        JLabel systemLabel1 = new JLabel(systemString.substring(0, newLinePosition));
        JLabel systemLabel2 = new JLabel(systemString.substring(newLinePosition + 1, systemString.length()));
        JLabel inputInitialApproximationLabel = new JLabel(ResourceBundle.getBundle("labels")
                .getString("input_initial_approximation"));
        JPanel inputInitialApproximationPanel = new JPanel();
        inputInitialApproximationPanel.setLayout(new BoxLayout(inputInitialApproximationPanel, BoxLayout.X_AXIS));
        JLabel x1Label = new JLabel("x1 = ");
        JLabel x2Label = new JLabel("x2 = ");
        x1TextField = new JTextField();
        x2TextField = new JTextField();
        x1TextField.setMaximumSize(new Dimension(300, 30));
        x2TextField.setMaximumSize(new Dimension(300, 30));
        JLabel inputAccuracyLabel = new JLabel(ResourceBundle.getBundle("labels").getString("input_accuracy"));
        JPanel inputAccuracyPanel = new JPanel();
        inputAccuracyPanel.setLayout(new BoxLayout(inputAccuracyPanel, BoxLayout.X_AXIS));
        JLabel epsLabel = new JLabel("eps = ");
        epsTextField = new JTextField();
        epsTextField.setMaximumSize(new Dimension(300,30 ));
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        JButton okButton = new JButton(ResourceBundle.getBundle("buttons").getString("ok"));
        okButton.addActionListener((ActionEvent event) -> onOkClick());
        JButton chooseFileButton = new JButton(ResourceBundle.getBundle("buttons").getString("choose_file"));
        chooseFileButton.addActionListener((ActionEvent event) -> onChooseFileClick());
        JButton cancelButton = new JButton(ResourceBundle.getBundle("buttons").getString("cancel"));
        cancelButton.addActionListener((ActionEvent event) -> setVisible(false));
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        getContentPane().add(Box.createRigidArea(new Dimension(0, 10)));
        systemLabel1.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        getContentPane().add(systemLabel1);
        getContentPane().add(Box.createRigidArea(new Dimension(0, 10)));
        systemLabel2.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        getContentPane().add(systemLabel2);
        getContentPane().add(Box.createRigidArea(new Dimension(0, 10)));
        inputInitialApproximationLabel.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        getContentPane().add(inputInitialApproximationLabel);
        getContentPane().add(Box.createRigidArea(new Dimension(0, 10)));
        inputInitialApproximationPanel.add(x1Label);
        inputInitialApproximationPanel.add(x1TextField);
        inputInitialApproximationPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        inputInitialApproximationPanel.add(x2Label);
        inputInitialApproximationPanel.add(x2TextField);
        inputInitialApproximationPanel.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        getContentPane().add(inputInitialApproximationPanel);
        getContentPane().add(Box.createRigidArea(new Dimension(0, 10)));
        inputAccuracyLabel.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        getContentPane().add(inputAccuracyLabel);
        getContentPane().add(Box.createRigidArea(new Dimension(0, 10)));
        inputAccuracyPanel.add(epsLabel);
        inputAccuracyPanel.add(epsTextField);
        inputAccuracyPanel.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        getContentPane().add(inputAccuracyPanel);
        getContentPane().add(Box.createRigidArea(new Dimension(0, 10)));
        buttonsPanel.add(okButton);
        buttonsPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonsPanel.add(chooseFileButton);
        buttonsPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonsPanel.add(cancelButton);
        buttonsPanel.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        getContentPane().add(buttonsPanel);
    }

    private void onChooseFileClick() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV File", "csv"));
        int ret = fileChooser.showOpenDialog(this);
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                SystemInputData inputData = new CsvToBeanBuilder<SystemInputData>(new FileReader(file))
                        .withType(SystemInputData.class)
                        .build()
                        .parse()
                        .get(0);
                x1TextField.setText(String.valueOf(inputData.getX1()));
                x2TextField.setText(String.valueOf(inputData.getX2()));
                epsTextField.setText(String.valueOf(inputData.getEps()));
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(this,
                        ResourceBundle.getBundle("exceptions").getString("file_not_found_exception"),
                        ResourceBundle.getBundle("title").getString("error"),
                        JOptionPane.ERROR_MESSAGE);
            } catch (RuntimeException e) {
                JOptionPane.showMessageDialog(this,
                        ResourceBundle.getBundle("exceptions").getString("csv_system_format_exception"),
                        ResourceBundle.getBundle("title").getString("error"),
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onOkClick() {
        System system = new FirstSystem();
        systemResultsFrame.setSystem(system);
        method.setSystem(system);
        double x1;
        double x2;
        double eps;
        try {
            x1 = Double.parseDouble(x1TextField.getText().replace(',', '.').trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "x1 " + ResourceBundle.getBundle("exceptions").getString("number_format_exception"),
                    ResourceBundle.getBundle("title").getString("error"),
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            x2 = Double.parseDouble(x2TextField.getText().replace(',', '.').trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "x2 " + ResourceBundle.getBundle("exceptions").getString("number_format_exception"),
                    ResourceBundle.getBundle("title").getString("error"),
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            eps = Math.abs(Double.parseDouble(epsTextField.getText().replace(',', '.').trim()));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "eps " + ResourceBundle.getBundle("exceptions").getString("number_format_exception"),
                    ResourceBundle.getBundle("title").getString("error"),
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        SystemInputData inputData = new SystemInputData();
        inputData.setX1(x1);
        inputData.setX2(x2);
        inputData.setEps(eps);
        method.setInputData(inputData);
        try {
            systemResultsFrame.setResults(method.solve());
        } catch (SufficientConditionException e) {
            JOptionPane.showMessageDialog(this,
                    ResourceBundle.getBundle("exceptions").getString("sufficient_condition_exception"),
                    ResourceBundle.getBundle("title").getString("error"),
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        setVisible(false);
        systemResultsFrame.setVisible(true);
    }
}

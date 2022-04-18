package ru.itmo.robq.comp_math_2.frames;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itmo.robq.comp_math_2.equations.Function;
import ru.itmo.robq.comp_math_2.exceptions.CountIteratioNException;
import ru.itmo.robq.comp_math_2.exceptions.SufficientConditionException;
import ru.itmo.robq.comp_math_2.methods.EquationMethod;
import ru.itmo.robq.comp_math_2.methods.EquationInputData;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ResourceBundle;

@Setter
@Component
public class ChooseEquationInputDataFrame extends JFrame {

    private EquationMethod method;

    private final EquationResultsFrame resultsFrame;

    private JTextField aTextField;
    private JTextField bTextField;
    private JTextField epsTextField;

    @Autowired
    public ChooseEquationInputDataFrame(EquationResultsFrame resultsFrame) {
        this.resultsFrame = resultsFrame;
        initUI();
    }

    public void setFunction(Function function) {
        method.setFunction(function);
    }

    private void initUI() {
        setTitle(ResourceBundle.getBundle("title").getString("title"));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(600, 600));
        setLocationRelativeTo(null);
        JLabel inputInitialApproximationLabel = new JLabel(ResourceBundle.getBundle("labels")
                .getString("input_initial_approximation"));
        JPanel inputInitialApproximationPanel = new JPanel();
        inputInitialApproximationPanel.setLayout(new BoxLayout(inputInitialApproximationPanel, BoxLayout.X_AXIS));
        JLabel aLabel = new JLabel("a = ");
        JLabel bLabel = new JLabel("b = ");
        aTextField = new JTextField();
        bTextField = new JTextField();
        aTextField.setMaximumSize(new Dimension(300, 30));
        bTextField.setMaximumSize(new Dimension(300, 30));
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
        inputInitialApproximationLabel.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        getContentPane().add(inputInitialApproximationLabel);
        getContentPane().add(Box.createRigidArea(new Dimension(0, 10)));
        inputInitialApproximationPanel.add(aLabel);
        inputInitialApproximationPanel.add(aTextField);
        inputInitialApproximationPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        inputInitialApproximationPanel.add(bLabel);
        inputInitialApproximationPanel.add(bTextField);
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
                EquationInputData inputData = new CsvToBeanBuilder<EquationInputData>(new FileReader(file))
                        .withType(EquationInputData.class)
                        .build()
                        .parse()
                        .get(0);
                aTextField.setText(String.valueOf(inputData.getA()));
                bTextField.setText(String.valueOf(inputData.getB()));
                epsTextField.setText(String.valueOf(inputData.getEps()));
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(this,
                        ResourceBundle.getBundle("exceptions").getString("file_not_found_exception"),
                        ResourceBundle.getBundle("title").getString("error"),
                        JOptionPane.ERROR_MESSAGE);
            } catch (RuntimeException e) {
                JOptionPane.showMessageDialog(this,
                        ResourceBundle.getBundle("exceptions").getString("csv_equation_format_exception"),
                        ResourceBundle.getBundle("title").getString("error"),
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onOkClick() {
        resultsFrame.setFunction(method.getFunction());
        double a;
        double b;
        double eps;
        try {
            a = Double.parseDouble(aTextField.getText().replace(',', '.').trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "a " + ResourceBundle.getBundle("exceptions").getString("number_format_exception"),
                    ResourceBundle.getBundle("title").getString("error"),
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            b = Double.parseDouble(bTextField.getText().replace(',', '.').trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "b " + ResourceBundle.getBundle("exceptions").getString("number_format_exception"),
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
        EquationInputData inputData = new EquationInputData();
        inputData.setA(a);
        inputData.setB(b);
        inputData.setEps(eps);
        method.setInputData(inputData);
        try {
            resultsFrame.setResults(method.solve());
        } catch (SufficientConditionException e) {
            JOptionPane.showMessageDialog(this,
                    ResourceBundle.getBundle("exceptions").getString("sufficient_condition_exception") +
                        "phi'(a) = " + e.getPhiA() + "phi'(b) = " + e.getPhiB(),
                    ResourceBundle.getBundle("title").getString("error"),
                    JOptionPane.ERROR_MESSAGE);
            return;
        } catch (CountIteratioNException e) {
            JOptionPane.showMessageDialog(this,
                    "Infinity process, iterations: " + e.getIterations(),
                    ResourceBundle.getBundle("title").getString("error"),
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        resultsFrame.setInputData(inputData);
        setVisible(false);
        resultsFrame.setVisible(true);
    }
}

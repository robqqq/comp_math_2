package ru.itmo.robq.comp_math_2.frames;

import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.stereotype.Component;
import ru.itmo.robq.comp_math_2.equations.Function;
import ru.itmo.robq.comp_math_2.methods.EquationResults;
import ru.itmo.robq.comp_math_2.methods.EquationInputData;
import ru.itmo.robq.comp_math_2.plot.FunctionPlot;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ResourceBundle;

@Component
public class EquationResultsFrame extends JFrame {
    private EquationResults results;
    private EquationInputData inputData;
    private JLabel xLabel;
    private JLabel fLabel;
    private JLabel iLabel;
    private JLabel phiALabel;
    private JLabel phiBLabel;
    private FunctionPlot plot;

    public EquationResultsFrame() {
        initUI();
    }

    public void setFunction(Function function) {
        plot.setFunction(function);
        if (inputData != null && results != null) {
            plot.repaint();
        }
    }

    public void setResults(EquationResults results) {
        this.results = results;
        xLabel.setText("x = " + results.getX());
        fLabel.setText("f(x) = " + results.getF());
        iLabel.setText(ResourceBundle.getBundle("labels").getString("iterations_count") + results.getI());
        phiALabel.setText("phi'(a) = " + results.getPhiA());
        phiBLabel.setText("phi'(b) = " + results.getPhiB());
        plot.setX(results.getX());
        if (inputData != null) {
            plot.repaint();
        }
    }

    public void setInputData(EquationInputData inputData) {
        this.inputData = inputData;
        plot.setInputData(inputData);
        if (results != null) {
            plot.repaint();
        }
    }

    private void initUI() {
        setTitle(ResourceBundle.getBundle("title").getString("title"));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(600, 600));
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        getContentPane().add(Box.createRigidArea(new Dimension(0, 10)));
        JPanel resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.X_AXIS));
        xLabel = new JLabel("x = ");
        fLabel = new JLabel("f(x) = ");
        iLabel = new JLabel(ResourceBundle.getBundle("labels").getString("iterations_count"));
        phiALabel = new JLabel("phi'(a) = ");
        phiBLabel = new JLabel("phi'(b) = ");
        resultsPanel.add(xLabel);
        resultsPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        resultsPanel.add(fLabel);
        resultsPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        resultsPanel.add(iLabel);
        resultsPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        resultsPanel.add(phiALabel);
        resultsPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        resultsPanel.add(phiBLabel);
        resultsPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        resultsPanel.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        getContentPane().add(resultsPanel);
        getContentPane().add(Box.createRigidArea(new Dimension(0, 10)));
        plot = new FunctionPlot();
        plot.setMinimumSize(new Dimension(500, 500));
        plot.setMaximumSize(new Dimension(500, 500));
        getContentPane().add(plot);
        getContentPane().add(Box.createRigidArea(new Dimension(0, 10)));
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        JButton okButton = new JButton(ResourceBundle.getBundle("buttons").getString("ok"));
        okButton.addActionListener((ActionEvent event) -> setVisible(false));
        buttonPanel.add(okButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        JButton saveToFileButton = new JButton(ResourceBundle.getBundle("buttons").getString("save_to_file"));
        saveToFileButton.addActionListener((ActionEvent event) -> onSaveToFileClick());
        buttonPanel.add(saveToFileButton);
        buttonPanel.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        getContentPane().add(buttonPanel);
    }

    private void onSaveToFileClick() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV File", "csv"));
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            if (!filePath.substring(filePath.lastIndexOf(".") + 1).equals("csv"))
                filePath += ".csv";
            try {
                FileWriter writer = new FileWriter(filePath);
                HeaderColumnNameMappingStrategy<EquationResults> strategy = new HeaderColumnNameMappingStrategy<>();
                strategy.setType(EquationResults.class);
                new StatefulBeanToCsvBuilder<EquationResults>(writer)
                        .withMappingStrategy(strategy)
                        .withApplyQuotesToAll(false)
                        .build()
                        .write(results);
                writer.close();
            } catch (IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
                JOptionPane.showMessageDialog(this,
                        ResourceBundle.getBundle("exceptions").getString("can_not_write_exception"),
                        ResourceBundle.getBundle("title").getString("error"),
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

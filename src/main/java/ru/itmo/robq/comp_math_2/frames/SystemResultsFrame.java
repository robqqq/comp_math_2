package ru.itmo.robq.comp_math_2.frames;

import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.stereotype.Component;
import ru.itmo.robq.comp_math_2.equations.System;
import ru.itmo.robq.comp_math_2.methods.EquationResults;
import ru.itmo.robq.comp_math_2.methods.SystemInputData;
import ru.itmo.robq.comp_math_2.methods.SystemResults;
import ru.itmo.robq.comp_math_2.plot.FunctionPlot;
import ru.itmo.robq.comp_math_2.plot.SystemPlot;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ResourceBundle;

@Component
public class SystemResultsFrame extends JFrame {
    private SystemResults results;
    private SystemInputData inputData;
    private SystemPlot plot;

    private JLabel x1Label;
    private JLabel x2Label;
    private JLabel x1ErrorLabel;
    private JLabel x2ErrorLabel;
    private JLabel f1Label;
    private JLabel f2Label;
    private JLabel iLabel;

    public SystemResultsFrame() {
        initUI();
    }


    public void setSystem(System system) {
        plot.setSystem(system);
        if (results != null) {
            plot.repaint();
        }
    }

    public void setResults(SystemResults results) {
        this.results = results;
        x1Label.setText("x1 = " + results.getX1());
        x2Label.setText("x2 = " + results.getX2());
        x1ErrorLabel.setText("Δx1 = " + results.getErrorX1());
        x2ErrorLabel.setText("Δx2 = " + results.getErrorX2());
        f1Label.setText("f1 = " + results.getF1());
        f2Label.setText("f2 = " + results.getF2());
        iLabel.setText(ResourceBundle.getBundle("labels").getString("iterations_count") + results.getI());
        plot.setSystemResults(results);
        if (inputData != null) {
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
        JPanel xPanel = new JPanel();
        xPanel.setLayout(new BoxLayout(xPanel, BoxLayout.X_AXIS));
        JPanel xErrorPanel = new JPanel();
        xErrorPanel.setLayout(new BoxLayout(xErrorPanel, BoxLayout.X_AXIS));
        JPanel fPanel = new JPanel();
        fPanel.setLayout(new BoxLayout(fPanel, BoxLayout.X_AXIS));
        x1Label = new JLabel("x1 = ");
        x2Label = new JLabel("x2 = ");
        x1ErrorLabel = new JLabel("Δx1 = ");
        x2ErrorLabel = new JLabel("Δx2 = ");
        f1Label = new JLabel("f1 = ");
        f2Label = new JLabel("f2 = ");
        iLabel = new JLabel(ResourceBundle.getBundle("labels").getString("iterations_count"));
        xPanel.add(x1Label);
        xPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        xPanel.add(x2Label);
        xErrorPanel.add(x1ErrorLabel);
        xErrorPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        xErrorPanel.add(x2ErrorLabel);
        fPanel.add(f1Label);
        fPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        fPanel.add(f2Label);
        xPanel.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        getContentPane().add(xPanel);
        getContentPane().add(Box.createRigidArea(new Dimension(0, 10)));
        xErrorPanel.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        getContentPane().add(xErrorPanel);
        getContentPane().add(Box.createRigidArea(new Dimension(0, 10)));
        fPanel.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        getContentPane().add(fPanel);
        getContentPane().add(Box.createRigidArea(new Dimension(0, 10)));
        iLabel.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        getContentPane().add(iLabel);
        getContentPane().add(Box.createRigidArea(new Dimension(0, 10)));
        plot = new SystemPlot();
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
                HeaderColumnNameMappingStrategy<SystemResults> strategy = new HeaderColumnNameMappingStrategy<>();
                strategy.setType(SystemResults.class);
                new StatefulBeanToCsvBuilder<SystemResults>(writer)
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

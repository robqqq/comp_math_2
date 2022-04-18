package ru.itmo.robq.comp_math_2.plot;

import lombok.Setter;
import org.springframework.stereotype.Component;
import ru.itmo.robq.comp_math_2.equations.System;
import ru.itmo.robq.comp_math_2.methods.SystemResults;

import java.awt.*;

@Component
@Setter
public class SystemPlot extends Canvas {
    private System system;
    private SystemResults systemResults;

    @Override
    public void paint(Graphics g) {
        if (system != null && systemResults != null) {
            double step = 0.0001;
            double max = -Double.MAX_VALUE;
            double min = Double.MAX_VALUE;
            double left = systemResults.getX1() - 2;
            double right = systemResults.getX1() + 2;
            for (double i = left; i < right; i += step) {
                for (int j = 1; j <= 2; j++) {
                    for (int k = 1; k <= 2; k++) {
                        if (system.getX2(j, k, i) > max) {
                            max = system.getValue(j, k, i);
                        }
                        if (system.getValue(j, k, i) < min) {
                            min = system.getValue(j, k, i);
                        }
                    }
                }
            }
            if (left * right < 0) {
                int x10 = (int) (25 - left * 450 / (right - left));
                g.drawLine(x10, 0, x10, 500);
                g.drawLine(x10 - 5, 10, x10, 0);
                g.drawLine(x10 + 5, 10, x10, 0);
            }
            {
                int x20 = (int) (475 + min * 450 / (max - min));
                g.drawLine(0, x20, 500, x20);
                g.drawLine(490, x20 - 5, 500, x20);
                g.drawLine(490, x20 + 5, 500, x20);
                g.fillOval(22, x20 - 3, 6, 6);
                g.fillOval(472, x20 - 3, 6, 6);
                g.drawString(String.format("%.2f", left), 25, x20 + 15);
                g.drawString(String.format("%.2f", right), 475, x20 + 15);
                int plotX1 = (int) (25 + (systemResults.getX1() - left) * 450 / (right - left));
                int plotX2 = (int) (475 - (systemResults.getX2() - min) * 450 / (max - min));
                g.fillOval(plotX1 - 3,  plotX2 - 3, 6, 6);
                g.drawString(String.format("%.2f, %.2f", systemResults.getX1(), systemResults.getX2()), plotX1, plotX2 - 5);
            }
            for (int j = 1; j <= 2; j++) {
                for (int k = 1; k <= 2; k++) {
                    for (double i = left; i < right; i += step) {
                        if (!Double.isNaN(system.getX2(j, k, i + step)) && !Double.isNaN(system.getX2(j, k, i + step))) {
                            g.drawLine((int) (25 + (i - left) * 450 / (right - left)),
                                    (int) (475 - (system.getX2(j, k, i) - min) * 450 / (max - min)),
                                    (int) (25 + (i + step - left) * 450 / (right - left)),
                                    (int) (475 - (system.getX2(j, k, i + step) - min) * 450 / (max - min)));
                        }
                    }
                }
            }
        }
    }
}

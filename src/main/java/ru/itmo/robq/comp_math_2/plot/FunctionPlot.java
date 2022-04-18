package ru.itmo.robq.comp_math_2.plot;

import lombok.Setter;
import org.springframework.stereotype.Component;
import ru.itmo.robq.comp_math_2.equations.Function;
import ru.itmo.robq.comp_math_2.methods.EquationInputData;

import java.awt.*;

@Setter
@Component
public class FunctionPlot extends Canvas {
    private Function function;
    private EquationInputData inputData;
    private Double x;

    @Override
    public void paint(Graphics g) {
        if (function != null && inputData != null && x != null) {
            double step = 0.01;
            double max = -Double.MAX_VALUE;
            double min = Double.MAX_VALUE;
            for (double i = inputData.getA(); i < inputData.getB(); i += step) {
                if (function.getValue(i) > max) {
                    max = function.getValue(i);
                }
                if (function.getValue(i) < min) {
                    min = function.getValue(i);
                }
            }
            if (inputData.getA() * inputData.getB() < 0) {
                int x0 = (int) (25 - inputData.getA() * 450 / (inputData.getB() - inputData.getA()));
                g.drawLine(x0, 0, x0, 500);
                g.drawLine(x0 - 5, 10, x0, 0);
                g.drawLine(x0 + 5, 10, x0, 0);
            }
            {
                int y0 = (int) (475 + min * 450 / (max - min));
                g.drawLine(0, y0, 500, y0);
                g.drawLine(490, y0 - 5, 500, y0);
                g.drawLine(490, y0 + 5, 500, y0);
                g.fillOval(22, y0 - 3, 6, 6);
                g.fillOval(472, y0 - 3, 6, 6);
                g.drawString(String.format("%.2f", inputData.getA()), 25, y0 + 15);
                g.drawString(String.format("%.2f", inputData.getB()), 475, y0 + 15);
                int plotX = (int) (25 + (x - inputData.getA()) * 450 / (inputData.getB() - inputData.getA()));
                g.fillOval(plotX - 3, y0 - 3, 6, 6);
                g.drawString(String.format("%.2f", x), plotX, y0 - 5);
            }
            for (double i = inputData.getA(); i < inputData.getB(); i += step) {
                g.drawLine((int) (25 + (i - inputData.getA()) * 450 / (inputData.getB() - inputData.getA())),
                        (int) (475 - (function.getValue(i) - min) * 450 / (max - min)),
                        (int) (25 + (i + step - inputData.getA()) * 450 / (inputData.getB() - inputData.getA())),
                        (int) (475 - (function.getValue(i + step) - min) * 450 / (max - min)));
            }
        }
    }
}

package ru.itmo.robq.comp_math_2.equations;

import org.springframework.stereotype.Component;

@Component
public class ThirdFunction implements Function{

    @Override
    public double getValue(double x) {
        return Math.sin(x) + 0.1 * x * x;
    }

    @Override
    public double getFirstDerivative(double x) {
        return Math.cos(x) + 0.2 * x;
    }

    @Override
    public double getSecondDerivative(double x) {
        return 0.2 - Math.sin(x);
    }

    @Override
    public String toString() {
        return "sinx + 0,1x^2";
    }
}

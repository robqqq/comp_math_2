package ru.itmo.robq.comp_math_2.equations;

import org.springframework.stereotype.Component;

@Component
public class FirstFunction implements Function{

    @Override
    public double getValue(double x) {
        return x * x * x - x + 4;
    }

    @Override
    public double getFirstDerivative(double x) {
        return 3 * x * x - 1;
    }

    @Override
    public double getSecondDerivative(double x) {
        return 6 * x;
    }

    @Override
    public String toString() {
        return "x^3 - x + 4";
    }
}

package ru.itmo.robq.comp_math_2.equations;

import org.springframework.stereotype.Component;

@Component
public class SecondFunction implements Function{

    @Override
    public double getValue(double x) {
        return x * x * x - x * x - 3;
    }

    @Override
    public double getFirstDerivative(double x) {
        return 3 * x * x - 2 * x;
    }

    @Override
    public double getSecondDerivative(double x) {
        return 6 * x - 2;
    }

    @Override
    public String toString() {
        return "x^3 - x^2 - 3";
    }
}

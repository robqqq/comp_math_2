package ru.itmo.robq.comp_math_2.equations;

public interface Function {

    double getValue(double x);

    double getFirstDerivative(double x);

    double getSecondDerivative(double x);
}

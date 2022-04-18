package ru.itmo.robq.comp_math_2.equations;

public interface System {

    double getValue(int equation, double x1, double x2);

    double getPhi(int equation, double x1, double x2);

    double getPhiDerivative(int equation, int differential, double x1, double x2);

    double getDerivative(int equation, int differential, double x1, double x2);

    double getX2(int equation, int index, double x1);
}

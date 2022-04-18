package ru.itmo.robq.comp_math_2.equations;

import org.springframework.stereotype.Component;

@Component
public class FirstSystem implements System{

    @Override
    public double getValue(int equation, double x1, double x2) {
        return switch (equation) {
            case 1 -> 0.1 * x1 * x1 + x1 + 0.2 * x2 * x2 - 0.3;
            case 2 -> 0.2 * x1 * x1 + x2 - 0.1 * x1 * x2 - 0.7;
            default -> throw new ArrayIndexOutOfBoundsException();
        };
    }

    @Override
    public double getPhi(int equation, double x1, double x2) {
        return switch (equation) {
            case 1 -> 0.3 - 0.1 * x1 * x1 - 0.2 * x2 * x2;
            case 2 -> 0.7 + 0.1 * x1 * x2 - 0.2 * x1 * x1;
            default -> throw new ArrayIndexOutOfBoundsException();
        };
    }

    @Override
    public double getPhiDerivative(int equation, int differential, double x1, double x2) {
        return switch (equation) {
            case 1 ->
                switch (differential) {
                    case 1 -> -0.2 * x1;
                    case 2 -> -0.4 * x2;
                    default -> throw new ArrayIndexOutOfBoundsException();
                };
            case 2 ->
                switch (differential) {
                    case 1 -> 0.1 * x2 - 0.4 * x1;
                    case 2 -> 0.1 * x1;
                    default -> throw new ArrayIndexOutOfBoundsException();
                };
            default -> throw new ArrayIndexOutOfBoundsException();
        };
    }

    @Override
    public double getX2(int equation, int index, double x1) {
        return switch (equation) {
            case 1 -> switch (index) {
                case 1 -> Math.sqrt(1.5 - 0.5 * x1 * x1 - 5 * x1);
                case 2 -> -Math.sqrt(1.5 - 0.5 * x1 * x1 - 5 * x1);
                default -> throw new IndexOutOfBoundsException();
            };
            case 2 -> switch (index) {
                case 1, 2 -> (0.7 - 0.2 * x1 * x1) / (1 - 0.1 * x1);
                default -> throw new IndexOutOfBoundsException();
            };
            default -> throw new IndexOutOfBoundsException();
        };
    }

    @Override
    public double getDerivative(int equation, int differential, double x1, double x2) {
        return switch (equation) {
            case 1 ->
                switch (differential) {
                    case 1 -> 0.2 * x1 + 1;
                    case 2 -> 0.4 * x2;
                    default -> throw new ArrayIndexOutOfBoundsException();
                };
            case 2 ->
                switch (differential) {
                    case 1 -> 0.4 * x1 - 0.1 * x2;
                    case 2 -> 1 - 0.1 * x1;
                    default -> throw new ArrayIndexOutOfBoundsException();
                };
            default -> throw new ArrayIndexOutOfBoundsException();
        };
    }

    @Override
    public String toString() {
        return "0.1 * x1^2 + x1 + 0.2 * x2^2 - 0.3 = 0\n" +
                "0.2 * x1^2 + x2 - 0.1 * x1 * x2 - 0.7 = 0";
    }
}

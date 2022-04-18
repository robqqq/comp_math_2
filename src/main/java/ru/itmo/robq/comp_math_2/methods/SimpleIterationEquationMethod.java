package ru.itmo.robq.comp_math_2.methods;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import ru.itmo.robq.comp_math_2.equations.Function;
import ru.itmo.robq.comp_math_2.exceptions.CountIteratioNException;
import ru.itmo.robq.comp_math_2.exceptions.SufficientConditionException;

import java.util.ResourceBundle;

@Setter
@Component
public class SimpleIterationEquationMethod implements EquationMethod{

    private EquationInputData inputData;
    @Getter
    private Function function;

    @Override
    public EquationResults solve() {
        EquationResults results = new EquationResults();
        double lambda;
        if (function.getFirstDerivative(inputData.getA()) > function.getFirstDerivative(inputData.getB())) {
            results.setX(inputData.getA());
            lambda = -1 / function.getFirstDerivative(inputData.getA());
        } else {
            results.setX(inputData.getB());
            lambda = -1 / function.getFirstDerivative(inputData.getB());
        }
        results.setPhiA(Math.abs(1 + lambda * function.getFirstDerivative(inputData.getA())));
        results.setPhiB(Math.abs(1 + lambda * function.getFirstDerivative(inputData.getB())));
        if (Math.abs(1 + lambda * function.getFirstDerivative(inputData.getA())) >= 1 &&
                Math.abs(1 + lambda * function.getFirstDerivative(inputData.getB())) >= 1) {
            throw new SufficientConditionException(Math.abs(1 + lambda * function.getFirstDerivative(inputData.getA())),
                    Math.abs(1 + lambda * function.getFirstDerivative(inputData.getB())));
        }
        results.setI(0);
        double oldX;
        do {
            oldX = results.getX();
            results.setX(oldX + lambda * function.getValue(oldX));
            results.setI(results.getI() + 1);
            if (results.getI() > 100) {
                throw new CountIteratioNException(results.getI());
            }
        } while (Math.abs(results.getX() - oldX) > inputData.getEps());
        results.setF(function.getValue(results.getX()));
        return results;
    }

    @Override
    public String toString() {
        return ResourceBundle.getBundle("buttons").getString("method.equation.simple_iteration");
    }
}

package ru.itmo.robq.comp_math_2.methods;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import ru.itmo.robq.comp_math_2.equations.Function;
import ru.itmo.robq.comp_math_2.exceptions.SufficientConditionException;

import java.util.ResourceBundle;

@Setter
@Component
public class NewtonEquationMethod implements EquationMethod{

    private EquationInputData inputData;
    @Getter
    private Function function;

    @Override
    public String toString() {
        return ResourceBundle.getBundle("buttons").getString("method.equation.newton");
    }

    @Override
    public EquationResults solve() {
        if (function.getValue(inputData.getA()) * function.getValue(inputData.getB()) >= 0) {
            throw new SufficientConditionException(0,0);
        }
        EquationResults results = new EquationResults();
        results.setI(0);
        if (inputData.getA() * function.getSecondDerivative(inputData.getA()) > 0) {
            results.setX(inputData.getA());
        } else {
            results.setX(inputData.getB());
        }
        double oldX;
        do {
            oldX = results.getX();
            results.setX(oldX - function.getValue(oldX) / function.getFirstDerivative(oldX));
            results.setI(results.getI() + 1);
        } while (Math.abs(results.getX() - oldX) > inputData.getEps());
        results.setF(function.getValue(results.getX()));
        return results;
    }
}

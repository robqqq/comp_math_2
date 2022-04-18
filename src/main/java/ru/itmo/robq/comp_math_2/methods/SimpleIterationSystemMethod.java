package ru.itmo.robq.comp_math_2.methods;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itmo.robq.comp_math_2.equations.System;
import ru.itmo.robq.comp_math_2.exceptions.SufficientConditionException;

import java.util.ResourceBundle;

@Setter
@Component
public class SimpleIterationSystemMethod implements SystemMethod{

    private System system;
    private SystemInputData inputData;

    @Autowired
    public SimpleIterationSystemMethod(System system) {
        this.system = system;
    }

    @Override
    public SystemResults solve() {
        if (Math.abs(system.getPhiDerivative(1, 1, inputData.getX1(), inputData.getX2())) +
                Math.abs(system.getPhiDerivative(1, 2, inputData.getX1(), inputData.getX2())) >= 1 &&
                Math.abs(system.getPhiDerivative(2, 1, inputData.getX1(), inputData.getX2())) +
                Math.abs(system.getPhiDerivative(2, 2, inputData.getX1(), inputData.getX2())) >= 1) {
            throw new SufficientConditionException(0, 0);
        }
        SystemResults results = new SystemResults();
        results.setX1(inputData.getX1());
        results.setX2(inputData.getX2());
        results.setI(0);
        double oldX1;
        double oldX2;
        do {
            oldX1 = results.getX1();
            oldX2 = results.getX2();
            results.setX1(system.getPhi(1, oldX1, oldX2));
            results.setX2(system.getPhi(2, oldX1, oldX2));
            results.setI(results.getI() + 1);
        } while (Math.max(Math.abs(results.getX1() - oldX1), Math.abs(results.getX2() - oldX2)) > inputData.getEps());
        results.setErrorX1(Math.abs(results.getX1() - oldX1));
        results.setErrorX2(Math.abs(results.getX2() - oldX2));
        results.setF1(system.getValue(1, results.getX1(), results.getX2()));
        results.setF2(system.getValue(2, results.getX1(), results.getX2()));
        return results;
    }

    @Override
    public String toString() {
        return ResourceBundle.getBundle("buttons").getString("method.system.simple_iteration");
    }
}

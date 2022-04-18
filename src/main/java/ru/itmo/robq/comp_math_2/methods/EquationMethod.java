package ru.itmo.robq.comp_math_2.methods;

import ru.itmo.robq.comp_math_2.equations.Function;

public interface EquationMethod {

    void setInputData(EquationInputData inputData);
    void setFunction(Function function);
    Function getFunction();
    EquationResults solve();
}

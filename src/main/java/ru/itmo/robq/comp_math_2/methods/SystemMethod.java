package ru.itmo.robq.comp_math_2.methods;

import ru.itmo.robq.comp_math_2.equations.System;

public interface SystemMethod {

    void setInputData(SystemInputData inputData);
    void setSystem(System system);
    SystemResults solve();
}

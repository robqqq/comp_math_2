package ru.itmo.robq.comp_math_2.methods;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class EquationInputData {
    @CsvBindByName(column = "a", required = true)
    private double a;
    @CsvBindByName(column = "b", required = true)
    private double b;
    @CsvBindByName(column = "eps", required = true)
    private double eps;
}

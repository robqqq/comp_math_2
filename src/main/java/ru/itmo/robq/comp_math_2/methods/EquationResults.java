package ru.itmo.robq.comp_math_2.methods;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

@Data
public class EquationResults {
    @CsvBindByName(column = "x")
    @CsvBindByPosition(position = 0)
    private double x;
    @CsvBindByName(column = "f(x)")
    @CsvBindByPosition(position = 1)
    private double f;
    @CsvBindByName(column = "iterations")
    @CsvBindByPosition(position = 2)
    private int i;

    private double phiA;
    private double phiB;
}

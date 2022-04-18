package ru.itmo.robq.comp_math_2.methods;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

@Data
public class SystemResults {
    @CsvBindByName(column = "x1")
    @CsvBindByPosition(position = 0)
    private double x1;
    @CsvBindByName(column = "x2")
    @CsvBindByPosition(position = 1)
    private double x2;
    @CsvBindByName(column = "error_x1")
    @CsvBindByPosition(position = 2)
    private double errorX1;
    @CsvBindByName(column = "error_x2")
    @CsvBindByPosition(position = 3)
    private double errorX2;
    @CsvBindByName(column = "f1")
    @CsvBindByPosition(position = 4)
    private double f1;
    @CsvBindByName(column = "f2")
    @CsvBindByPosition(position = 5)
    private double f2;
    @CsvBindByName(column = "iterations")
    @CsvBindByPosition(position = 6)
    private int i;
}

package ru.itmo.robq.comp_math_2.methods;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class SystemInputData {
    @CsvBindByName(column = "x1", required = true)
    private double x1;
    @CsvBindByName(column = "x2", required = true)
    private double x2;
    @CsvBindByName(column = "eps", required = true)
    private double eps;
}

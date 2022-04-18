package ru.itmo.robq.comp_math_2.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CountIteratioNException extends RuntimeException{
    private int iterations;
}

package ru.itmo.robq.comp_math_2;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import ru.itmo.robq.comp_math_2.frames.ChooseMethodFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

@SpringBootApplication
public class SpringBootCompMath2Application{

    public static void main(String[] args) {
        ApplicationContext ctx = new SpringApplicationBuilder(SpringBootCompMath2Application.class)
                .headless(false).run(args);

        EventQueue.invokeLater(() -> {

            JFrame chooseMethodFrame = ctx.getBean(ChooseMethodFrame.class);
            chooseMethodFrame.setVisible(true);
        });
    }

}

package code_files.entities.shape_tree;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Setter
@Getter
public class Rectangle extends Shape {
    public double x_size, y_size;
}

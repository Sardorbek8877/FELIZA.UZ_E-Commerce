package uz.feliza.felizabackend.projection;

import org.springframework.data.rest.core.config.Projection;
import uz.feliza.felizabackend.entity.Status;

@Projection(types = Status.class)
public interface CustomStatus {

    Long getId();

    String getName();
}

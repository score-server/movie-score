package ch.felix.moviedbapi.service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.stereotype.Service;

/**
 * @author Felix
 * @date 06.06.2018
 * <p>
 * Project: movie-db-api
 * Package: ch.felix.moviedbapi.service
 **/

@Service
public class ViolationService {

    public String getViolation(ConstraintViolationException e) {
        StringBuilder violation = new StringBuilder();
        for (ConstraintViolation constraintViolation : e.getConstraintViolations()) {
            violation.append("\n");
            violation.append(constraintViolation.getPropertyPath());
            violation.append(" ");
            violation.append(constraintViolation.getMessage());
        }
        return violation.toString();
    }

}

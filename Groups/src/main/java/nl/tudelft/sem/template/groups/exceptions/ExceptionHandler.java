package nl.tudelft.sem.template.groups.exceptions;

import org.springframework.beans.TypeMismatchException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandler {

    /**
     * Handles missing parameters in controller methods.
     *
     * @param e the exception caught
     * @return an error message
     */
    @org.springframework.web.bind.annotation.ExceptionHandler
            (MissingServletRequestParameterException.class)
    public @ResponseBody
    String handleMissingParams(MissingServletRequestParameterException e) {
        String name = e.getParameterName();
        return name + " parameter is missing";
    }

    /**
     * Handles type mismatches in controller methods.
     *
     * @param e the exception caught
     * @return an error message
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(TypeMismatchException.class)
    public @ResponseBody
    String handleTypeMismatchException(TypeMismatchException e) {
        String input = e.getValue().toString();
        String type = e.getRequiredType().toString();
        return "Incorrect type for input: " + input + ". Type should be: " + type;
    }
}


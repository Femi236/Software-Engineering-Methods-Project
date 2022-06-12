package nl.tudelft.sem.template.field;

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
    public @ResponseBody String handleMissingParams(MissingServletRequestParameterException e) {
        String name = e.getParameterName();
        return name + " parameter is missing";
        // Actual exception handling
    }
}

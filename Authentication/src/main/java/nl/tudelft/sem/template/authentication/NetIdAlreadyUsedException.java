package nl.tudelft.sem.template.authentication;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;



@ResponseStatus(value = HttpStatus.IM_USED, reason = "netID is already used")
public class NetIdAlreadyUsedException extends Exception {
    private static final long serialVersionUID = 1;

}

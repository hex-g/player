package hive.player.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "The Authenticated user id can not be blank")
public class BlankIdException extends RuntimeException{
}

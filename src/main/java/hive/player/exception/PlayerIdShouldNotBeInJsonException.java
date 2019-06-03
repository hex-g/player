package hive.player.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "The autogenerated playerId should not be in JSON")
public class PlayerIdShouldNotBeInJsonException extends RuntimeException{
}

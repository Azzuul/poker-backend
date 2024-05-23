package cl.bennu.poker.card.api.base;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
public abstract class BaseController {

    protected static ResponseEntity<?> ok() {
        return ResponseEntity.ok(HttpStatus.OK);
    }

    protected static ResponseEntity<?> ok(Object o) {
        return ResponseEntity.ok().body(o);
    }

}

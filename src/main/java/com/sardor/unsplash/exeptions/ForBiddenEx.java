package com.sardor.unsplash.exeptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ControllerAdvice
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForBiddenEx extends RuntimeException {

    private String type;
    private String massage;

    public ForBiddenEx(String type, String massage) {
        this.type = type;
        this.massage = massage;
    }
}

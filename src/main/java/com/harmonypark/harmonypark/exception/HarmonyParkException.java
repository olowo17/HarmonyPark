package com.harmonypark.harmonypark.exception;


import com.harmonypark.harmonypark.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class HarmonyParkException extends RuntimeException{

    protected HttpStatus httpStatus;

    private ErrorCode errorCode;

    private String infoLink;

    private String metadata;

    public HarmonyParkException(final String message) {
        super(message);
    }

    public HarmonyParkException(final String message, final HttpStatus httpStatus) {
        this(message);
        this.httpStatus = httpStatus;
    }

    public HarmonyParkException(final String message, final HttpStatus httpStatus, final ErrorCode errorCode) {
        this(message, httpStatus);
        this.errorCode = errorCode;
    }

    public HarmonyParkException(final String message, final HttpStatus httpStatus, final String metadata) {
        this(message, httpStatus);
        this.metadata = metadata;
    }


    public HarmonyParkException(final String message, final HttpStatus httpStatus,
                                final ErrorCode errorCode, final String metadata) {
        this(message, httpStatus, errorCode);
        this.metadata = metadata;
    }
}

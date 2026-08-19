package com.awin.awin_project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TransactionNotFoundException.class)
    public ProblemDetail handleNotFound(
            TransactionNotFoundException exception
    ) {
        var problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                exception.getMessage()
        );
        problem.setTitle("Transaction not found");

        return problem;
    }

    @ExceptionHandler(TransactionAlreadyDecidedException.class)
    public ProblemDetail handleAlreadyDecided(
            TransactionAlreadyDecidedException exception
    ) {
        var problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                exception.getMessage()
        );
        problem.setTitle("Transaction already decided");

        return problem;
    }
}
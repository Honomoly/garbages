package com.honomoly.garbages;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

public class GlobalHttpException extends ErrorResponseException {

    public GlobalHttpException(HttpStatusCode httpStatusCode) {
        super(httpStatusCode);
    }

    public GlobalHttpException(HttpStatusCode httpStatusCode, Throwable cause) {
        super(httpStatusCode, cause);
    }

    // 아마 이 생성자를 가장 많이 사용하지 않을까 생각
    public GlobalHttpException(HttpStatusCode httpStatusCode, String detail) {
        this(httpStatusCode, detail, null);
    }

    public GlobalHttpException(HttpStatusCode httpStatusCode, String detail, Throwable cause) {
        super(httpStatusCode, ProblemDetail.forStatusAndDetail(httpStatusCode, detail), cause);
    }

}

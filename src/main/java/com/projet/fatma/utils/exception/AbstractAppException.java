package com.projet.fatma.utils.exception;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.projet.fatma.utils.exception.model.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class AbstractAppException extends RuntimeException {

    protected static final Map<Integer, String> exceptionMapping;
    @Serial
    private static final long serialVersionUID = -1L;

    static {
        exceptionMapping = new HashMap<>();
        exceptionMapping.put(BAD_REQUEST.value(), "Incorrect parameter in the request");
        exceptionMapping.put(UNAUTHORIZED.value(), "Missing authentication in the request");
        exceptionMapping.put(FORBIDDEN.value(), "Request for a restricted resource");
        exceptionMapping.put(NOT_FOUND.value(),
                "Request for a non-existent resource (empty lists should return 200 OK or 206 Partial Content)");
        exceptionMapping.put(METHOD_NOT_ALLOWED.value(),
                "Use of a method not allowed for the requested resource (but handled otherwise)");
        exceptionMapping.put(NOT_ACCEPTABLE.value(),
                "Missing or unexpected content type or content encoding in the request");
        exceptionMapping.put(UNSUPPORTED_MEDIA_TYPE.value(),
                "Request for a non-existent range of an actual resource");
        exceptionMapping.put(UNPROCESSABLE_ENTITY.value(), "Incorrect entity in the request");
        exceptionMapping.put(INTERNAL_SERVER_ERROR.value(),
                "Unrecoverable error from the service (not related to an upstream service called by it)");
        exceptionMapping.put(NOT_IMPLEMENTED.value(), "Use of a method not handled for any resource");
        exceptionMapping.put(BAD_GATEWAY.value(),
                "Error from an essential upstream service called by the main service");
        exceptionMapping.put(GATEWAY_TIMEOUT.value(),
                "Timeout from an essential upstream service called by the main service");
        exceptionMapping.put(REQUESTED_RANGE_NOT_SATISFIABLE.value(), "Range not satisfied");
    }

    @JsonIgnore
    private Integer httpStatus; //NOSONAR

    @JsonIgnore
    private String mainResource; //NOSONAR

    @JsonProperty("errors")
    private List<ErrorResponse> errors;

    protected AbstractAppException(int httpStatus, String mainResource) {
        super(eventCode(httpStatus));
        this.httpStatus = httpStatus;
        this.mainResource = mainResource;
    }

    private static String eventCode(Integer httpStatus) {
        return exceptionMapping.computeIfAbsent(httpStatus, val -> "HTTP-" + httpStatus);
    }

}


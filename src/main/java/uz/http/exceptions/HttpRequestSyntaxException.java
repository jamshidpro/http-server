package uz.http.exceptions;

public class HttpRequestSyntaxException extends RuntimeException {
    public HttpRequestSyntaxException(String message) {
        super(message);
    }
}

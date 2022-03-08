package project.utils;

import java.util.Objects;

public class Result<T> {

    private String message;

    private T data;

    private int code;

    public Result() {}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Result)) return false;
        Result<?> result = (Result<?>) o;
        return code == result.code && Objects.equals(message, result.message) && Objects.equals(data, result.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, data, code);
    }

    @Override
    public String toString() {
        return "Result{" +
                "message='" + message + '\'' +
                ", data=" + data +
                ", code=" + code +
                '}';
    }
    //ErrorCode:
    //300
    //400
    //Message
    //Body список данных
}

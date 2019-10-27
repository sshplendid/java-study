package me.shawn.common.model;

import java.time.LocalDateTime;

public class CommonResponseModel<T> {
    private int status;
    private String message;
    private T data;
    private LocalDateTime generateAt;

    public CommonResponseModel() {}
    public CommonResponseModel(int status, String message, T data, LocalDateTime generateAt) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.generateAt = generateAt;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

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

    public LocalDateTime getGenerateAt() {
        return generateAt;
    }

    public void setGenerateAt(LocalDateTime generateAt) {
        this.generateAt = generateAt;
    }

    public static CommonResponseModel.Builder builder() {
        return new Builder();
    }

    static class Builder<T> {
        private CommonResponseModel<T> model;

        public Builder() {
            this.model = new CommonResponseModel<>();
        }

        public Builder status(int status) {
            this.model.setStatus(status);
            return this;
        }

        public Builder message(String message) {
            this.model.setMessage(message);
            return this;
        }

        public Builder data(T data) {
            this.model.setData(data);
            return this;
        }

        public CommonResponseModel build() {
            this.model.setGenerateAt(LocalDateTime.now());
            return this.model;
        }
    }
}

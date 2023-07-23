package uz.cluster.payload.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse {

    private String message;
    private boolean isSuccess;
    private int id;
    private Object object;

    public ApiResponse(boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        this.message = message;
    }

    public ApiResponse(boolean isSuccess, Object object, String message) {
        this.isSuccess = isSuccess;
        this.object = object;
        this.message = message;
    }

    public ApiResponse(boolean isSuccess, int id, String message) {
        this.isSuccess = isSuccess;
        this.id = id;
        this.message = message;
    }

    public ApiResponse(boolean isSuccess, Object object) {
        this.isSuccess = isSuccess;
        this.object = object;
    }
}

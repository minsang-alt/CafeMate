package hello.cafemate.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

//응답(각종 오류 내용등)을 목적으로 하는 DTO
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RespDto<T> {
    private Integer code; // 1 성공 , -1 실패
    private String message;
    private T data;

}

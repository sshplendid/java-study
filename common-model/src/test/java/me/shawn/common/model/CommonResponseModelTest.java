package me.shawn.common.model;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class CommonResponseModelTest {

    @Test
    public void responseModelTest() {
        String data = "hello";
        String message = "junit test";
        int status = 200;
        LocalDateTime beforeGenerate = LocalDateTime.now();

        CommonResponseModel<String> response = CommonResponseModel.builder()
                .message(message)
                .status(status)
                .data(data)
                .build();

        LocalDateTime afterGenerate = LocalDateTime.now();

        assertThat(response.getData()).isEqualTo(data);
        assertThat(response.getMessage()).isEqualTo(message);
        assertThat(response.getStatus()).isEqualTo(status);
        assertThat(response.getGenerateAt()).isAfterOrEqualTo(beforeGenerate);
        assertThat(response.getGenerateAt()).isBeforeOrEqualTo(afterGenerate);

    }

}
package br.com.jhonecmd.courses_api.modules.users.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthUserResponseDTO {
    @Schema(example = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJjb3Vyc2VzLWFwaSIsInN1YiI6IjQzNDZiMDVmLWJkOTUtNDA2Ni04MjU0LWVkNzZiMWFmYWE1NCIsImV4cCI6MTc3NTc1NzIwMSwicm9sZXMiOlsiY29vcmRpbmF0b3IiXX0.XyXLxjw01BmwG3_hzRTM3KqlFDR5XT1FgDPRjQ3G6MXSXDbZds61j35RAngdql9BiS9mNJeAswQKR7i5mmUl9jF3t5GR56nAKvwpc1sNjnylAqemflO6ekZ6drR-3sErdToG7aD7T1krHzxo8BSi4WlN1-D-DYBUl3enb14V_WlGbTAfHK5RKcsvHiIGSgb76KxcDDMFgH5gieDGT4v6olT6wc3fIUhHzwNsl92Q-IANfHJ2al0Uo1FU4xD5YnCFq2DNRsV93Bgr3i-gxtJelPxqaQjwv5L_K2s1X9ZPGeaZG3-CztY34Y4Fi9Q5wlwjU0lGimHxDic7rGPU9uJ4CA")
    private String access_token;

    private Long expiresAt;

    @Schema(example = "Rector")
    private String position;
}

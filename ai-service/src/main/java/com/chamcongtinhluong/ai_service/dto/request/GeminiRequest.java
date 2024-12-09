package com.chamcongtinhluong.ai_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeminiRequest {
    private String prompt;
    private int maxTokens;
}

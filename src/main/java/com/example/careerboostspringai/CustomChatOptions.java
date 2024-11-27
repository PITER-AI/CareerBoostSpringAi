package com.example.careerboostspringai;

import org.springframework.ai.chat.prompt.ChatOptions;
import java.util.List;

public class CustomChatOptions implements ChatOptions {
    private String model;
    private Double frequencyPenalty;
    private Integer maxTokens;
    private Double presencePenalty;
    private List<String> stopSequences;
    private Double temperature;
    private Integer topK;
    private Double topP;

    // Getters
    @Override
    public String getModel() {
        return model;
    }

    @Override
    public Double getFrequencyPenalty() {
        return frequencyPenalty;
    }

    @Override
    public Integer getMaxTokens() {
        return maxTokens;
    }

    @Override
    public Double getPresencePenalty() {
        return presencePenalty;
    }

    @Override
    public List<String> getStopSequences() {
        return stopSequences;
    }

    @Override
    public Double getTemperature() {
        return temperature;
    }

    @Override
    public Integer getTopK() {
        return topK;
    }

    @Override
    public Double getTopP() {
        return topP;
    }

    @Override
    public ChatOptions copy() {
        return new Builder()
                .model(this.model)
                .frequencyPenalty(this.frequencyPenalty)
                .maxTokens(this.maxTokens)
                .presencePenalty(this.presencePenalty)
                .stopSequences(this.stopSequences)
                .temperature(this.temperature)
                .topK(this.topK)
                .topP(this.topP)
                .build();
    }

    // Builder
    public static class Builder {
        private String model;
        private Double frequencyPenalty;
        private Integer maxTokens;
        private Double presencePenalty;
        private List<String> stopSequences;
        private Double temperature;
        private Integer topK;
        private Double topP;

        public Builder model(String model) {
            this.model = model;
            return this;
        }

        public Builder frequencyPenalty(Double frequencyPenalty) {
            this.frequencyPenalty = frequencyPenalty;
            return this;
        }

        public Builder maxTokens(Integer maxTokens) {
            this.maxTokens = maxTokens;
            return this;
        }

        public Builder presencePenalty(Double presencePenalty) {
            this.presencePenalty = presencePenalty;
            return this;
        }

        public Builder stopSequences(List<String> stopSequences) {
            this.stopSequences = stopSequences;
            return this;
        }

        public Builder temperature(Double temperature) {
            this.temperature = temperature;
            return this;
        }

        public Builder topK(Integer topK) {
            this.topK = topK;
            return this;
        }

        public Builder topP(Double topP) {
            this.topP = topP;
            return this;
        }

        public CustomChatOptions build() {
            CustomChatOptions options = new CustomChatOptions();
            options.model = this.model;
            options.frequencyPenalty = this.frequencyPenalty;
            options.maxTokens = this.maxTokens;
            options.presencePenalty = this.presencePenalty;
            options.stopSequences = this.stopSequences;
            options.temperature = this.temperature;
            options.topK = this.topK;
            options.topP = this.topP;
            return options;
        }
    }
}

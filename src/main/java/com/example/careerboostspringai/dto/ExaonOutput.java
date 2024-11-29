package com.example.careerboostspringai.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ExaonOutput {
    private final String content;
    private final List<String> refLists;
}

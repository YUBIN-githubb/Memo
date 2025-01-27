package com.example.memo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class Memo {
    private Long id;
    private String title;
    private String content;
}

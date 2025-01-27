package com.example.memo.entity;

import com.example.memo.dto.MemoRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class Memo {
    private Long id;
    private String title;
    private String content;

    public void update(MemoRequestDto dto) {
        this.title = dto.getTitle();
        this.content = dto.getContent();
    }
}

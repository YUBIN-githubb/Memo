package com.example.memo.controller;

import com.example.memo.dto.MemoRequestDto;
import com.example.memo.dto.MemoResponseDto;
import com.example.memo.entity.Memo;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/memos")
public class MemoController {

    private final Map<Long,Memo> memoList = new HashMap<>();

    //메모 생성
    @PostMapping
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto dto) {
        //식별자가 1씩 증가
        Long memoId = memoList.isEmpty() ? 1 : Collections.max(memoList.keySet()) + 1;

        //요청받은 데이터로 메모 객체 생성
        Memo memo = new Memo(memoId, dto.getTitle(), dto.getContent());

        //데이터베이스에 저장
        memoList.put(memoId, memo);

        return new MemoResponseDto(memo);

    }

    //메모 단건 조회
    @GetMapping("/{id}")
    public MemoResponseDto findMemoById(@PathVariable Long id) {
        Memo memo = memoList.get(id);

        return new MemoResponseDto(memo);
    }

    //메모 전체 조회
    @GetMapping
    public Map<Long,Memo> findAllMemo() {
        return memoList;
    }

    //메모 단건 수정
    @PutMapping("/{id}")
    public MemoResponseDto updateMemoById(@PathVariable Long id, @RequestBody MemoRequestDto dto) {
        Memo memo = memoList.get(id);
        memo.update(dto);

        return new MemoResponseDto(memo);
    }

    //메모 단건 삭제
    @DeleteMapping("/{id}")
    public void deleteMemoById(@PathVariable Long id) {
        memoList.remove(id);
    }

    //메모 전체 삭제
    @DeleteMapping
    public void deleteAllMemo() {
        memoList.clear();
    }

}

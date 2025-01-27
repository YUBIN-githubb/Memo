package com.example.memo.controller;

import com.example.memo.dto.MemoRequestDto;
import com.example.memo.dto.MemoResponseDto;
import com.example.memo.entity.Memo;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/memos")
public class MemoController {

    private final Map<Long,Memo> memoList = new HashMap<>();

    //메모 생성
    @PostMapping
    public ResponseEntity<MemoResponseDto> createMemo(@RequestBody MemoRequestDto dto) {
        //식별자가 1씩 증가
        Long memoId = memoList.isEmpty() ? 1 : Collections.max(memoList.keySet()) + 1;

        //요청받은 데이터로 메모 객체 생성
        Memo memo = new Memo(memoId, dto.getTitle(), dto.getContent());

        //데이터베이스에 저장
        memoList.put(memoId, memo);

        return new ResponseEntity<MemoResponseDto>(new MemoResponseDto(memo), HttpStatus.CREATED);

    }

    //메모 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<MemoResponseDto> findMemoById(@PathVariable Long id) {
        Memo memo = memoList.get(id);

        if (memo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new MemoResponseDto(memo), HttpStatus.OK);
    }

    //메모 전체 조회
    @GetMapping
    public ResponseEntity<List<MemoResponseDto>>  findAllMemos() {
        List<MemoResponseDto> responseList = new ArrayList<>();

        //HashMap<Long,Memo> -> List<MemoResponseDto>
//        for (Memo memo : memoList.values()) {
//            MemoResponseDto memoResponseDto = new MemoResponseDto(memo);
//            responseList.add(memoResponseDto);
//        }

        //Map to List using Stream
        responseList = memoList.values().stream().map(MemoResponseDto::new).toList();

        return new ResponseEntity<List<MemoResponseDto>>(responseList, HttpStatus.OK);
    }

    //메모 단건 전체 수정(제목,내용)
    @PutMapping("/{id}")
    public ResponseEntity<MemoResponseDto> updateMemoById(@PathVariable Long id, @RequestBody MemoRequestDto dto) {
        Memo memo = memoList.get(id);

        if (memo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        //필수값 검증
        if (dto.getTitle() == null || dto.getContent() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        memo.update(dto);

        return new ResponseEntity<>(new MemoResponseDto(memo), HttpStatus.OK);
    }

    //메모 단건 일부 수정
    @PatchMapping("/{id}")
    public ResponseEntity<MemoResponseDto> updateTitle(@PathVariable Long id, @RequestBody MemoRequestDto dto) {
        Memo memo = memoList.get(id);
        if (memo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (dto.getTitle() == null || dto.getContent() != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        memo.updateTitle(dto);

        return new ResponseEntity<>(new MemoResponseDto(memo), HttpStatus.OK);
    }

    //메모 단건 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMemoById(@PathVariable Long id) {
        if (memoList.containsKey(id)) {
            memoList.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //메모 전체 삭제
    @DeleteMapping
    public ResponseEntity<Void> deleteAllMemos() {
        memoList.clear();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

package com.cnu.sw2023.hj.controller;

import com.cnu.sw2023.hj.entity.testboard;
import com.cnu.sw2023.hj.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

// 연습용 컨트롤러 입니다.
@Controller
@RequiredArgsConstructor
public class BoardController {
    private BoardService boardService;

    @GetMapping("/board/write")
    public String boardWriteForm() {
        return "boardwrite";
    }

    @PostMapping("/board/writepro")
    public String boardWritePro(testboard board) {
//        System.out.println(board.getTitle()); // testboard에서 @Data 어노테이션을 사용해 get 쓸 수 있음
        boardService.write(board);

        return "";
    }

}

package com.cnu.sw2023.hj.service;

import com.cnu.sw2023.hj.entity.testboard;
import com.cnu.sw2023.hj.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    private BoardRepository boardRepository;

    public void write(testboard testboard) {
        boardRepository.save(testboard);
    }
}

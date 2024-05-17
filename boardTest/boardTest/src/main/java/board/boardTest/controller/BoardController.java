package board.boardTest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    /**
     * 게시글 전체를 가져오는 api
     *
     * @return
     */
    @GetMapping("/board")
    public String boardView() {
        //추후에 추가할 html 페이지
        return "asdf";
    }
}

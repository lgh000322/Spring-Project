package board.boardTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BoardTestApplication {

	/**
	 *TODO
	 * 1. 현재 세션으로 구현한 validation이 적용되어 있음. 차후에 이걸 spring의 validator로 변경해야 함.
	 * 2. 대댓글 조회기능 구현해야 함.
	 */
	public static void main(String[] args) {
		SpringApplication.run(BoardTestApplication.class, args);
	}

}

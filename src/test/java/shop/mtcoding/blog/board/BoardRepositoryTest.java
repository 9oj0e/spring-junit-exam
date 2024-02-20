package shop.mtcoding.blog.board;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@Import(BoardRepository.class)
@DataJpaTest // DB 관련 객체들이 IoC에 뜬다.
public class BoardRepositoryTest {

    @Autowired // Test에서 DI하는 코드
    private BoardRepository boardRepository;

    @Test
    public void insert_test() { // TestMethod = parameter void, return void.
        // given
        String title = "제목1";
        String content = "내용10";
        String author = "이순신";
        // when
        boardRepository.insert(title, content, author);
        // then
    } // Rollback

    @Test
    public void selectAll_test() {
        List<Board> boardList = boardRepository.selectAll();
        //boardRepository에서 데이터를 찾을 수 없으면?
        //List<Board> boardList = new ArrayList<>();

        System.out.println(boardList);
        Assertions.assertThat(boardList.size()).isEqualTo(10);
    }

    @Test
    public void selectOne_test() { // TestMethod = parameter void, return void.
        // given
        int id = 1;
        // when
        Board board = boardRepository.selectOne(id);
        // then (상태 검사)
        System.out.println(board);
        Assertions.assertThat(board.getTitle()).isEqualTo("제목1");
        Assertions.assertThat(board.getContent()).isEqualTo("내용1");
        Assertions.assertThat(board.getAuthor()).isEqualTo("홍길동");
    } // Rollback

    @Test
    public void update_test() {
        // given
        String title = "수정된 제목";
        String content = "수정된 내용";
//        String author = "수정자";
        int id = 1;
        // when
        boardRepository.update(title, content, id);
//        boardRepository.update(title, content, author, id);

        // then
        Board board = boardRepository.selectOne(id);
        System.out.println(board);
        Assertions.assertThat(board.getTitle()).isEqualTo("수정된 제목");
        Assertions.assertThat(board.getContent()).isEqualTo("수정된 내용");
//        Assertions.assertThat(board.getAuthor()).isEqualTo("수정자");
    }

    @Test
    public void delete_test() {
        // given
        int id = 1;
        // when
        boardRepository.delete(id);
        // then
        List<Board> boardList = boardRepository.selectAll();
        System.out.println(boardList.size());
        Assertions.assertThat(boardList.size()).isEqualTo(7);
    }
}

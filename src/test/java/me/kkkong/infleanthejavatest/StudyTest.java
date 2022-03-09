package me.kkkong.infleanthejavatest;

import org.junit.jupiter.api.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class) // 메서드명의 언더스코어를 띄어쓰기로 변경
class StudyTest {

    @Test
    @DisplayName("스터디 생성")
    void create() {
        Study study = new Study(10);

        // 실행 시간 테스트
        assertTimeout(Duration.ofMillis(100), () -> {
            new Study(10);
            Thread.sleep(300);
        });

        // TODO ThreadLocal 확인 필요, 얘는 즉시 종료 됨
        assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
            new Study(10);
            Thread.sleep(300);
        });

        // 예외 테스트
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new Study(-10));
        assertEquals("limit은 0보다 커야한다.", exception.getMessage());

        // 전체 테스트, 람다 형식으로
        assertAll(
                () -> assertNotNull(study),
                () -> assertEquals(StudyStatus.DRAFT, study.getStatus(), "스터디를 처음 만들면, 상태 값이 DRAFT여야 한다."),
                () -> assertTrue(study.getLimit() > 0, "스터디 최대 참석 가능 인원은 0보다 커야한다.")
        );
    }

    @Test
    @Disabled // 미사용 시
    void create1() {
        Study study = new Study(-10);
        assertNotNull(study);
        System.out.println("create1");
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("실행 전에 한 번만 실행 됨");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("실행 후에 한 번만 실행 됨");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("before each");
    }

    @AfterEach
    void afterEach() {
        System.out.println("after each");
    }
}
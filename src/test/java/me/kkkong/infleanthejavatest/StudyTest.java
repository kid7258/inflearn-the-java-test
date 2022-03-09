package me.kkkong.infleanthejavatest;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class) // 메서드명의 언더스코어를 띄어쓰기로 변경
class StudyTest {

    @Test
    @DisplayName("태깅 테스트 - fast")
    @Tag("fast")
    void tagging_test_fast() {
        // 실행 환경에서 tags 값을 설정해서 일치하는 tags만 실행 가능
    }

    @Test
    @DisplayName("태깅 테스트 - slow")
    @Tag("slow")
    void tagging_test_slow() {

    }

    @Test
    @DisplayName("스터디 만들기")
    @EnabledOnOs({OS.MAC, OS.LINUX}) // OS 별 테스트 가능
    void create_new_study() {
        // @EnabledIfEnvironmentVariable(named = "TEST_ENV", matches = "LOCAL") 로 변경 가능
        String testEnv = System.getenv("TEST_ENV");
        System.out.println("testEnv = " + testEnv);
        assumeTrue("LOCAL".equalsIgnoreCase(testEnv));

        assumingThat("LOCAL".equalsIgnoreCase(testEnv), () -> {
            Study study = new Study(10);
            assertTrue(study.getLimit() > 0);
        });
    }

    @Test
    @DisplayName("스터디 생성")
    @DisabledOnOs(OS.WINDOWS)
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
package me.kkkong.infleanthejavatest;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class) // 메서드명의 언더스코어를 띄어쓰기로 변경
class StudyTest {

    @Test
    @DisplayName("스터디 생성")
    void create() {
        Study study = new Study();
        assertNotNull(study);
        System.out.println("create");
    }

    @Test
    @Disabled // 미사용 시
    void create1() {
        Study study = new Study();
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
package me.kkkong.infleanthejavatest;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class) // 메서드명의 언더스코어를 띄어쓰기로 변경
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // 얘가 없으면 기본적으로 메서드마다 인스턴스를 생성함
class StudyTest {

    int value = 1;

    @DisplayName("파라미터를 이용한 반복 테스트")
    @ParameterizedTest(name = "{displayName} {index} message={0}")
    @CsvSource({"10, '자바 스터디", "20, 스프링"})
    void parameterizedTestByCsvSource(@AggregateWith(StudyAggregator.class) Study study) {
        System.out.println(this);
        System.out.println(value++);
        System.out.println(study);
    }

    static class StudyAggregator implements ArgumentsAggregator {
        @Override
        public Object aggregateArguments(ArgumentsAccessor argumentsAccessor, ParameterContext parameterContext) throws ArgumentsAggregationException {
            System.out.println(this);
            return new Study(argumentsAccessor.getInteger(0), argumentsAccessor.getString(1));
        }
    }

    @DisplayName("파라미터를 이용한 반복 테스트")
    @ParameterizedTest(name = "{displayName} {index} message={0}")
    @ValueSource(ints = {10, 20, 40})
    // @NullAndEmptySource
    void parameterizedTest(Integer limit) {
        System.out.println(limit);
    }

    @DisplayName("파라미터를 이용한 반복 테스트")
    @ParameterizedTest(name = "{displayName} {index} message={0}")
    @ValueSource(ints = {10, 20, 40})
    void parameterizedTestByConverter(@ConvertWith(StudyConverter.class) Study study) {
        System.out.println(study);
    }

    // 하나의 파라미터만 가능
    static class StudyConverter extends SimpleArgumentConverter {
        @Override
        protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
            assertEquals(Study.class, targetType, "Can only convert to Study");
            return new Study(Integer.parseInt(source.toString()));
        }
    }

    @DisplayName("반복 테스트")
    @RepeatedTest(value = 10, name = "{displayName}, {currentRepetition}/{totalRepetitions}")
    void repeatTEST(RepetitionInfo repetitionInfo) {
        System.out.println("test " + repetitionInfo.getCurrentRepetition() + "/" +
                repetitionInfo.getTotalRepetitions());
    }

    @FastTest
    @DisplayName("태깅 테스트 - fast")
    @Tag("fast")
    void tagging_test_fast() {
        // 실행 환경에서 tags 값을 설정해서 일치하는 tags만 실행 가능
    }

    @SlowTest
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
            Thread.sleep(30);
        });

        // TODO ThreadLocal 확인 필요, 얘는 즉시 종료 됨
        assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
            new Study(10);
            Thread.sleep(30);
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

    // @TestInstance(TestInstance.Lifecycle.PER_CLASS) 얘로 인해 static으로 생성할 필요가 없음
    @BeforeAll
    void beforeAll() {
        System.out.println("실행 전에 한 번만 실행 됨");
    }

    // @TestInstance(TestInstance.Lifecycle.PER_CLASS) 얘로 인해 static으로 생성할 필요가 없음
    @AfterAll
    void afterAll() {
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
package me.kkkong.infleanthejavatest.study;

import me.kkkong.infleanthejavatest.domain.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, Long> {
}

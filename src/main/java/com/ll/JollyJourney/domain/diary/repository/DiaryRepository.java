package com.ll.JollyJourney.domain.diary.repository;

import com.ll.JollyJourney.domain.diary.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface DiaryRepository extends JpaRepository<Diary, Long> {

    Optional<Diary> findById(Long id);
}
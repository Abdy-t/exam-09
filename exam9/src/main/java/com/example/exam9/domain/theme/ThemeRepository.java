package com.example.exam9.domain.theme;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ThemeRepository extends JpaRepository<Theme, Integer> {
    @Query("select p from Theme as p order by p.date asc")
    Page<Theme> findOrderByDateAsc(Pageable pageable);
}

package com.example.exam9.domain.theme;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ThemeService {
    private final ThemeRepository themeRepository;

    public Page<ThemeDTO> getThemes(Pageable pageable) {
        return themeRepository.findAll(pageable)
                .map(ThemeDTO::from);
    }

    public ThemeDTO getTheme(int id) {
        return ThemeDTO.from(themeRepository.findById(id).get());
    }
}

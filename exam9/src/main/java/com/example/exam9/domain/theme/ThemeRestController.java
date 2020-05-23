package com.example.exam9.domain.theme;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ThemeRestController {
    private final ThemeService themeService;

    @GetMapping
    public List<ThemeDTO> getCategories(Pageable pageable) {
        return themeService.getThemes(pageable).getContent();
    }

    @GetMapping("/{id:\\d+?}")
    public ThemeDTO getCategory(@PathVariable int id) {
        return themeService.getTheme(id);
    }

//    @GetMapping("/{id:\\d+}/books")
//    public List<Book> getBooks(@PathVariable @Min(3) int id, Pageable pageable) {
//        return bs.getBooks(id, pageable).getContent();
//    }
}

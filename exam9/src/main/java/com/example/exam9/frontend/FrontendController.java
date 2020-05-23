package com.example.exam9.frontend;

import com.example.exam9.domain.comment.Comment;
import com.example.exam9.domain.comment.CommentRepository;
import com.example.exam9.domain.comment.CommentService;
import com.example.exam9.domain.theme.Theme;
import com.example.exam9.domain.theme.ThemeDTO;
import com.example.exam9.domain.theme.ThemeRepository;
import com.example.exam9.domain.theme.ThemeService;
import com.example.exam9.domain.user.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.apache.tomcat.util.http.fileupload.FileUploadBase.MULTIPART_FORM_DATA;

@Controller
//@RequestMapping
@AllArgsConstructor
public class FrontendController {

    private final UserService userService;
    private final PropertiesService propertiesService;
    private final ThemeService themeService;
    private final CommentService commentService;
    private final UserRepository userRepository;
    private final ResetRepository resetRepository;
    private final ThemeRepository themeRepository;
    private final CommentRepository commentRepository;

    private static <T> void constructPageable(Page<T> list, int pageSize, Model model, String uri) {
        if (list.hasNext()) {
            model.addAttribute("nextPageLink", constructPageUri(uri, list.nextPageable().getPageNumber(), list.nextPageable().getPageSize()));
        }

        if (list.hasPrevious()) {
            model.addAttribute("prevPageLink", constructPageUri(uri, list.previousPageable().getPageNumber(), list.previousPageable().getPageSize()));
        }

        model.addAttribute("hasNext", list.hasNext());
        model.addAttribute("hasPrev", list.hasPrevious());
        model.addAttribute("items", list.getContent());
        model.addAttribute("defaultPageSize", pageSize);
    }

    private static String constructPageUri(String uri, int page, int size) {
        return String.format("%s?page=%s&size=%s", uri, page, size);
    }

    @GetMapping
    public String getMainPage(Model model, Pageable pageable, HttpServletRequest uriBuilder) {
        Page<Theme> themes = themeRepository.findOrderByDateAsc(pageable);
        var uri = uriBuilder.getRequestURI();
        constructPageable(themes, propertiesService.getDefaultPageSize(), model, uri);
        return "index";
    }

    @GetMapping("/categories/{theme_id}")
    public String brandPage(@PathVariable int theme_id, Model model, HttpServletRequest uriBuilder, Pageable pageable) {
        ThemeDTO themeDTO = themeService.getTheme(theme_id);
        var commentDTO = commentService.getPageComments(theme_id, pageable);
        var uri = uriBuilder.getRequestURI();
        constructPageable(commentDTO, propertiesService.getDefaultPageSize(), model, uri);
//        themeDTO.setComment(commentDTO);
        model.addAttribute("theme", themeDTO);
        return "theme-page";
    }
    @GetMapping("/profile")
    public String pageCustomerProfile(Model model, Principal principal)
    {
        var user = userService.getByEmail(principal.getName());
        model.addAttribute("dto", user);
        return "profile";
    }

    @GetMapping("/register")
    public String pageRegisterCustomer(Model model) {
        if (!model.containsAttribute("dto")) {
            model.addAttribute("dto", new UserRegisterForm());
        }

        return "register";
    }

    @PostMapping("/register")
    public String registerPage(@Valid UserRegisterForm userRequestDto,
                               BindingResult validationResult,
                               RedirectAttributes attributes) {
        attributes.addFlashAttribute("dto", userRequestDto);

        if (validationResult.hasFieldErrors()) {
            attributes.addFlashAttribute("errors", validationResult.getFieldErrors());
            return "redirect:/register";
        }

        userService.register(userRequestDto);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false, defaultValue = "false") Boolean error, Model model) {
        model.addAttribute("error", error);
        return "login";
    }

    @GetMapping("/forgot-password")
    public String pageForgotPassword(Model model) {
        return "forgot";
    }

    @PostMapping("/forgot-password")
    public String submitForgotPasswordPage(@RequestParam("email") String email,
                                           RedirectAttributes attributes) {

        if (!userRepository.existsByEmail(email)) {
            attributes.addFlashAttribute("errorText", "Entered email does not exist!");
            return "redirect:/";
        }

        PasswordResetToken pToken = PasswordResetToken.builder()
                .user(userRepository.findByEmail(email).get())
                .token(UUID.randomUUID().toString())
                .build();

        resetRepository.deleteAll();
        resetRepository.save(pToken);

        return "redirect:/forgot-success";
    }

    @GetMapping("/forgot-success")
    public String pageResetPassword(Model model) {
        return "forgot-success";
    }

    @PostMapping("/theme/add")
    public String addTheme(@RequestParam("title") String title, Principal principal) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Theme theme = new Theme();
            theme.setTitle(title);
            theme.setUser(userRepository.findByEmail(principal.getName()).get());
            theme.setDate(LocalDateTime.now());
            themeRepository.save(theme);
            return "redirect:/";
        } catch (Exception ex) {

        }
        return "error-login";
    }
    @RequestMapping(value = "/comment/add", method = RequestMethod.POST, consumes=MULTIPART_FORM_DATA)
    public String addComment(@RequestParam("comment") String comment, @RequestParam("theme_id") int theme_id, Principal principal) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            if (userDetails.getUsername() == null) {
                return "error-login";
            }
            Comment comment1 = new Comment();
            comment1.setText(comment);
            comment1.setUser(userRepository.findByEmail(principal.getName()).get());
            comment1.setTheme(themeRepository.findById(theme_id).get());
            comment1.setDate(LocalDateTime.now());
            commentRepository.save(comment1);
            Theme theme = themeRepository.findById(theme_id).get();
            theme.setCount(theme.getCount() + 1);
            themeRepository.save(theme);
            return "redirect:/categories/" + theme_id;
        } catch (Exception ex) {

        }
        return "error-login";
    }
//    @PostMapping("/comment/add")
//    public String addComment(@RequestParam("text") String text, @RequestParam("theme_id") int theme_id, Principal principal) {
//        try {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//            Comment comment = new Comment();
//            comment.setText(text);
//            comment.setUser(userRepository.findByEmail(principal.getName()).get());
//            comment.setTheme(themeRepository.findById(theme_id).get());
//            comment.setDate(LocalDateTime.now());
//            commentRepository.save(comment);
//            Theme theme = themeRepository.findById(theme_id).get();
//            theme.setCount(theme.getCount() + 1);
//            themeRepository.save(theme);
//            return "redirect:/categories/" + theme_id;
//        } catch (Exception ex) {
//
//        }
//        return "error-login";
//    }

//    @RequestMapping(value = "/page/comment", method = RequestMethod.POST, consumes=MULTIPART_FORM_DATA)
//    public String commentDemo(@RequestParam("idUser") String idUser, @RequestParam("idPhoto") String idPhoto, @RequestParam("comment") String comment) {
//        System.out.println("User id : " + idUser);
//        System.out.println("idPhoto : " + idPhoto);
//        System.out.println("comment : " + comment);
//        comService.saveNewComment(idUser, idPhoto, comment);
//        return "redirect:/page/";
//    }

}

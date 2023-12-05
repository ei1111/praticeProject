package notice.pratice.controller;

import lombok.RequiredArgsConstructor;
import notice.pratice.domain.dataResult.Message;
import notice.pratice.domain.form.UserForm;
import notice.pratice.entity.User;
import notice.pratice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/save")
    public ResponseEntity<Message> createUser(@RequestBody UserForm userForm) {
        return userService.save(userForm);
    }

    @GetMapping
    public List<UserForm> selectAllUsers() {
        List<User> users = userService.selectAllUsers();
        return users.stream().map(UserForm::new).collect(Collectors.toList());
    }
}

package me.daniel.controller.user;

import me.daniel.domain.UserVO;
import me.daniel.service.UserService;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JoinController {

    private final UserService userService;

    public JoinController(UserService userService) {
        this.userService = userService;
    }

    // 회원 가입 처리
    @PostMapping("/user/join")
    public UserVO joinAction(@ModelAttribute UserVO userVO) {

        userService.addUser(userVO);
        return userService.getUser(userVO.getUserNo());
    }
}

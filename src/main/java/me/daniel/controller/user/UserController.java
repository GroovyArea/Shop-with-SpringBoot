package me.daniel.controller.user;

import lombok.RequiredArgsConstructor;
import me.daniel.domain.UserVO;
import me.daniel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

/*    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }*/

    @GetMapping("/insertTest")
    public Integer insertTest() {

        userService.insertTest(new UserVO(1, "김남영"));
        return 1;
    }


    @GetMapping("/insertTest2")
    public Integer insertTest2() {

        userService.insertTest(new UserVO(2, "김공룡"));

        return 2;
    }

    @GetMapping("/insertTest3")
    public Integer insertTest3() {

        userService.insertTest(new UserVO(3, "김냐냐"));

        return 3;
    }
}

package notice.pratice.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import notice.pratice.domain.dataResult.Message;
import notice.pratice.domain.form.UserForm;
import notice.pratice.entity.User;
import notice.pratice.global.error.exception.ErrorCode;
import notice.pratice.global.error.exception.domainException.UserValidException;
import notice.pratice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Api(tags = {"0. 사용자 등록 및 모든 사용자 조회"})
public class UserController {
    private final UserService userService;

    @PostMapping("/save")
    @ApiOperation(value = "회원 가입 기능" , notes = "DB에 등록된 ID는 회원가입을 할 수 없습니다.")
    @ApiResponses({@ApiResponse(code=201,message="저장완료"),@ApiResponse(code=-101,message="가입 폼 누락"),@ApiResponse(code=-103,message="토큰에러") ,@ApiResponse(code=500,message="서버문제발생")})
    public ResponseEntity<Message> createUser(@RequestBody  @Valid @ApiParam(value="writerId: 사용자 아이디\n" + "writerPwd: 사용자 패스워드\n" + "writerName: 사용자 이름" ,required=true) UserForm userForm , BindingResult result) {
        if (result.hasErrors()) {
            throw new UserValidException(ErrorCode.USER_VALID);
        }
        return userService.save(userForm);
    }

    @GetMapping
    @ApiOperation(value = "모든 사용자 조회 기능" , notes = "토큰 확인 후 모든 사용자를 조회 할 수 있습니다.")
    @ApiResponses({@ApiResponse(code=200,message="조회성공"),@ApiResponse(code=-103,message="토큰에러") ,@ApiResponse(code=500,message="서버문제발생")})
    public List<UserForm> selectAllUsers() {
        List<User> users = userService.selectAllUsers();
        return users.stream().map(UserForm::new).collect(Collectors.toList());
    }
}

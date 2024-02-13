package notice.pratice.service;

import lombok.RequiredArgsConstructor;
import notice.pratice.domain.dataResult.Message;
import notice.pratice.domain.form.UserForm;
import notice.pratice.entity.User;
import notice.pratice.global.error.exception.domainException.UserException;
import notice.pratice.repository.UserRepository;
import notice.pratice.utils.AesClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    @Transactional
    public ResponseEntity<Message> save(UserForm userForm) {
        //아이디 암호화
        String id = AesClass.encrypt(userForm.getWriterId());

        //비밀번호 암호화
        String pwd = AesClass.encrypt(userForm.getWriterPwd());
        //이름 암호화
        String name = AesClass.encrypt(userForm.getWriterPwd());

        //아이디 중복 체크
        checkUser(id);

        userRepository.save(
                    User.builder()
                    .writerId(id)
                    .writerPwd(pwd)
                    .writerName(name).build()
        );

        return  Message.createMessage("아이디 저장 완료", "1", HttpStatus.CREATED);
    }

    private void checkUser(String encryptId) {
        //유저 조회
        Integer cnt = userRepository.selectUser(encryptId);

        if (cnt > 0) {
            throw new UserException("중복된 회원이 있습니다", "-101");
        }
    }

    public List<User> selectAllUsers() {
        return userRepository.findAll();
    }
}

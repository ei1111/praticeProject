package notice.pratice.repository;

import notice.pratice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select count(u) from User u where u.writerId = :writerId")
    Integer selectUser(@Param("writerId") String writerId);

    @Query("select u from User u where u.writerId = :writerId and u.writerPwd = :writerPwd")
    User selectUserCheck(@Param("writerId") String writerId, @Param("writerPwd") String writerPwd);
}

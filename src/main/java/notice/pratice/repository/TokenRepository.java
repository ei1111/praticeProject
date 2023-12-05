package notice.pratice.repository;

import notice.pratice.entity.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TokenRepository extends JpaRepository<UserToken, Integer> {
    @Query("select u from UserToken u where u.writerId = :writerId")
    UserToken findByTokenUser(@Param("writerId") String writerId);

    @Query("select u from UserToken u where u.accessToken = :accessToken")
    UserToken findByAccessToken(@Param("accessToken") String accessToken);

    @Query("select u from UserToken u where u.refreshToken = :refreshToken")
    UserToken findByRefreshToken(@Param("refreshToken") String refreshToken);
}

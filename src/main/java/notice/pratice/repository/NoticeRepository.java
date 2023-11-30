package notice.pratice.repository;

import notice.pratice.entity.Notice;
import notice.pratice.repository.custom.NoticeRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice,Integer>, NoticeRepositoryCustom {
}

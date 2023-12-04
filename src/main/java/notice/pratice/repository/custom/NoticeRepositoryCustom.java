package notice.pratice.repository.custom;

import notice.pratice.domain.pageData.PageModel;
import notice.pratice.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;


public interface NoticeRepositoryCustom {
    List<Notice> findAllList(Map<String, String> search);
    Page<Notice> searchPageSimple(Pageable pageable, PageModel pageModel);
}

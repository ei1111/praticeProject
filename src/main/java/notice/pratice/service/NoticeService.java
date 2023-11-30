package notice.pratice.service;

import lombok.RequiredArgsConstructor;
import notice.pratice.domain.PageRequest;
import notice.pratice.domain.dataResult.Message;
import notice.pratice.domain.form.NoticeForm;
import notice.pratice.entity.Notice;
import notice.pratice.exception.domainException.NoticeException;
import notice.pratice.repository.NoticeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {
    private final NoticeRepository noticeRepository;
    @Transactional
    public ResponseEntity<Message> save(NoticeForm form) {
        noticeRepository.save(
                  Notice.builder()
                        .title(form.getTitle())
                        .note(form.getNote())
                        .content(form.getContent()).build()
        );

        return Message.createMessage("공지사항 등록 완료", "1", HttpStatus.CREATED);
    }

    public Map<String, String> findContent(Integer id){
        Notice notice = noticeRepository.findById(id).orElseThrow(() -> new NoticeException("공지사항이 없습니다", "404"));
        Map<String, String> map = new HashMap<>();
        map.put("content", notice.getContent());

        return map;
    }
    @Transactional
    public ResponseEntity<Message> delete(Integer id) {
        noticeRepository.deleteById(id);
        return Message.createMessage("공지사항 삭제 성공", "1", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Message> update(NoticeForm form) {
        Notice notice = noticeRepository.findById(form.getId()).orElseThrow(() -> new NoticeException("공지사항이 없습니다", "404"));
        notice.setTitle(form.getTitle());
        notice.setContent(form.getContent());
        notice.setNote(form.getNote());

        return Message.createMessage("공지사항 수정 성공", "1", HttpStatus.OK);
    }

    public Page<Notice> findAllList(Pageable pageRequest, String... orderCondition) {
        return noticeRepository.searchPageSimple(pageRequest, orderCondition);
    }
    @Transactional
    public ResponseEntity<Message> multiDelete(List<Integer> list) {
        for (Integer id : list) {
            noticeRepository.deleteById(id);
        }

        return Message.createMessage("공지사항 삭제 성공", "1", HttpStatus.OK);
    }

    /* 데이터 넣어 주는 코드*/
    @Transactional
    public void testPage(String title, String note, String content) {
        noticeRepository.save(
                Notice.builder()
                        .title(title)
                        .note(note)
                        .content(content)
                        .build());
    }
}

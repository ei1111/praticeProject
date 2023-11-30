package notice.pratice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import notice.pratice.domain.PageRequest;
import notice.pratice.domain.dataResult.Message;
import notice.pratice.domain.form.NoticeForm;
import notice.pratice.entity.Notice;
import notice.pratice.exception.domainException.NoticeException;
import notice.pratice.service.NoticeService;
import notice.pratice.utils.CheckType;
import notice.pratice.utils.methods.CheckOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {
    private final NoticeService noticeService;

    /*
     * 공지사항 등록
     * */
    @PostMapping("/save")
    public ResponseEntity<Message> save(@RequestBody @Valid NoticeForm form, BindingResult result) {
        ResponseEntity<Message> resultResponse;
        if (result.hasErrors()) {
            throw new NoticeException(result.getFieldError().getDefaultMessage(), "-201");
        }

        return noticeService.save(form);
    }

    /*
     * 공지사항 단건 조회
     * */
    @GetMapping("/content/{id}")
    public Map selectContent(@PathVariable("id") Integer id) {
        return noticeService.findContent(id);
    }

    /*
     * 공지사항 수정
     * */
    @PostMapping("/update")
    public ResponseEntity<Message> update(@RequestBody NoticeForm form) {
        return noticeService.update(form);
    }

    /*
     * 공지사항 삭제
     * */
    @GetMapping("/delete/{id}")
    public ResponseEntity<Message> delete(@PathVariable Integer id) {
       return noticeService.delete(id);
    }


    /*공지 사항 다중 삭제*/
    @PostMapping("/multiDelete")
    public ResponseEntity<Message> multiDelete(@RequestBody Map<String, List<Integer>> multiMap) {
        List<Integer> list = new ArrayList(multiMap.get("multipleList"));
       return noticeService.multiDelete(list);
    }

    /*
    * 공지사항 리스트를 조회
        검색어로 제목을 사용하여, 제목의 일부분이 포함된 결과를 조회할 수 있습니다.
        검색어가 없으면 전체 리스트를 조회하면 됩니다.
        기본 정렬조건은 생성시각(createAt)으로 내림차순으로 정렬합니다.(최신순으로 정렬)
        정렬조건에 따라 제목순, 생성시각 순으로 정렬할 수 있습니다.
        정렬순서에 따라 오름차순 또는 내림차순을 선택하여 정렬할 수 있습니다.
      페이징 처리를 할 수 있습니다.
        기본값으로는 1page이며 page당 조회건수 10건입니다.
        page당 조회건수는 최대 20건으로 제한해주세요.
    * */
    @GetMapping("/lists")
    public Page<NoticeForm> findAllList(
                                        @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                        @RequestParam(value = "size", required = false, defaultValue = "3") int size,
                                        @RequestParam(value = "titleName", required = false) String titleName,
                                        @RequestParam(value = "title", required = false) String title,
                                        @RequestParam(value = "createTime", required = false) String createTime
    ) {
/*      for (int i = 1; i <= 26; i++) {
            String title =  String.valueOf((char) (64 + i));
            String note = "team" + i;
            String content = String.valueOf((char) (64 + i));

            noticeService.testPage(title, note, content);
        }*/
        //title과 createTime 검증 코드
        CheckOrder checkOrder = new CheckOrder();
        title = checkOrder.checkOrder(title);
        createTime = checkOrder.checkOrder(createTime);

        notice.pratice.domain.PageRequest pageRequest = new notice.pratice.domain.PageRequest();
        pageRequest.setPage(page);
        pageRequest.setSize(size);
        Pageable pageable = pageRequest.of();
        //DTO는 엔티티를 받아도 괜찮다
        Page<Notice> notices = noticeService.findAllList(pageable, titleName, title, createTime);
        return notices.map(notice -> new NoticeForm(notice));
    }

}

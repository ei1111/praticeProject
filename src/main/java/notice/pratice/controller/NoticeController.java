package notice.pratice.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import notice.pratice.domain.PageRequest;
import notice.pratice.domain.dataResult.Message;
import notice.pratice.domain.form.NoticeForm;
import notice.pratice.domain.pageData.PageModel;
import notice.pratice.entity.Notice;
import notice.pratice.exception.domainException.NoticeException;
import notice.pratice.service.NoticeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = {"2. 공지사항 등록, 공지 사항 내용, 공지사항 단건 수정, 공지사항 단건 삭제, 공지사항 다중 삭제, 공지사항 페이징 처리 후 리스트로 조회"})
@RequestMapping("/notices")
public class NoticeController {
    private final NoticeService noticeService;

    /*
     * 공지사항 등록
     * */
    @PostMapping("/save")
    @ApiOperation(value = "공지사항 등록" , notes = "토큰 확인 후 공지사항을 등록 할 수 있습니다.")
    @ApiResponses({@ApiResponse(code=200,message="저장완료"),@ApiResponse(code=-201,message="저장 폼 누락"),@ApiResponse(code=-103,message="토큰에러") ,@ApiResponse(code=500,message="서버문제발생")})
    public ResponseEntity<Message> save(@RequestBody @Valid @ApiParam(value="title: 제목\n" + "content: 내용\n" + "note: 비고" ,required=true)  NoticeForm form, BindingResult result) {
        if (result.hasErrors()) {
            throw new NoticeException(result.getFieldError().getDefaultMessage(), "-201");
        }

        return noticeService.save(form);
    }

    /*
     * 공지사항 단건 조회
     * */
    @GetMapping("/contents/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "공지사항 키", required = true,  dataType = "string")
    })
    @ApiOperation(value = "공지사항 단건 조회" , notes = "토큰 확인 후 공지사항 내용을 확인 할 수 있습니다.")
    @ApiResponses({@ApiResponse(code=200,message="조회완료"),@ApiResponse(code=-103,message="토큰에러") ,@ApiResponse(code=500,message="서버문제발생")})
    public Map<String, String> selectContent(@PathVariable("id") Integer id) {
        return noticeService.findContent(id);
    }

    /*
     * 공지사항 수정
     * */
    @PutMapping("/update")
    @ApiOperation(value = "공지사항 단건 수정" , notes = "토큰 확인 후 공지사항 내용을 수정 할 수 있습니다.")
    public ResponseEntity<Message> update(@RequestBody @ApiParam(value="수정 할 공지사항",required=true)NoticeForm form) {
        return noticeService.update(form);
    }

    /*
     * 공지사항 삭제
     * */
    @DeleteMapping("/delete/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "공지사항 키", required = true,  dataType = "string"),
    })
    @ApiOperation(value = "공지사항 단건 삭제" , notes = "토큰 확인 후 공지사항 단건을 삭제 할 수 있습니다.")
    public ResponseEntity<Message> delete(@PathVariable Integer id) {
        return noticeService.delete(id);
    }


    /*공지 사항 다중 삭제*/
    @DeleteMapping("/multiDelete")
    @ApiOperation(value = "공지사항 다중 삭제" , notes = "토큰 확인 후 공지사항을 다중으로 삭제 할 수 있습니다.")
    public ResponseEntity<Message> multiDelete(@RequestBody  Map<String, List<Integer>> multiMap) {
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
    @ApiOperation(value = "공지사항 페이징 리스트 조회" , notes = "기본값으로는 1page이며 page당 조회건수 10건입니다.\n"
                                                               +"page당 조회건수는 최대 20건으로 제한합니다.\n"
                                                                + "기본 정렬조건은 생성시각(createAt)으로 내림차순으로 정렬합니다.\n"
                                                                + "정렬조건에 따라 제목순, 생성시각 순으로 정렬할 수 있습니다.\n"
                                                                + "titleName에 ASC 또는 DESC 입력, title에 제목 입력, createTime에 ASC 또는 DESC 입력"  )
    public Page<NoticeForm> findAllList(@ModelAttribute PageModel pageModel) throws InterruptedException {
        /*  for (int i = 1; i <= 26; i++) {
                String title = String.valueOf((char) (64 + i));
                String note = "team" + i;
                String content = String.valueOf((char) (64 + i));
                Thread.sleep(1000);
                noticeService.testPage(title, note, content);
            }
        */
        //title과 createTime 검증 코드
        pageModel.setTitle(PageModel.checkOrder(pageModel.getTitle()));
        pageModel.setCreateTime(PageModel.checkOrder(pageModel.getCreateTime()));

        PageRequest pageRequest = new PageRequest();
        pageRequest.setPage(pageModel.getPage());
        pageRequest.setSize(pageModel.getSize());
        Pageable pageable = pageRequest.of();
        //DTO는 엔티티를 받아도 괜찮다
        Page<Notice> notices = noticeService.findAllList(pageable, pageModel);
        return notices.map(NoticeForm::new);

    }
}


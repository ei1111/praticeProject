package notice.pratice.repository.impl;

import com.google.common.collect.Lists;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import notice.pratice.domain.OrderByNull;
import notice.pratice.domain.pageData.PageModel;
import notice.pratice.entity.Notice;
import notice.pratice.entity.QNotice;
import notice.pratice.repository.custom.NoticeRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static notice.pratice.entity.QNotice.*;
import static notice.pratice.entity.QNotice.notice;

@Slf4j
@RequiredArgsConstructor
public class NoticeRepositoryImpl implements NoticeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    /*
    map -> 담겨오는 데이터
    기본 정렬조건 생성시간 내림 차순
    * titleName -> 검색 내용
    * createTime : 생성시간
    * title(제목)-> ASC , DESC
    * */
    @Override
    public List<Notice> findAllList(Map<String, String> map) {
        QNotice notice = QNotice.notice;

        //검색 내용
        String titleName = map.get("titleName");

        //제목
        String title = map.get("title");

        //생성 시간
        String createTime = map.get("createTime");

        return queryFactory
                .selectFrom(notice)
                .where(titleContains(titleName))
                .orderBy(titleOrder(title), createTimeOrder(createTime))
                .limit(1000)
                .fetch();
    }

    @Override
    public Page<Notice> searchPageSimple(Pageable pageable, PageModel pageModel) {
        QNotice notice = QNotice.notice;

        String titleName = pageModel.getTitleName();
        String title = pageModel.getTitle();
        String createTime = pageModel.getCreateTime();

        //컨텐츠
        List<Notice> content = queryFactory
                .selectFrom(notice)
                .where(titleContains(titleName))
                .orderBy(titleOrder(title), createTimeOrder(createTime))
                //몇번부터 스킵하고 몇번 부터 할것인지
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Notice> countQuery = queryFactory
                .selectFrom(notice)
                .where(titleContains(titleName))
                .orderBy(titleOrder(title), createTimeOrder(createTime));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }


    private BooleanExpression titleContains(String titleName) {
        return StringUtils.hasText(titleName) ? QNotice.notice.title.contains(titleName) : null ;
    }

    private OrderSpecifier<String> titleOrder(String title) {
        if (StringUtils.hasText(title)) {
            return title.equals("ASC") ? notice.title.asc() : notice.title.desc();
        }
        return OrderByNull.getDefault();
    }

    private OrderSpecifier<LocalDateTime> createTimeOrder(String createTime) {
        return createTime != null && createTime.equals("ASC") ? notice.createAt.asc() : notice.createAt.desc();
    }

}

package notice.pratice.domain.pageData;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Getter
@Setter
public class PageModel {

    private int page = 1;

    private int size = 3;

    private String titleName;
    private String title;
    private String createTime;

    public static String checkOrder(String str) {
        String result = str;

        if (StringUtils.hasText(result)) {
            if (result.equals("ASC") || result.equals("DESC")) {
                return result;
            }

            return (result.equals("asc") || result.equals("desc")) ? result.toUpperCase() : null;
        }

        return result;
    }
}

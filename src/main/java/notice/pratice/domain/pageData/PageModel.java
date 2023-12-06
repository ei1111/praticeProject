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
        if (StringUtils.hasText(str)) {
            if (str.equals("ASC") || str.equals("DESC")) {
                return str;
            }
            return (str.equals("asc") || str.equals("desc")) ? str.toUpperCase() : new String();
        }
        return new String();
    }
}

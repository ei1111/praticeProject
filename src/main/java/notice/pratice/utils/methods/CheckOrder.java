package notice.pratice.utils.methods;

import org.springframework.util.StringUtils;

public class CheckOrder {
    public String checkOrder(String str) {
        if(StringUtils.hasText(str)){
            return (str.equals("asc") || str.equals("desc")) ? str.toUpperCase() : null;
        }
        return null;
    }
}

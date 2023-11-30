package notice.pratice.domain.dataResult;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ErrorResMessAndCode {
    private final String message;
    private final String resultCode;
}

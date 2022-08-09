package kumoh.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Log {
    private String symmetricKey;
    private String encryptedData;
    private String recordDate;
    private String authorInfo;
}

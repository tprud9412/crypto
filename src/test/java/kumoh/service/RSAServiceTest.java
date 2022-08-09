package kumoh.service;

import kumoh.domain.Log;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RSAServiceTest {
    RSAService rsaService = new RSAService();

    @Test
    void 유효성확인(){
        // 현재 사용중인 키, 로그에 기록된 마지막 index의 대칭키 검사
        rsaService.keyValidation();
    }

    @Test
    void 키기록(){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime ldt = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), now.getHour(), now.getMinute(), now.getSecond());
        List<Log> newLog = new ArrayList<>();
        newLog.add(new Log("46D4CD4BEF44ECCC1FD033A6E55E9DEB", "newEncryptedData", ldt.toString(), "newAuthorInfo"));
        rsaService.recordKey(newLog);
    }

    // 평문 출력
    @Test
    void 로그읽기(){
        rsaService.readLog();
    }
}

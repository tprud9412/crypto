package kumoh.config;

import kumoh.domain.Log;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ResourceConfigTest {
    KeyConfig keyConfig = new KeyConfig();
    LogConfig logConfig = new LogConfig();

    @Test
    void 비밀키읽기() {
        System.out.println(keyConfig.getSecretKey());
    }

    @Test
    void 공개키읽기() {
        System.out.println(keyConfig.getPublicKey());
    }

    @Test
    void 대칭키읽기() {
        System.out.println(keyConfig.getSymmetricKey());
    }

    @Test
    void 로그읽기() {
        List<Log> logList = logConfig.getLog();
        for(Log log : logList){
            System.out.println(log.getSymmetricKey());
            System.out.println(log.getAuthorInfo());
            System.out.println(log.getRecordDate());
            System.out.println(log.getEncryptedData());
            System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
        }
    }

    // 암호화 복호화 되지 않은 문자열 입력, 파일 쓰기 테스트
    @Test
    void 비밀키쓰기() throws IOException {
        System.out.println(keyConfig.getSecretKey());
        keyConfig.secretKeyWrite("newSecretKey");
        System.out.println(keyConfig.getSecretKey());
    }

    // 암호화 복호화 되지 않은 문자열 입력, 파일 쓰기 테스트
    @Test
    void 공개키쓰기() throws IOException {
        System.out.println(keyConfig.getPublicKey());
        keyConfig.publicKeyWrite("newPublicKey");
        System.out.println(keyConfig.getPublicKey());
    }

    // 암호화 복호화 되지 않은 문자열 입력, 파일 쓰기 테스트
    @Test
    void 대칭키쓰기() throws IOException {
        System.out.println(keyConfig.getSymmetricKey());
        keyConfig.symmentricKeyWrite("newSymmetricKey");
        System.out.println(keyConfig.getSymmetricKey());
    }

    // 암호화 복호화 되지 않은 문자열 입력, 파일 쓰기 테스트
    @Test
    void 로그쓰기() throws IOException {
        List<Log> logList = logConfig.getLog();
        for(Log log : logList){
            System.out.println(log.getSymmetricKey());
            System.out.println(log.getAuthorInfo());
            System.out.println(log.getRecordDate());
            System.out.println(log.getEncryptedData());
        }
        System.out.println("ㅡㅡㅡㅡㅡbeforeㅡㅡㅡㅡㅡ");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime ldt = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), now.getHour(), now.getMinute(), now.getSecond());
        List<Log> newLog = new ArrayList<>();
        newLog.add(new Log("newSymmetricKey", "newEncryptedData", ldt.toString(), "newAuthorInfo"));
        logConfig.logWrite(newLog);
        logList = logConfig.getLog();
        for(Log log : logList){
            System.out.println(log.getSymmetricKey());
            System.out.println(log.getAuthorInfo());
            System.out.println(log.getRecordDate());
            System.out.println(log.getEncryptedData());
            System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
        }
        System.out.println("ㅡㅡㅡㅡㅡafterㅡㅡㅡㅡㅡ");
    }
}
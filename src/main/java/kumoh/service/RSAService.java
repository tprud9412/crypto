package kumoh.service;

import kumoh.config.KeyConfig;
import kumoh.config.LogConfig;
import kumoh.crypto.RSA;
import kumoh.domain.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RSAService {
    private KeyConfig keyConfig;
    private LogConfig logConfig;
    private RSA rsa;

    public RSAService(){
        keyConfig = new KeyConfig();
        logConfig = new LogConfig();
        rsa = new RSA();
    }

    public boolean keyValidation() {
        String originKey = keyConfig.getSymmetricKey(); // 현재 사용중인 키 읽어들임
        String logSymmetricKey = null;
        List<Log> logList = logConfig.getLog();
        if(!logList.isEmpty())
            logSymmetricKey = rsa.decryption(logList.get(logList.size()-1).getSymmetricKey()); // 마지막 index의 대칭키만 검사
        else{
            System.out.println("로그 기록 없음");
        }
        if (originKey.equals(logSymmetricKey)) {
            System.out.println(logSymmetricKey + " 키 유효");
            return true;
        }else{
            System.out.println(logSymmetricKey + " 키 무효");
            return false;
        }

    }

    public void readLog(){
        if(!logConfig.getLog().isEmpty()){
            for(Log log : logConfig.getLog()){
                System.out.println(
                        rsa.decryption(log.getSymmetricKey()) + " " +
                        rsa.decryption(log.getEncryptedData()) + " " +
                        rsa.decryption(log.getRecordDate()) + " " +
                        rsa.decryption(log.getAuthorInfo())
                );
            }
        }else System.out.println("로그 기록 없음");
    }

    public void recordKey(List<Log> newLog){
        List<Log> existingLog = new ArrayList<>();
        // 기존 로그 get
        if(!logConfig.getLog().isEmpty())
            existingLog = logConfig.getLog();

        //기존 로그 파일에 새로운 로그내용 덮어쓰기
        for(Log log : newLog){
            String newSymmetricKey = rsa.encryption(log.getSymmetricKey());
            String newEncryptedData = rsa.encryption(log.getEncryptedData());
            String newDate = rsa.encryption(log.getRecordDate());
            String newAuthorInfo = rsa.encryption(log.getAuthorInfo());

            existingLog.add(new Log(newSymmetricKey, newEncryptedData, newDate, newAuthorInfo));
        }
        try {
            logConfig.logWrite(existingLog);
            // 암호문 출력
            /*for(Log log : logConfig.getLog()){
                System.out.println(log.getSymmetricKey());
                System.out.println(log.getEncryptedData());
                System.out.println(log.getRecordDate());
                System.out.println(log.getAuthorInfo());
            }*/
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

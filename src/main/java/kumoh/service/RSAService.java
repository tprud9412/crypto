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

    public RSAService(){
        keyConfig = new KeyConfig();
        logConfig = new LogConfig();
    }

    public boolean keyValidation() {
        String originKey = keyConfig.getSymmentricKey(); // 현재 사용중인 키 읽어들임
        String logSymmetricKey = null;
        List<Log> logList = logConfig.getLog();
        if(!logList.isEmpty())
            logSymmetricKey = RSA.RSADecryption(logList.get(logList.size()-1).getSymmetricKey()); // 마지막 index의 대칭키만 검사
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
                        RSA.RSADecryption(log.getSymmetricKey()) + " " +
                        RSA.RSADecryption(log.getEncryptedData()) + " " +
                        RSA.RSADecryption(log.getRecordDate()) + " " +
                        RSA.RSADecryption(log.getAuthorInfo())
                );
            }
        }else System.out.println("로그 기록 없음");
    }

    public void recordKey(List<Log> listLog){
        List<Log> existingLog = new ArrayList<>();
        // 기존 로그 get
        if(!logConfig.getLog().isEmpty())
            existingLog = logConfig.getLog();

        //기존 로그 파일에 새로운 로그내용 덮어쓰기
        for(Log log : listLog){
            String newSymmetricKey = RSA.RSAEncryption(log.getSymmetricKey());
            String newEncryptedData = RSA.RSAEncryption(log.getEncryptedData());
            String newDate = RSA.RSAEncryption(log.getRecordDate());
            String newAuthorInfo = RSA.RSAEncryption(log.getAuthorInfo());

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

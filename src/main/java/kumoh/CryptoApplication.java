package kumoh;

import kumoh.domain.Log;
import kumoh.service.RSAService;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class CryptoApplication {
	public static void main(String[] args) {
		try {
			RSAService rsaService = new RSAService();
			rsaService.keyValidation();

			LocalDateTime now = LocalDateTime.now();
			LocalDateTime ldt = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), now.getHour(), now.getMinute(), now.getSecond());
			List<Log> newLog = new ArrayList<>();
			newLog.add(new Log("46D4CD4BEF44ECCC1FD033A6E55E9DEB", "newEncryptedData", ldt.toString(), "newAuthorInfo"));
			rsaService.recordKey(newLog);

			System.out.println();
			rsaService.readLog();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
//
//		SpringApplication.run(CryptoApplication.class, args);
//		RSAService rsaService = new RSAService();
//
//
///** LEA에서 생성한 원래 대칭키 **/
//
//		byte[] originKey = new byte[16];
//		new Random().nextBytes(originKey);
//
//
///** 파일에서 읽어들인 대칭키 **/
//
//		byte[] fileReadKey = new byte[16];
////		fileReadKey = key; //같은 키로 설정 (예제1)
//
//		new Random().nextBytes(fileReadKey); //다른 키로 설정 (예제2)
//
//
///** 대칭키 스트링형으로 변환 **/
//
//		String stringOrigin_key = rsaService.byteArrayToHexaString(originKey);
//		String stringFileRead_key = rsaService.byteArrayToHexaString(fileReadKey);
//		System.out.println("파일에서 읽어들인 키 : " + stringFileRead_key);
//		System.out.println("현재 복호화 중인 키 : " + stringOrigin_key);
//
////		if(rsaService.keyValidation(stringFileRead_key)){
////			System.out.println("키가 유효합니다.");
////			System.out.println("파일에서 읽어들인 키 : " + stringFileRead_key);
////			System.out.println("현재 복호화 중인 키 : " + stringOrigin_key);
////		}else{
////			System.out.println("키가 무효합니다.");
////			System.out.println("파일에서 읽어들인 키 : " + stringFileRead_key);
////			System.out.println("현재 복호화 중인 키 : " + stringOrigin_key);
////		}
//
//
////`/** 스트링 키를 다시 바이트로 변환 *
////
////*//** 새로운 바이트 배열에 생성하여 해당 키를 사용하여 복호화 진행 **//*
////
////
////		byte[] newKey = rsaService.hexStringToByteArray(stringFileRead_key);
////		System.out.println("다시 바이트로 바꾸기 : " + rsaService.byteArrayToHexaString(newKey));*/`
	}
}


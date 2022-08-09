package kumoh.crypto;

import lombok.SneakyThrows;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.*;
import java.security.spec.*;
import java.util.Base64;

public class RSA {
    public static boolean RSAKeyCreate() throws UnsupportedEncodingException {
        boolean retval = false;
        // 서버측 키 파일 생성 하기
        PublicKey publicKey1 = null;
        PrivateKey privateKey1 = null;

        SecureRandom secureRandom = new SecureRandom();
        KeyPairGenerator keyPairGenerator;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(1024, secureRandom);

            KeyPair keyPair = keyPairGenerator.genKeyPair();
            publicKey1 = keyPair.getPublic();
            privateKey1 = keyPair.getPrivate();

            KeyFactory keyFactory1 = KeyFactory.getInstance("RSA");
            RSAPublicKeySpec rsaPublicKeySpec = keyFactory1.getKeySpec(publicKey1, RSAPublicKeySpec.class);
            RSAPrivateKeySpec rsaPrivateKeySpec = keyFactory1.getKeySpec(privateKey1, RSAPrivateKeySpec.class);
            System.out.println("Public  key modulus : " + rsaPublicKeySpec.getModulus());
            System.out.println("Public  key exponent: " + rsaPublicKeySpec.getPublicExponent());
            System.out.println("Private key modulus : " + rsaPrivateKeySpec.getModulus());
            System.out.println("Private key exponent: " + rsaPrivateKeySpec.getPrivateExponent());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        //공개키와 개인키를 String으로 변환하여 파일에 저장
        byte[] bPublicKey1 = publicKey1.getEncoded();
        String sPublicKey1 =  Base64.getEncoder().encodeToString(bPublicKey1);

        byte[] bPrivateKey1 = privateKey1.getEncoded();
        String sPrivateKey1 =  Base64.getEncoder().encodeToString(bPrivateKey1);

        try {
            BufferedWriter bw1 = new BufferedWriter(new FileWriter("src/main/resources/key/publicKey.txt"));
            bw1.write(sPublicKey1);
            bw1.newLine();
            bw1.close();
            BufferedWriter bw2 = new BufferedWriter(new FileWriter("src/main/resources/key/secretKey.txt"));
            bw2.write(sPrivateKey1);
            bw2.newLine();
            bw2.close();
            retval = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retval;
    }

    //공개키로 암호화(키정보를 가져오기위한 사용자ID, 암호화 대상 평문)
    @SneakyThrows
    public static String RSAEncryption(String plainstr) {
        String sCipherBase64 = "";
        String sPublicKey = null;
        BufferedReader brPublicKey = null;
        try {
            //암호화를 위해 저장된 공개키를 읽어옴
            brPublicKey = new BufferedReader(new FileReader("src/main/resources/key/publicKey.txt"));
            sPublicKey = brPublicKey.readLine();     // First Line Read
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (brPublicKey != null)
                    brPublicKey.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //공개키를 디코딩
        byte[] bPublicKey = Base64.getDecoder().decode(sPublicKey.getBytes("UTF-8"));


        //디코딩 된 공개키정보를 사용하기위해 PublicKey에 저장
        PublicKey  publicKey = null;
        try {
            KeyFactory keyFactory2 = KeyFactory.getInstance("RSA");

            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(bPublicKey);
            publicKey = keyFactory2.generatePublic(publicKeySpec);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }

        // 공개키 이용 암호화
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] bCipher = cipher.doFinal(plainstr.getBytes());
            sCipherBase64 =  Base64.getEncoder().encodeToString(bCipher);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }

        return sCipherBase64;
    }

    //개인키로 복호화(키정보를 가져오기위한 사용자ID, 복호화 대상 암호문)
    @SneakyThrows
    public static String RSADecryption(String sCipherBase64) {
        String plainstr = "";
        String sPrivateKey = null;
        BufferedReader brPrivateKey = null;
        try {
            brPrivateKey = new BufferedReader(new FileReader("src/main/resources/key/secretKey.txt"));
            sPrivateKey = brPrivateKey.readLine();    // First Line Read
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (brPrivateKey != null)
                    brPrivateKey.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        byte[] bPrivateKey = Base64.getDecoder().decode(sPrivateKey.getBytes("UTF-8"));

        PrivateKey privateKey = null;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(bPrivateKey);
            privateKey = keyFactory.generatePrivate(privateKeySpec);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }

        // 개인키 이용 복호화
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            byte[] bCipher = Base64.getDecoder().decode(sCipherBase64.getBytes("UTF-8"));
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] bPlain = cipher.doFinal(bCipher);
            plainstr = new String(bPlain);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }

        return plainstr;
    }

    //암호화 및 복호화
    public static void RSATest() throws UnsupportedEncodingException {


        String sPlain = "46D4CD4BEF44ECCC1FD033A6E55E9DEB";
        String encStr = RSAEncryption(sPlain);
        String decStr = RSADecryption(encStr);

        System.out.println("평문원본 : " + sPlain);
        System.out.println("암호화문 : " + encStr);
        System.out.println("복호화문 : " + decStr);

    }

}
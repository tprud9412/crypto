package kumoh.config;

import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
@Setter
public class KeyConfig {
    private String publicKey;
    private String secretKey;
    private String symmentricKey;
    private FileWriter fw = null;
    private FileReader fr = null;
    private BufferedWriter bw = null;
    private BufferedReader br = null;
    private static String KEY = "src/main/resources/key/";
    private static String PUBLIC_KEY = "publicKey.txt";
    private static String SECRET_KEY = "secretKey.txt";
    private static String SYMMETRIC_KEY = "symmetricKey.txt";

    public KeyConfig(){
        try{
            publicKey = publicKeyRead();
            secretKey = secretKeyRead();
            symmentricKey = symmentricKeyRead();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private String keyRead(String dir, String keyFile) throws IOException {

        try {
            Path path = Paths.get(dir + keyFile);
            fr = new FileReader(path.toString());
            br = new BufferedReader(fr);
            return br.readLine();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if(br != null){
                br.close();
            }
            if(fr != null){
                fr.close();
            }
        }
    }

    private void keyWrite(String dir, String keyFile, String newKey) throws IOException {
        try{
            Path path = Paths.get(dir + keyFile);
            fw = new FileWriter(path.toString(), false);
            bw = new BufferedWriter(fw);
            bw.write(newKey);
            bw.flush();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if(br != null){
                br.close();
            }
            if(fr != null){
                fr.close();
            }
        }
    }

    public String publicKeyRead() throws IOException {
        return keyRead(KEY, PUBLIC_KEY);
    }

    public String secretKeyRead() throws IOException {
        return keyRead(KEY, SECRET_KEY);
    }

    public String symmentricKeyRead() throws IOException {
        return keyRead(KEY, SYMMETRIC_KEY);
    }

    public void publicKeyWrite(String newKey) throws IOException {
        keyWrite(KEY, PUBLIC_KEY, newKey);
        setPublicKey(newKey);
    }

    public void secretKeyWrite(String newKey) throws IOException {
        keyWrite(KEY, SECRET_KEY, newKey);
        setSecretKey(newKey);
    }

    public void symmentricKeyWrite(String newKey) throws IOException{
        keyWrite(KEY, SYMMETRIC_KEY, newKey);
        setSymmentricKey(newKey);
    }
}

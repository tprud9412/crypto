package kumoh.config;

import kumoh.domain.Log;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class LogConfig {
    private List<Log> log;
    private FileWriter fw = null;
    private FileReader fr = null;
    private BufferedWriter bw = null;
    private BufferedReader br = null;
    private static String LOG = "src/main/resources/log/";
    private static String LOG_FILE = "log.txt";

    public LogConfig(){
        try {
            log = logRead();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private List<Log> logRead(String dir, String logFile) throws IOException {

        try {
            Path path = Paths.get(dir+logFile);
            fr = new FileReader(path.toString());
            br = new BufferedReader(fr);
            List<Log> logList = new ArrayList<>();
            String readLine = null;
            String[] splitLog;
            LocalDateTime dateTime;
            while((readLine = br.readLine()) != null){
                splitLog = readLine.split(" ");
                logList.add(new Log(splitLog[0], splitLog[1], splitLog[2], splitLog[3]));
            }
            return logList;
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

    //로그 내용 덮어쓰기
    private void logWrite(String dir, String logFile, List<Log> newLog) throws IOException {
        try{
            Path path = Paths.get(dir + logFile);
            fw = new FileWriter(path.toString(), false);
            bw = new BufferedWriter(fw);
            for(Log log : newLog){
                bw.write(log.getSymmetricKey() + " " + log.getEncryptedData() + " " + log.getRecordDate() + " " + log.getAuthorInfo());
                bw.newLine();
            }
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

    public List<Log> logRead() throws IOException {
        return logRead(LOG, LOG_FILE);
    }

    public void logWrite(List<Log> newLog) throws IOException{
        logWrite(LOG, LOG_FILE, newLog);
    }
}

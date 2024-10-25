package org.example.gerenciadorcontratos;

import java.io.IOException;

public class DataBaseBackup {
    public static int CreateDataBaseBackup(){
        boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
        ProcessBuilder builder = new ProcessBuilder();
        if(isWindows){
            builder.command("src/main/resources/org/example/gerenciadorcontratos/backup.bat");
        }else{
            builder.command("sh", "-c", "\"" + "src/main/resources/org/example/gerenciadorcontratos/backup.sh" + "\"");
        }

        try {
            Process process = builder.start();
            System.out.println(process.waitFor());
            return process.waitFor();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

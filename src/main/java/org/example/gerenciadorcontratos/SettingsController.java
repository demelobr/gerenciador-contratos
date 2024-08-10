package org.example.gerenciadorcontratos;

import java.io.*;

public class SettingsController {
    public static void saveSettings(Settings settings) throws SettingsSavedSuccessfullyException, SettingsSavingFailedException {
        try {
            FileOutputStream binaryFile = new FileOutputStream("src/main/resources/org/example/gerenciadorcontratos/settings.bin");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(binaryFile);
            objectOutputStream.writeObject(settings);
            objectOutputStream.close();
            binaryFile.close();
            throw new SettingsSavedSuccessfullyException();
        } catch (IOException e) {
            throw new SettingsSavingFailedException();
        }
    }

    public static Settings loadSettings() throws ConfigurationLoadFailureException {
        Settings settings = null;
        try {
            FileInputStream binaryFile = new FileInputStream("src/main/resources/org/example/gerenciadorcontratos/settings.bin");
            ObjectInputStream objectInputStream = new ObjectInputStream(binaryFile);
            settings = (Settings) objectInputStream.readObject();
            objectInputStream.close();
            binaryFile.close();
        } catch (IOException e) {
            throw new ConfigurationLoadFailureException();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return settings;
    }
}

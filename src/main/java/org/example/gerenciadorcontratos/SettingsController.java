package org.example.gerenciadorcontratos;

import java.io.*;

public class SettingsController {
    public static void saveSettings(Settings settings) throws SettingsSavedSuccessfullyException, SettingsSavingFailedException {
        try {
            System.out.println(settings);
            FileOutputStream fileBinary = new FileOutputStream("src/main/resources/org/example/gerenciadorcontratos/settings.bin");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileBinary);
            objectOutputStream.writeObject(settings);
            objectOutputStream.close();
            fileBinary.close();
            throw new SettingsSavedSuccessfullyException();
        } catch (IOException e) {
            throw new SettingsSavingFailedException();
        }
    }

    public static Settings loadSettings() throws ConfigurationLoadFailureException {
        Settings settings = null;
        try {
            FileInputStream fileBinary = new FileInputStream("src/main/resources/org/example/gerenciadorcontratos/settings.bin");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileBinary);
            settings = (Settings) objectInputStream.readObject();
            objectInputStream.close();
            fileBinary.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new ConfigurationLoadFailureException();
        }
        return settings;
    }
}

package org.example.gerenciadorcontratos;

import java.io.*;

public class CloudController {
    public static void saveCloud(Cloud cloud) throws CloudSavingFailedException, CloudSavedSuccessfullyException {
        System.out.println(cloud);
        try {
            FileOutputStream fileBinary = new FileOutputStream("src/main/resources/org/example/gerenciadorcontratos/cloud.bin");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileBinary);
            objectOutputStream.writeObject(cloud);
            objectOutputStream.close();
            fileBinary.close();
            throw new CloudSavedSuccessfullyException();
        } catch (IOException e) {
            throw new CloudSavingFailedException();
        }
    }

    public static Cloud loadCloud() throws CloudLoadFailureException {
        Cloud cloud = null;
        try {
            FileInputStream fileBinary = new FileInputStream("src/main/resources/org/example/gerenciadorcontratos/cloud.bin");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileBinary);
            cloud = (Cloud) objectInputStream.readObject();
            objectInputStream.close();
            fileBinary.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new CloudLoadFailureException();
        }
        return cloud;
    }
}

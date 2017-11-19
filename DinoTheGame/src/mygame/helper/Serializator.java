/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Base64;

/**
 * A Helper class for Serializing object to a file.
 *
 */
public class Serializator {

    /**
     * Load data from file.
     *
     * @param s file path
     * @return Represented Object from the given file.
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object fromFile(File s) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(s);
        byte[] data = new byte[(int) s.length()];
        fis.read(data);
        fis.close();
        return fromString(new String(data, "UTF-8"));
    }

    /**
     * Read the object from Base64 string.
     */
    public static Object fromString(String s) throws IOException,
            ClassNotFoundException {
        byte[] data = Base64.getDecoder().decode(s);
        ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(data));
        Object o = ois.readObject();
        ois.close();
        return o;
    }

    /**
     * Write the object to a Base64 string.
     *
     * @param o
     * @return
     * @throws java.io.IOException
     */
    public static String toString(Serializable o) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(o);
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

    /**
     * Save the object to a singlefile.
     *
     * @param o Object that will be serialized
     * @param s Targetted file.
     * @throws IOException
     */
    public static void toFile(Serializable o, File s) throws IOException {
        PrintWriter prntFileOutput = new PrintWriter(s);
        prntFileOutput.append(toString(o));
        prntFileOutput.flush();
    }
}

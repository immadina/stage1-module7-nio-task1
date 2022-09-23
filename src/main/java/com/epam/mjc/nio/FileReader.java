package com.epam.mjc.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


public class FileReader {

    public Profile getDataFromFile(File file) {
        String name = null;
        Integer age = null;
        String email = null;
        Long phone = null;

        try (RandomAccessFile aFile = new RandomAccessFile(file, "r")) {
            FileChannel inChannel = aFile.getChannel();

            ByteBuffer buffer = ByteBuffer.allocate(1024);
            StringBuilder stringBuilder = new StringBuilder();

            while (inChannel.read(buffer) > 0) {
                buffer.flip();

                for (int i = 0; i < buffer.limit(); i++) {
                    stringBuilder.append((char) buffer.get());
                }
                buffer.clear();
            }
            String st = stringBuilder.toString();
            st = st.replaceAll(" ", "").replaceAll("(\\r|\\n)", ":");
            String[] sa = st.split(":");

            for (int i = 0; i < sa.length; i++) {

                if (sa[i].equals("Name")) {
                    name = sa[i+1];
                } else if (sa[i].equals("Age")) {
                    age = Integer.parseInt(sa[i+1]);
                } else if (sa[i].equals("Email")) {
                    email = sa[i+1];
                } else if (sa[i].equals("Phone")) {
                    phone = Long.parseLong(sa[i+1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Profile(name, age, email, phone);
    }
}

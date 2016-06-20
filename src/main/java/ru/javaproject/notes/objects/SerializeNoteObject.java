package ru.javaproject.notes.objects;

import com.mysql.fabric.jdbc.FabricMySQLDriver;

import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.sql.DriverManager;


/**
 * Created by s0u1 on 17.06.2016.
 */
public abstract class SerializeNoteObject {

    private static String HOST = "jdbc:mysql://localhost:3306/db_notes?useSSL=false";
    private static String user = "root";
    private static String password = "root";
    private static String READ_OBJECT_SQL = "SELECT * FROM pt_notes";
    private static String PutBlob = "insert into pt_notes (id, note_object) values (?, ?)";

    private static Connection getConnection() throws Exception {

        Connection connection = null;

        try {
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(HOST, user, password);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static void writeJavaObject(Object object, int count) throws Exception {
        Connection connection = getConnection();
        Blob newNote = createBlob(object, connection);
        if (newNote != null) {
            writeBlobToDB(newNote, connection, count);
        }
        connection.close();
    }

    private static Blob createBlob(Object object, Connection connection){
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream outputStream = new ObjectOutputStream(out);
            outputStream.writeObject(object);
            byte[] bytes = out.toByteArray();
            Blob newNote = connection.createBlob();
            newNote.setBytes(1, bytes);
            outputStream.close();

            return newNote;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void writeBlobToDB(Blob blob, Connection connection, int count){
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(PutBlob);
            ps.setInt(1, count);
            ps.setBlob(2, blob);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Note> readJavaObject() throws Exception {
        Connection connection = getConnection();
        ArrayList<Note> list = new ArrayList<Note>();

        if (connection != null){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(READ_OBJECT_SQL);

            Blob blob;
            int x;
            Note testNote;

            while (resultSet.next()){
                x = resultSet.getInt(1);
                blob = resultSet.getBlob(2);

                ObjectInputStream ois = new ObjectInputStream(blob.getBinaryStream());
                try {
                    testNote = (Note)ois.readObject();
                    list.add(testNote);
                } catch (Exception e){e.printStackTrace(); }
                ois.close();

            }
            connection.close();
        }

        return list;
    }
}

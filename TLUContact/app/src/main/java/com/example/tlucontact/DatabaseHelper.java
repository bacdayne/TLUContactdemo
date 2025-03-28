package com.example.tlucontact;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Tinhdiem.db"; // Thay thế bằng tên file của bạn

    // Đường dẫn đến thư mục cơ sở dữ liệu của ứng dụng
    private static String DB_PATH = "D:\\ANDROID\\TLUContact\\app\\src\\main\\assets\\TinhDiem.db";

    // Context của ứng dụng
    private final Context myContext;

    // Phiên bản cơ sở dữ liệu
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.myContext = context;
        // Xác định đường dẫn đến thư mục databases của ứng dụng
        DB_PATH = context.getDatabasePath(DATABASE_NAME).getAbsolutePath();
    }

    /**
     * Tạo một rỗng database mới trên hệ thống và ghi vào đó.
     * Bằng cách ghi đè hàm rỗng của SQLiteOpenHelper.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Không tạo bảng ở đây, vì chúng ta sẽ sao chép database đã có
    }

    /**
     * Nâng cấp cơ sở dữ liệu nếu cần.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xử lý việc nâng cấp cơ sở dữ liệu (nếu có).
        // Trong trường hợp này, bạn có thể xóa cơ sở dữ liệu cũ và sao chép lại từ assets.
        // Nhưng cần cẩn thận nếu có dữ liệu quan trọng.
        Log.w("DatabaseHelper", "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + "TABLE_NAME"); // Thay TABLE_NAME nếu cần
        onCreate(db);
    }

    /**
     * Kiểm tra nếu database đã tồn tại chưa để không sao chép lại mỗi lần ứng dụng chạy.
     *
     * @return true nếu tồn tại, false nếu không tồn tại
     */
    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH);
        return dbFile.exists();
    }

    /**
     * Sao chép database từ thư mục assets vào thư mục dữ liệu của ứng dụng.
     * Điều này được thực hiện bằng cách sao chép các luồng byte.
     * Vì tất cả các thư mục cha của nó sẽ được tạo ra, bạn chỉ cần mở luồng đầu vào.
     */
    private void copyDataBase() throws IOException {
        // Mở database dưới dạng luồng đầu vào
        InputStream myInput = myContext.getAssets().open(DATABASE_NAME);

        // Đường dẫn đến database rỗng mới
        OutputStream myOutput = new FileOutputStream(DB_PATH);

        // Truyền các byte từ luồng đầu vào sang luồng đầu ra
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Đóng các luồng
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();

        if (dbExist) {
            // Không làm gì - database đã tồn tại
        } else {
            // Gọi phương thức này để tạo một database rỗng vào thư mục hệ thống
            this.getReadableDatabase();
            this.close(); // Đóng kết nối để cho phép sao chép

            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    // Bạn có thể thêm các phương thức để mở và đóng database ở đây nếu cần
    // Ví dụ:
    private SQLiteDatabase myDataBase;

    public void openDataBase() throws android.database.SQLException {
        // Mở database
        String myPath = DB_PATH;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }

}
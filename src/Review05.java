import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Review05 {

    public static void main(String[] args) {
        // 3. データベース接続と結果取得のための変数宣言
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // 1. ドライバのクラスをJava上で読み込む
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. DB(kadaidb)と接続する
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/kadaidb?useSSL=false&allowPublicKeyRetrieval=true", "root", "07nob-kat30");

            // 4. DBとやりとりする窓口(Statementオブジェクト)の作成
            pstmt = con.prepareStatement("SELECT * FROM person WHERE id = ?");

            // 5,6. Select文の実行と結果を格納/代入
            System.out.println("検索するidを入力してください > ");
            int input = keyInNum();

            // PreparedStatementオブジェクトの?に値をセット
            pstmt.setInt(1, input);
            // System.out.println(input + "がidとして選択されました。"); // 途中経過のテスト用
            rs = pstmt.executeQuery();

            // 7. 結果を表示する
            while (rs.next()) {
                // name列の値を取得して表示
                System.out.println(rs.getString("name"));
                // age列の値を取得して表示
                System.out.println(rs.getInt("age"));
            }

            // 例外処理
        } catch (ClassNotFoundException e) {
            System.err.println("JDBCドライバのロードに失敗しました。");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("データベースに異常が発生しました。");
            e.printStackTrace();
        } finally {

            // 8. 接続を閉じる
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("ResultSetを閉じるときにエラーが発生しました。");
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    System.err.println("PreparedStatementを閉じるときににエラーが発生しました。");
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.err.println("データベース切断時にエラーが発生しました。");
                    e.printStackTrace();
                }
            }
        }
    }

    // キーボードから入力された値をStringで返す 引数：なし 戻り値：入力された文字列
    private static String keyIn() {
        String line = null;
        try {
            BufferedReader key = new BufferedReader(new InputStreamReader(System.in));
            line = key.readLine();
        } catch (IOException e) {
        }
        return line;
    }

    // キーボードから入力された値をintで返す 引数：なし 戻り値：int
    private static int keyInNum() {
        int result = 0;
        try {
            result = Integer.parseInt(keyIn());
        } catch (NumberFormatException e) {
        }
        return result;
    }
}
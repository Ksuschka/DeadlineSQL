package ru.netology.data;

import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class SQLData {
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app_db", "user", "pass");
    }

    public static String getVerificationCode() {
        val codeSQL = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1";
        val runner = new QueryRunner();
        try (val conn = getConnection()) {
            val code = runner.query(conn, codeSQL, new ScalarHandler<String>());

            return code;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    public static void clearDb(){
        QueryRunner runner = new QueryRunner();
        val deleteCode = "DELETE FROM auth_codes";
        val deleteCard = "DELETE FROM cards";
        val deleteUser = "DELETE FROM users";
        val deleteCardTransactions = "DELETE FROM card_transactions";
        try (val conn = getConnection()
        ) {
            runner.update(conn, deleteCode);
            runner.update(conn, deleteCard);
            runner.update(conn, deleteUser);
            runner.update(conn, deleteCardTransactions);
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}

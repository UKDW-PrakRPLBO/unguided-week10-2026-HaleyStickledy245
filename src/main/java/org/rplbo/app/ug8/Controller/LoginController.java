package org.rplbo.app.ug8.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import org.rplbo.app.ug8.UmbrellaApp;
import org.rplbo.app.ug8.UmbrellaDBManager;

import java.io.IOException;

public class LoginController {
    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private Label status;

    @FXML
    private void handleLogin(ActionEvent event) {
        // ==============================================================================
        // TODO 1: PROSES AUTENTIKASI (LOGIN)
        // ==============================================================================
        // 1. Ambil input teks dari txtUsername dan txtPassword.
        // 2. Buat instansiasi dari class UmbrellaDBManager.
        // 3. Panggil metode validateUser() dari db manager tersebut.
        // 4. Jika hasil validasi berhasil (tidak null):
        //    a. Simpan nama user ke variabel statis UmbrellaApp.loggedInUser.
        //    b. Pindah ke halaman "umbrella-view.fxml" menggunakan UmbrellaApp.switchScene().
        // 5. Jika gagal, tampilkan pesan error "AUTHENTICATION FAILED" pada lblStatus.
        // ==============================================================================

        // --- TULIS KODE ANDA DI BAWAH INI ---

        String username = this.username.getText();
        String password = this.password.getText();

        UmbrellaDBManager db = new UmbrellaDBManager();
        String fullName = db.validateUser(username, password);
        if (fullName != null) {
            UmbrellaApp.loggedInUser = fullName;
            try {
                UmbrellaApp.switchScene("umbrella-view.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            status.setText("AUTHENTICATION FAILED");
        }
    }
}
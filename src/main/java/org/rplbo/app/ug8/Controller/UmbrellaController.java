package org.rplbo.app.ug8.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.rplbo.app.ug8.InventoryItem;
import org.rplbo.app.ug8.UmbrellaApp;
import org.rplbo.app.ug8.UmbrellaDBManager;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class UmbrellaController implements Initializable {
    // Variabel FXML diubah untuk mencerminkan skema Grup B
    @FXML private TextField item, initial, supply;
    @FXML private TableView<InventoryItem> inventory;
    @FXML private TableColumn<InventoryItem, String> invName;
    @FXML private TableColumn<InventoryItem, Integer> invInitial, invSupply, invFinal;

    private UmbrellaDBManager db;
    private ObservableList<InventoryItem> masterData = FXCollections.observableArrayList();
    private InventoryItem selectedItem;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new UmbrellaDBManager();
        System.out.println("LOG: OPERATIVE " + UmbrellaApp.loggedInUser + " ACCESS GRANTED.");

        // ==============================================================================
        // TODO 1: MENGHUBUNGKAN KOLOM TABEL (TABLE COLUMN MAPPING)
        // ==============================================================================
        // Hubungkan setiap TableColumn (colName, colInitial, colSupply, colFinal)
        // dengan nama atribut (property) yang sesuai di dalam class InventoryItem.
        // Gunakan setCellValueFactory() dan new PropertyValueFactory<>(). (???)
        // ==============================================================================

        // --- TULIS KODE ANDA DI BAWAH INI ---

        invName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        invInitial.setCellValueFactory(new PropertyValueFactory<>("initialStock"));
        invSupply.setCellValueFactory(new PropertyValueFactory<>("newSupply"));
        invFinal.setCellValueFactory(new PropertyValueFactory<>("finalStock"));

        // ==============================================================================
        // TODO 2: LISTENER KLIK BARIS TABEL (SELECTION MODEL)
        // ==============================================================================
        // Lengkapi logika di dalam listener di bawah ini:
        // 1. Masukkan objek 'newVal' ke dalam variabel global 'selectedItem'.
        // 2. Tampilkan nilai itemName dari newVal ke dalam TextField 'txtItem'.
        // 3. Tampilkan nilai initialStock dari newVal ke dalam TextField 'txtInitial'.
        // 4. Tampilkan nilai newSupply dari newVal ke dalam TextField 'txtSupply'.
        //    (Ingat: Ubah tipe data angka menjadi String menggunakan String.valueOf).
        // 5. Matikan (disable) TextField 'txtItem' agar pengguna tidak bisa mengubah
        //    nama item (Primary Key) saat sedang mengedit data.
        // ==============================================================================

        inventory.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                selectedItem = newVal;
                item.setText(selectedItem.getItemName());
                item.setDisable(true);
                initial.setText(String.valueOf(selectedItem.getInitialStock()));
                supply.setText(String.valueOf(selectedItem.getNewSupply()));
            }
        });

        refreshTable();
    }

    @FXML
    private void handleSave() {
        // ==============================================================================
        // TODO 3: LOGIKA PERBARUI/UPDATE DATA
        // ==============================================================================
        // 1. Pastikan ada item yang dipilih (cek apakah selectedItem tidak sama dengan null).
        // 2. Ambil nilai teks terbaru dari txtInitial dan txtSupply, lalu ubah menjadi Integer.
        // 3. HITUNG FINAL STOCK BARU:
        //    Rumus GRUP B: final_stock = initial + supply
        // 4. Buat objek InventoryItem baru menggunakan data yang diperbarui.
        //    PENTING: Ambil nama item dari selectedItem.getItemName(), jangan dari TextBox!
        // 5. Panggil db.updateItem(). Jika berhasil (mengembalikan true), panggil:
        //    - refreshTable()
        //    - clearFields()
        // ==============================================================================

        // --- TULIS KODE ANDA DI BAWAH INI ---

        if (selectedItem == null) return;
        int newInitial = Integer.parseInt(initial.getText());
        int newSupply  = Integer.parseInt(supply.getText());
        int finalStock = newInitial + newSupply;

        selectedItem.setInitialStock(newInitial);
        selectedItem.setNewSupply(newSupply);
        selectedItem.setFinalStock(finalStock);

        db.updateItem(selectedItem);
        refreshTable();
        clearFields();
    }

    @FXML
    private void handleAdd() {
        // ==============================================================================
        // TODO 4: LOGIKA TAMBAH DATA
        // ==============================================================================
        // 1. Ambil nilai teks dari txtInitial dan txtSupply, lalu ubah menjadi Integer.
        // 2. HITUNG FINAL STOCK:
        //    Rumus GRUP B: final_stock = initial + supply
        // 3. Ambil nilai String dari field txtItem untuk nama item.
        // 4. Buat objek InventoryItem baru menggunakan data-data di atas.
        // 5. Panggil metode addItem() dari objek 'db' dan masukkan objek item tersebut.
        // 6. Panggil metode refreshTable() agar data baru muncul di tabel.
        // ==============================================================================

        /*
         * Do you ever use block comments? Use them.
         * */

        // --- TULIS KODE ANDA DI BAWAH INI ---

        String itemName = item.getText();
        int newInitial = Integer.parseInt(initial.getText());
        int newSupply  = Integer.parseInt(supply.getText());
        int finalStock = newInitial + newSupply;

        db.addItem(new InventoryItem(itemName, newInitial, newSupply, finalStock));
        refreshTable();
    }

    @FXML
    private void handleDelete() {
        // ==============================================================================
        // TODO 5: LOGIKA HAPUS DATA
        // ==============================================================================
        // 1. Ambil item yang sedang dipilih oleh user di tableInventory.
        // 2. Cek jika item tersebut ada (tidak null):
        //    a. (Opsional/Nilai Plus) Tampilkan Alert konfirmasi penghapusan.
        //    b. Panggil db.deleteItem() dengan parameter nama item tersebut.
        //    c. Jika berhasil terhapus dari database, hapus juga dari 'masterData'.
        //    d. Panggil clearFields().
        // 3. Jika null (user belum memilih baris), tampilkan Alert bertipe WARNING
        //    yang meminta user memilih item terlebih dahulu.
        // ==============================================================================

        // --- TULIS KODE ANDA DI BAWAH INI ---

        // Shouty caps aren't the most friendly register, but the examples given weren't nice
        if (selectedItem == null) {
            Alert youForgot = new Alert(Alert.AlertType.WARNING);
            youForgot.setTitle("ATTENTION");
            youForgot.setHeaderText("ITEM NOT SELECTED");
            // I'm not sure who is Umbrella Corp. nor if they call their colleagues "agents",
            // but I think it's very thematic
            // (can someone can elaborate what this is referencing to?)
            youForgot.setContentText("AGENT, PLEASE SELECT THE ITEM TO BE DELETED.");
            ((Button) youForgot.getDialogPane().lookupButton(ButtonType.OK)).setText("OK");
            youForgot.showAndWait();
            return;
        }
        Alert areYouSure = new Alert(
            Alert.AlertType.CONFIRMATION,
            "AGENT, ARE YOU SURE YOU WANT TO DELETE THIS ITEM?",
            new ButtonType("YES", ButtonBar.ButtonData.YES),
            new ButtonType("NO", ButtonBar.ButtonData.NO)
        );
        areYouSure.setTitle("ATTENTION");
        areYouSure.setHeaderText("CONFIRM DELETION");
        Optional<ButtonType> selection = areYouSure.showAndWait();
        System.err.println(selection);

        if (selection.isPresent() && selection.get().getButtonData() == ButtonBar.ButtonData.YES) {
            db.deleteItem(selectedItem.getItemName());
            refreshTable();
            clearFields();
        }
    }

    // Logout
    @FXML
    private void handleLogout() {
        try {
            UmbrellaApp.switchScene("login-view.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Bersihkan Text Fields
    @FXML
    private void clearFields() {
        item.clear();
        initial.clear();
        supply.clear();
        item.setDisable(false);
        selectedItem = null;
    }

    // Refresh Table
    @FXML
    private void refreshTable() {
        masterData.setAll(db.getAllItems());
        inventory.setItems(masterData);
    }
}
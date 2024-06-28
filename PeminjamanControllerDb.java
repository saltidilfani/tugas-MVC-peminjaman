/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salti.controller;


import salti.Dao.BukuDb;
import salti.Dao.MahasiswaDb;
import salti.Dao.PeminjamanDb;
import salti.model.Buku;
import salti.model.Mahasiswa;
import salti.view.FormPeminjamanDb;
import salti.model.Peminjaman;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;



/**
 *
 * @author salti
 */
public class PeminjamanControllerDb {
    FormPeminjamanDb formPeminjamanDb;
    Peminjaman peminjaman;
    MahasiswaDb mahasiswaDb;
    BukuDb bukuDb;
    PeminjamanDb peminjamanDb;
    
    public PeminjamanControllerDb(FormPeminjamanDb formPeminjamanDb){
        this.formPeminjamanDb = formPeminjamanDb;
        mahasiswaDb = new MahasiswaDb();
        bukuDb = new BukuDb();
        peminjamanDb = new PeminjamanDb();
    }
    
    public void clearForm(){
        formPeminjamanDb.getTxtTglpinjam().setText("");
        formPeminjamanDb.getTxtTglkembali().setText("");
    }

    public void isiCboMahasiswa() {
        try {
            List<Mahasiswa> list = mahasiswaDb.getAllMahasiswa();
            formPeminjamanDb.getCboMahasiswa().removeAllItems();
            for(Mahasiswa mahasiswa : list) {
                formPeminjamanDb.getCboMahasiswa()
                        .addItem(mahasiswa.getNobp()+ "-" +mahasiswa.getNama());
            }
        } catch (Exception ex) {
            Logger.getLogger(PeminjamanControllerDb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void isiCboBuku() {
        try {
            List<Buku> list = bukuDb.getAllBuku();
            formPeminjamanDb.getCboBuku().removeAllItems();
            for(Buku buku : list) {
                formPeminjamanDb.getCboBuku()
                        .addItem(buku.getKodeBuku()+ "-" +buku.getJudul());
            }
        } catch (Exception ex) {
            Logger.getLogger(PeminjamanControllerDb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void insert(){
        try {
            peminjaman = new Peminjaman();
       
            Mahasiswa mahasiswa = mahasiswaDb.getMahasiswa(
                    formPeminjamanDb.getCboMahasiswa().getSelectedItem().toString().split("-")[0]);
            Buku buku = bukuDb.getBuku(
                    formPeminjamanDb.getCboBuku().getSelectedItem().toString().split("-")[0]);
            peminjaman.setMahasiswa(mahasiswa);
            peminjaman.setBuku(buku);
            peminjaman.setTglpinjam(formPeminjamanDb.getTxtTglpinjam().getText());
            peminjaman.setTglkembali(formPeminjamanDb.getTxtTglkembali().getText());
            peminjamanDb.insert(mahasiswa, buku, peminjaman);
            JOptionPane.showMessageDialog(formPeminjamanDb, "Entri Data Ok");
        } catch (Exception ex) {
            Logger.getLogger(PeminjamanControllerDb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void update(){
        try {
            Mahasiswa mahasiswa = mahasiswaDb.getMahasiswa(
                    formPeminjamanDb.getCboMahasiswa().getSelectedItem().toString().split("-")[0]);
            Buku buku = bukuDb.getBuku(
                    formPeminjamanDb.getCboBuku().getSelectedItem().toString().split("-")[0]);
            peminjaman.setMahasiswa(mahasiswa);
            peminjaman.setBuku(buku);
            peminjaman.setTglpinjam(formPeminjamanDb.getTxtTglpinjam().getText());
            peminjaman.setTglkembali(formPeminjamanDb.getTxtTglkembali().getText());
            peminjamanDb.update(peminjaman);
            JOptionPane.showMessageDialog(formPeminjamanDb, "Update Data Ok");
        } catch (Exception ex) {
            Logger.getLogger(PeminjamanControllerDb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void delete(){
        try {
            String nobp = formPeminjamanDb.getTabelPeminjaman().getValueAt(
                    formPeminjamanDb.getTabelPeminjaman().getSelectedRow(), 0).toString();
            String kodeBuku = formPeminjamanDb.getTabelPeminjaman().getValueAt(
                    formPeminjamanDb.getTabelPeminjaman().getSelectedRow(), 1).toString();
            peminjamanDb.delete(nobp, kodeBuku);
            JOptionPane.showMessageDialog(formPeminjamanDb, "Delete Data Ok");
        } catch (Exception ex) {
            Logger.getLogger(PeminjamanControllerDb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void viewData(){
        try {
            DefaultTableModel model = 
                    (DefaultTableModel)formPeminjamanDb.getTabelPeminjaman().getModel();
            model.setNumRows(0);
            List<Peminjaman> list = peminjamanDb.getAllPeminjaman();
            for(Peminjaman peminjaman : list){
                Object[] data = {
                    peminjaman.getMahasiswa().getNobp(),
                    peminjaman.getBuku().getKodeBuku(),
                    peminjaman.getTglpinjam(),
                    peminjaman.getTglkembali()
                };
                model.addRow(data);
            }
        } catch (Exception ex) {
            Logger.getLogger(MahasiswaControllerDb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public void actionClickTabel() {
        try {
            String nobp = formPeminjamanDb.getTabelPeminjaman().getValueAt(formPeminjamanDb.getTabelPeminjaman().getSelectedRow(), 0).toString();
            String kodeBuku = formPeminjamanDb.getTabelPeminjaman().getValueAt(formPeminjamanDb.getTabelPeminjaman().getSelectedRow(), 1).toString();
            
            peminjaman = peminjamanDb.getPeminjaman(nobp, kodeBuku);
            formPeminjamanDb.getCboMahasiswa().setSelectedItem(peminjaman.getMahasiswa().getNobp() + "-" + peminjaman.getMahasiswa().getNama());
            formPeminjamanDb.getCboBuku().setSelectedItem(
                    peminjaman.getBuku().getKodeBuku() + "-" + peminjaman.getBuku().getJudul());
            formPeminjamanDb.getTxtTglpinjam().setText(peminjaman.getTglpinjam());
            formPeminjamanDb.getTxtTglkembali().setText(peminjaman.getTglkembali());
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(PeminjamanControllerDb.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
}   
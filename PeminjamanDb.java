/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salti.Dao;

import salti.model.Peminjaman;
import salti.model.Mahasiswa;
import salti.model.Buku;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author salti dilfani
 */
public class PeminjamanDb {
    
    public void insert(Mahasiswa mahasiswa, Buku buku, Peminjaman peminjaman) throws Exception {
        DBHelper db = new DBHelper();
        String query = "insert into peminjaman values(?,?,?,?)";
        PreparedStatement ps = db.getKoneksi().prepareStatement(query);
        ps.setString(1, mahasiswa.getNobp());
        ps.setString(2, buku.getKodeBuku());
        ps.setString(3, peminjaman.getTglpinjam());
        ps.setString(4, peminjaman.getTglkembali());
        ps.executeUpdate();
    }
    
    public void update(Peminjaman peminjaman) throws Exception {
        DBHelper db = new DBHelper();
        String query = "update peminjaman set tglpinjam=?, tglkembali=? "
                + "where nobp=? and kodebuku=?";
        PreparedStatement ps = db.getKoneksi().prepareStatement(query);
        ps.setString(1, peminjaman.getTglpinjam());
        ps.setString(2, peminjaman.getTglkembali());
        ps.setString(3, peminjaman.getMahasiswa().getNobp());
        ps.setString(4, peminjaman.getBuku().getKodeBuku());
        ps.executeUpdate();
    }
    
    public void delete(String nobp, String kodebuku) throws Exception{
        DBHelper db = new DBHelper();
        String query = "delete from peminjaman where nobp=? and kodebuku=?";
        PreparedStatement ps = db.getKoneksi().prepareStatement(query);
        ps.setString(1, nobp);
        ps.setString(2, kodebuku);
        ps.executeUpdate();
    }
    
     public Peminjaman getPeminjaman(String nobp, String kodebuku) throws Exception{
        DBHelper db = new DBHelper();
        String query = "select * from peminjaman where nobp=? and kodebuku=?";
        PreparedStatement ps = db.getKoneksi().prepareStatement(query);
        ps.setString(1, nobp);
        ps.setString(2, kodebuku);
        Peminjaman peminjaman = null;
        Mahasiswa mahasiswa = null;
        Buku buku = null;
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            peminjaman = new Peminjaman();
            mahasiswa = new MahasiswaDb().getMahasiswa(rs.getString(1));
            buku = new BukuDb().getBuku(rs.getString(2));
            peminjaman.setMahasiswa(mahasiswa);
            peminjaman.setBuku(buku);
            peminjaman.setTglpinjam(rs.getString(3));
            peminjaman.setTglkembali(rs.getString(4));
        }
        return peminjaman;
    }
    
    /**
     *
     * @return
     * @throws Exception
     */
    public List<Peminjaman> getAllPeminjaman()  throws Exception{
        DBHelper db = new DBHelper();
        String query = "select * from peminjaman";
        PreparedStatement ps = db.getKoneksi().prepareStatement(query);
        List<Peminjaman> list = new ArrayList();
        Mahasiswa mahasiswa = null;
        Buku buku = null;
        Peminjaman peminjaman = null;
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            peminjaman = new Peminjaman();
            mahasiswa = new MahasiswaDb().getMahasiswa(rs.getString(1));
            buku = new BukuDb().getBuku(rs.getString(2));
            peminjaman.setMahasiswa(mahasiswa);
            peminjaman.setBuku(buku);
            peminjaman.setTglpinjam(rs.getString(3));
            peminjaman.setTglkembali(rs.getString(4));
            list.add(peminjaman);
        }
        return list;
    }
}
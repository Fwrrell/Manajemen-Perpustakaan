package com.perpustakaan.patterns.structural.facade;

import java.time.LocalDate;
import java.util.List;

import com.perpustakaan.model.Buku;
import com.perpustakaan.patterns.behavioral.strategy.KonteksPencarian;
import com.perpustakaan.patterns.behavioral.strategy.PencarianBerdasarkanGenre;
import com.perpustakaan.patterns.behavioral.strategy.PencarianBerdasarkanJudul;
import com.perpustakaan.patterns.behavioral.strategy.PencarianBerdasarkanPenulis;
import com.perpustakaan.patterns.creational.factory.BukuFactory;
import com.perpustakaan.repository.BukuRepository;
import com.perpustakaan.repository.MemberRepository;
import com.perpustakaan.repository.PeminjamanRepository;
import com.perpustakaan.model.Member;
import com.perpustakaan.model.Peminjaman;

public class PerpustakaanFacade {
    KonteksPencarian pencarian;

    public PerpustakaanFacade() {
        pencarian = new KonteksPencarian(new PencarianBerdasarkanJudul());
    }

    public int RegistrasiMember(String nama, String email, String noTelpon) {
        return new MemberRepository().tambahMember(nama, email, noTelpon, false);
    }

    public boolean tambahBuku(String tipe, String judul, String penulis, String genre, int maksPeminjamanm,
            double hargaBeli, String info) {
        Buku newBuku = new BukuFactory().buatBuku(tipe, judul, penulis, genre, maksPeminjamanm, hargaBeli, info);
        return new BukuRepository().tambahBuku(newBuku);
    }

    public boolean tandaiBaru(int idBuku, boolean status) {
        return new BukuRepository().tandaiBaru(idBuku, status);
    }

    public boolean tandaiPopuler(int idBuku, boolean status) {
        return new BukuRepository().tandaiPopuler(idBuku, status);
    }

    public boolean tandaiLangka(int idBuku, boolean status) {
        return new BukuRepository().tandaiLangka(idBuku, status);
    }

    public boolean tandaiPromo(int idBuku, boolean status) {
        return new BukuRepository().tandaiPromo(idBuku, status);
    }

    public Peminjaman pinjamBuku(int idMember, int idBuku) {
        Member member = new MemberRepository().cariMember(idMember);
        Buku buku = new BukuRepository().cariBuku(idBuku);

        if (member == null) {
            throw new RuntimeException("Member dengan ID tersebut tidak ditemukan");
        }

        if (buku == null) {
            throw new RuntimeException("Buku dengan ID tersebut tidak ditemukan");
        }

        if (member.isBlocked()) {
            throw new RuntimeException("Member ini sedang diblokir dan tidak dapat meminjam buku");
        }

        PeminjamanRepository repo = new PeminjamanRepository();
        int jumlahAktif = repo.pinjamanAktifMember(idMember);
        if (jumlahAktif >= 3) {
            throw new RuntimeException("Member telah mencapai limit maksimal peminjaman buku");
        }

        if (!buku.getStatusBuku().getStatusBuku().equals("Tersedia")) {
            throw new RuntimeException(
                    "Buku tidak dapat dipinjam. Status saat ini: " + buku.getStatusBuku().getStatusBuku());
        }

        double deposit = 0;
        if (buku.getClass().getSimpleName().contains("Langka")) {
            deposit = buku.getHargaBeli() * 3.0;
        }

        buku.pinjam();
        new BukuRepository().updateStatusBuku(idBuku, "DIPINJAM");

        Peminjaman peminjaman = new Peminjaman(-1, member, buku, LocalDate.now());
        repo.tambahPeminjaman(peminjaman, deposit);

        return peminjaman;
    }

    public List<Buku> pencarianBuku(String kriteria, String kataKunci) {
        switch (kriteria.toLowerCase()) {
            case "judul":
                pencarian.setStrategy(new PencarianBerdasarkanJudul());
                break;
            case "penulis":
                pencarian.setStrategy(new PencarianBerdasarkanPenulis());
                break;
            case "genre":
                pencarian.setStrategy(new PencarianBerdasarkanGenre());
                break;
        }

        return pencarian.executePencarian(new BukuRepository().ambilBuku(), kataKunci);
    }

    public List<Member> ambilSemuaMember() {
        return new MemberRepository().getAllMember();
    }

    public boolean updateMember(Member m) {
        return new MemberRepository().updateMember(m);
    }

    public boolean ubahStatusBlokirMember(int idMember, boolean status) {
        return new MemberRepository().ubahStatusBlokir(idMember, status);
    }

    public boolean hapusMember(int idMember) {
        return new MemberRepository().hapusMember(idMember);
    }

    public Member cariMember(int idMember) {
        return new MemberRepository().cariMember(idMember);
    }

    public int getTotalBuku() {
        return new BukuRepository().hitungTotalBuku();
    }

    public int getTotalPeminjamAktif() {
        return new PeminjamanRepository().hitungPeminjamAktif();
    }

    public List<Buku> getBukuTersedia() {
        return new BukuRepository().ambilBukuTersedia();
    }
}

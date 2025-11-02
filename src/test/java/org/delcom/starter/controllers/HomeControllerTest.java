package org.delcom.starter.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HomeControllerUnitTest {

    private HomeController homeController;

    @BeforeEach
    void setUp() {
        homeController = new HomeController();
    }

    // =================================================================
    // == PENGUJIAN UNTUK METODE informasiNim
    // =================================================================

    @Test
    @DisplayName("Informasi NIM: Menguji NIM Sarjana Informatika yang valid")
    void testInformasiNim_ValidInformatika() {
        String nim = "11S22111";
        String expected = "Informasi NIM 11S22111:\n" +
                          ">> Program Studi: Sarjana Informatika\n" +
                          ">> Angkatan: 2022\n" +
                          ">> Urutan: 111\n";
        assertEquals(expected, homeController.informasiNim(nim));
    }
    
    // --- PENAMBAHAN TEST CASE UNTUK MENUTUPI SEMUA CABANG ---

    @Test
    @DisplayName("Informasi NIM: Menguji NIM Sarjana Sistem Informasi")
    void testInformasiNim_ValidSistemInformasi() {
        String nim = "12S21001";
        String expected = "Informasi NIM 12S21001:\n" +
                          ">> Program Studi: Sarjana Sistem Informasi\n" +
                          ">> Angkatan: 2021\n" +
                          ">> Urutan: 1\n";
        assertEquals(expected, homeController.informasiNim(nim));
    }

    @Test
    @DisplayName("Informasi NIM: Menguji NIM Sarjana Teknik Elektro")
    void testInformasiNim_ValidTeknikElektro() {
        String nim = "14S20010";
        String expected = "Informasi NIM 14S20010:\n" +
                          ">> Program Studi: Sarjana Teknik Elektro\n" +
                          ">> Angkatan: 2020\n" +
                          ">> Urutan: 10\n";
        assertEquals(expected, homeController.informasiNim(nim));
    }

    @Test
    @DisplayName("Informasi NIM: Menguji NIM Sarjana Manajemen Rekayasa")
    void testInformasiNim_ValidManajemenRekayasa() {
        String nim = "21S19020";
        String expected = "Informasi NIM 21S19020:\n" +
                          ">> Program Studi: Sarjana Manajemen Rekayasa\n" +
                          ">> Angkatan: 2019\n" +
                          ">> Urutan: 20\n";
        assertEquals(expected, homeController.informasiNim(nim));
    }
    
    @Test
    @DisplayName("Informasi NIM: Menguji NIM Sarjana Teknik Metalurgi")
    void testInformasiNim_ValidTeknikMetalurgi() {
        String nim = "22S22030";
        String expected = "Informasi NIM 22S22030:\n" +
                          ">> Program Studi: Sarjana Teknik Metalurgi\n" +
                          ">> Angkatan: 2022\n" +
                          ">> Urutan: 30\n";
        assertEquals(expected, homeController.informasiNim(nim));
    }

    @Test
    @DisplayName("Informasi NIM: Menguji NIM Sarjana Teknik Bioproses")
    void testInformasiNim_ValidTeknikBioproses() {
        String nim = "31S21040";
        String expected = "Informasi NIM 31S21040:\n" +
                          ">> Program Studi: Sarjana Teknik Bioproses\n" +
                          ">> Angkatan: 2021\n" +
                          ">> Urutan: 40\n";
        assertEquals(expected, homeController.informasiNim(nim));
    }

    @Test
    @DisplayName("Informasi NIM: Menguji NIM D3 Teknologi Informasi")
    void testInformasiNim_ValidD3TI() {
        String nim = "11322050";
        String expected = "Informasi NIM 11322050:\n" +
                          ">> Program Studi: Diploma 3 Teknologi Informasi\n" +
                          ">> Angkatan: 2022\n" +
                          ">> Urutan: 50\n";
        assertEquals(expected, homeController.informasiNim(nim));
    }
    
    @Test
    @DisplayName("Informasi NIM: Menguji NIM D3 Teknologi Komputer")
    void testInformasiNim_ValidD3TK() {
        String nim = "13321060";
        String expected = "Informasi NIM 13321060:\n" +
                          ">> Program Studi: Diploma 3 Teknologi Komputer\n" +
                          ">> Angkatan: 2021\n" +
                          ">> Urutan: 60\n";
        assertEquals(expected, homeController.informasiNim(nim));
    }
    
    // --- TEST CASE YANG SUDAH ADA SEBELUMNYA ---

    @Test
    @DisplayName("Informasi NIM: Menguji NIM D4 TRPL yang valid")
    void testInformasiNim_ValidD4TRPL() {
        String nim = "14421005";
        String expected = "Informasi NIM 14421005:\n" +
                          ">> Program Studi: Diploma 4 Teknologi Rekayasa Perangkat Lunak\n" +
                          ">> Angkatan: 2021\n" +
                          ">> Urutan: 5\n";
        assertEquals(expected, homeController.informasiNim(nim));
    }

    @Test
    @DisplayName("Informasi NIM: Menguji NIM dengan panjang tidak valid")
    void testInformasiNim_InvalidLength() {
        String nim = "11S2";
        String expected = "Format NIM tidak valid (minimal 5 karakter).";
        assertEquals(expected, homeController.informasiNim(nim));
    }

    @Test
    @DisplayName("Informasi NIM: Menguji NIM dengan kode prodi tidak dikenal")
    void testInformasiNim_UnknownProdi() {
        String nim = "99X20001";
        String expected = "Informasi NIM 99X20001:\n" +
                          ">> Program Studi: Tidak ditemukan!\n" +
                          ">> Angkatan: 2020\n" +
                          ">> Urutan: 1\n";
        assertEquals(expected, homeController.informasiNim(nim));
    }

    // =================================================================
    // == PENGUJIAN LAINNYA (TIDAK PERLU DIUBAH)
    // =================================================================

    @Test
    @DisplayName("Perolehan Nilai: Skenario umum menghasilkan grade BC")
    void testPerolehanNilai_GradeBC() {
        String input = "10\n20\n10\n20\n20\n20\n" +
                       "T|80|20\nT|90|20\nK|85|100\n" +
                       "UTS|80|100\nUAS|80|100\n---";
        String base64Input = Base64.getEncoder().encodeToString(input.getBytes());
        String expected = "Perolehan Nilai:\n" +
                          ">> Partisipatif: 0/100 (0.00/10)\n" +
                          ">> Tugas: 85/100 (17.00/20)\n" +
                          ">> Kuis: 85/100 (8.50/10)\n" +
                          ">> Proyek: 0/100 (0.00/20)\n" +
                          ">> UTS: 80/100 (16.00/20)\n" +
                          ">> UAS: 80/100 (16.00/20)\n\n" +
                          ">> Nilai Akhir: 57.50\n" +
                          ">> Grade: BC\n";
        assertEquals(expected.trim(), homeController.perolehanNilai(base64Input).trim());
    }

    @Test
    @DisplayName("Perolehan Nilai: Skenario nilai tinggi untuk mendapatkan grade A")
    void testPerolehanNilai_GradeA() {
        String input = "10\n20\n10\n20\n20\n20\n" +
                       "PA|100|100\nT|90|100\nK|95|100\n" +
                       "P|85|100\nUTS|80|100\nUAS|88|100\n---";
        String base64Input = Base64.getEncoder().encodeToString(input.getBytes());
        String expected = "Perolehan Nilai:\n" +
                          ">> Partisipatif: 100/100 (10.00/10)\n" +
                          ">> Tugas: 90/100 (18.00/20)\n" +
                          ">> Kuis: 95/100 (9.50/10)\n" +
                          ">> Proyek: 85/100 (17.00/20)\n" +
                          ">> UTS: 80/100 (16.00/20)\n" +
                          ">> UAS: 88/100 (17.60/20)\n\n" +
                          ">> Nilai Akhir: 88.10\n" +
                          ">> Grade: A\n";
        assertEquals(expected.trim(), homeController.perolehanNilai(base64Input).trim());
    }

    @Test
    @DisplayName("Perolehan Nilai: Mengabaikan baris input yang formatnya salah")
    void testPerolehanNilai_MalformedInput() {
        String input = "10\n20\n10\n20\n20\n20\n" +
                       "T|80|100\nK|90\nUTS|75|100\n---\n";
        String base64Input = Base64.getEncoder().encodeToString(input.getBytes());
        String expected = "Perolehan Nilai:\n" +
                        ">> Partisipatif: 0/100 (0.00/10)\n" +
                        ">> Tugas: 80/100 (16.00/20)\n" +
                        ">> Kuis: 0/100 (0.00/10)\n" +
                        ">> Proyek: 0/100 (0.00/20)\n" +
                        ">> UTS: 75/100 (15.00/20)\n" +
                        ">> UAS: 0/100 (0.00/20)\n\n" +
                        ">> Nilai Akhir: 31.00\n" +
                        ">> Grade: E\n";
        assertEquals(expected.trim(), homeController.perolehanNilai(base64Input).trim());
    }

    @Test
    @DisplayName("Perbedaan L: Menguji matriks ukuran 3x3 (ganjil)")
    void testPerbedaanL_OddMatrix() {
        String input = "3\n1 2 3\n4 5 6\n7 8 9";
        String base64Input = Base64.getEncoder().encodeToString(input.getBytes());
        String expected = "Nilai L: 20\n" +
                        "Nilai Kebalikan L: 20\n" +
                        "Nilai Tengah: 5\n" +
                        "Perbedaan: 0\n" +
                        "Dominan: 5\n";
        assertEquals(expected, homeController.perbedaanL(base64Input));
    }

    @Test
    @DisplayName("Perbedaan L: Menguji matriks ukuran 1x1 (kasus khusus)")
    void testPerbedaanL_SizeOneMatrix() {
        String input = "1\n42";
        String base64Input = Base64.getEncoder().encodeToString(input.getBytes());
        String expected = "Nilai L: Tidak Ada\n" +
                        "Nilai Kebalikan L: Tidak Ada\n" +
                        "Nilai Tengah: 42\n" +
                        "Perbedaan: Tidak Ada\n" +
                        "Dominan: 42";
        assertEquals(expected, homeController.perbedaanL(base64Input));
    }

    @Test
    @DisplayName("Perbedaan L: Menguji matriks ukuran 2x2 (kasus khusus)")
    void testPerbedaanL_SizeTwoMatrix() {
        String input = "2\n1 2\n3 4";
        String base64Input = Base64.getEncoder().encodeToString(input.getBytes());
        String expected = "Nilai L: Tidak Ada\n" +
                        "Nilai Kebalikan L: Tidak Ada\n" +
                        "Nilai Tengah: 10\n" +
                        "Perbedaan: Tidak Ada\n" +
                        "Dominan: 10";
        assertEquals(expected, homeController.perbedaanL(base64Input));
    }

    @Test
    @DisplayName("Paling Ter: Skenario umum dengan data bervariasi")
    void testPalingTer_GeneralCase() {
        String input = "10\n20\n10\n30\n20\n10\n5\n---";
        String base64Input = Base64.getEncoder().encodeToString(input.getBytes());
        String expected = "Tertinggi: 30\n" +
                        "Terendah: 5\n" +
                        "Terbanyak: 10 (3x)\n" +
                        "Tersedikit: 5 (1x)\n" +
                          "Jumlah Tertinggi: 20 * 2 = 40\n" +
                          "Jumlah Terendah: 5 * 1 = 5\n";
        assertEquals(expected, homeController.palingTer(base64Input));
    }

    @Test
    @DisplayName("Paling Ter: Menguji kasus dengan input kosong")
    void testPalingTer_EmptyInput() {
        String input = "---\n";
        String base64Input = Base64.getEncoder().encodeToString(input.getBytes());
        String expected = "Tidak ada data untuk diproses.";
        assertEquals(expected, homeController.palingTer(base64Input));
    }

    @Test
    @DisplayName("Paling Ter: Menguji skenario tie-breaking (kasus seri)")
    void testPalingTer_TieBreaking() {
        String input = "10\n20\n10\n20\n5\n30\n---";
        String base64Input = Base64.getEncoder().encodeToString(input.getBytes());
        String expected = "Tertinggi: 30\n" +
                        "Terendah: 5\n" +
                        "Terbanyak: 20 (2x)\n" +
                        "Tersedikit: 5 (1x)\n" +
                        "Jumlah Tertinggi: 20 * 2 = 40\n" +
                        "Jumlah Terendah: 5 * 1 = 5\n";
        assertEquals(expected, homeController.palingTer(base64Input));
    }
}
package org.delcom.starter.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Scanner;
import java.util.Base64;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class HomeController {

    @GetMapping("/informasi-nim/{nim}")
    public String informasiNim(@PathVariable String nim) {
        StringBuilder response = new StringBuilder();
        String NIM = nim;

        if (NIM.length() < 5) {
            return "Format NIM tidak valid (minimal 5 karakter).";
        }

        String prodi = NIM.substring(0, 3);
        String angkatan = "20" + NIM.substring(3, 5);
        String urutanStr = (NIM.length() > 5) ? NIM.substring(5) : "0";
        int urutanint = Integer.parseInt(urutanStr);

        response.append("Informasi NIM ").append(NIM).append(":\n");

        String namaProdi;
        switch (prodi) {
            case "11S": namaProdi = "Sarjana Informatika"; break;
            case "12S": namaProdi = "Sarjana Sistem Informasi"; break;
            case "14S": namaProdi = "Sarjana Teknik Elektro"; break;
            case "21S": namaProdi = "Sarjana Manajemen Rekayasa"; break;
            case "22S": namaProdi = "Sarjana Teknik Metalurgi"; break;
            case "31S": namaProdi = "Sarjana Teknik Bioproses"; break;
            case "144": namaProdi = "Diploma 4 Teknologi Rekayasa Perangkat Lunak"; break;
            case "113": namaProdi = "Diploma 3 Teknologi Informasi"; break;
            case "133": namaProdi = "Diploma 3 Teknologi Komputer"; break;
            default: namaProdi = "Tidak ditemukan!"; break;
        }
        response.append(">> Program Studi: ").append(namaProdi).append("\n");
        response.append(">> Angkatan: ").append(angkatan).append("\n");
        response.append(">> Urutan: ").append(urutanint).append("\n");

        return response.toString();
    }


    @GetMapping("/perolehan-nilai/{strBase64}")
    public String perolehanNilai(@PathVariable String strBase64) {
        byte[] decodedBytes = Base64.getDecoder().decode(strBase64);
        String decodedInput = new String(decodedBytes);
        Scanner sc = new Scanner(decodedInput);
        StringBuilder response = new StringBuilder();

        String[] kategori = {"PA", "T", "K", "P", "UTS", "UAS"};
        double[] bobotKategori = new double[6];
        for (int i = 0; i < 6; i++) {
            if (sc.hasNextLine()) {
                bobotKategori[i] = Double.parseDouble(sc.nextLine().trim());
            }
        }

        double[] totalNilai = new double[6];
        double[] totalBobot = new double[6];

        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.equals("---") || line.isEmpty()) break;
            String[] parts = line.split("\\|");
            if (parts.length != 3) continue;

            String simbol = parts[0].trim();
            double nilai = Double.parseDouble(parts[1].trim());
            double bobot = Double.parseDouble(parts[2].trim());

            int idx = -1;
            for (int i = 0; i < kategori.length; i++) {
                if (kategori[i].equals(simbol)) {
                    idx = i;
                    break;
                }
            }
            if (idx != -1) {
                totalNilai[idx] += nilai * bobot;
                totalBobot[idx] += bobot;
            }
        }

        response.append("Perolehan Nilai:\n");
        double nilaiAkhir = 0.0;

        for (int i = 0; i < kategori.length; i++) {
            double rata = (totalBobot[i] > 0) ? totalNilai[i] / totalBobot[i] : 0.0;
            double kontribusi = rata * bobotKategori[i] / 100.0;
            nilaiAkhir += kontribusi;

            // --- Logika dari 'namaLengkap' dipindahkan ke sini (Inlined) ---
            String namaKategoriLengkap;
            switch (kategori[i]) {
                case "PA": namaKategoriLengkap = "Partisipatif"; break;
                case "T":  namaKategoriLengkap = "Tugas"; break;
                case "K":  namaKategoriLengkap = "Kuis"; break;
                case "P":  namaKategoriLengkap = "Proyek"; break;
                case "UTS":namaKategoriLengkap = "UTS"; break;
                case "UAS":namaKategoriLengkap = "UAS"; break;
                default:   namaKategoriLengkap = kategori[i]; break;
            }
            
            response.append(String.format(">> %s: %.0f/100 (%.2f/%s)\n",
                    namaKategoriLengkap, rata, kontribusi, (int)bobotKategori[i]));
        }

        response.append(String.format("\n>> Nilai Akhir: %.2f\n", nilaiAkhir));

        // --- Logika dari 'getGrade' dipindahkan ke sini (Inlined) ---
        String grade;
        if (nilaiAkhir >= 79.5) grade = "A";
        else if (nilaiAkhir >= 72) grade = "AB";
        else if (nilaiAkhir >= 64.5) grade = "B";
        else if (nilaiAkhir >= 57) grade = "BC";
        else if (nilaiAkhir >= 49.5) grade = "C";
        else if (nilaiAkhir >= 34) grade = "D";
        else grade = "E";
        
        response.append(String.format(">> Grade: %s\n", grade));
        sc.close();
        return response.toString();
    }

    /**
     * Endpoint untuk menghitung nilai L, kebalikannya, dan nilai dominan dari matriks.
     * @param strBase64 Input multi-baris (ukuran dan elemen matriks) yang di-encode dengan Base64.
     * @return String yang berisi hasil kalkulasi matriks.
     */
    @GetMapping("/perbedaan-l/{strBase64}")
    public String perbedaanL(@PathVariable String strBase64) {
        byte[] decodedBytes = Base64.getDecoder().decode(strBase64);
        String decodedInput = new String(decodedBytes);
        Scanner sc = new Scanner(decodedInput);
        StringBuilder response = new StringBuilder();

        int s = sc.nextInt();
        int[][] b = new int[s][s];
        for (int i = 0; i < s; i++) {
            for (int j = 0; j < s; j++) {
                b[i][j] = sc.nextInt();
            }
        }
        sc.close();

        if (s == 1) {
            int tengah = b[0][0];
            return "Nilai L: Tidak Ada\nNilai Kebalikan L: Tidak Ada\nNilai Tengah: " + tengah + "\nPerbedaan: Tidak Ada\nDominan: " + tengah;
        }

        if (s == 2) {
            int total = 0;
            for (int i = 0; i < s; i++) {
                for (int j = 0; j < s; j++) {
                    total += b[i][j];
                }
            }
            return "Nilai L: Tidak Ada\nNilai Kebalikan L: Tidak Ada\nNilai Tengah: " + total + "\nPerbedaan: Tidak Ada\nDominan: " + total;
        }
        
        int tengah;
        if (s % 2 == 1) {
            tengah = b[s / 2][s / 2];
        } else {
            int mid1 = s / 2 - 1;
            int mid2 = s / 2;
            tengah = b[mid1][mid1] + b[mid1][mid2] + b[mid2][mid1] + b[mid2][mid2];
        }

        int nilaiL = 0;
        for (int i = 0; i < s; i++) nilaiL += b[i][0];
        for (int j = 1; j <= s - 2; j++) nilaiL += b[s - 1][j];

        int nilaiKebalikan = 0;
        for (int i = 0; i < s; i++) nilaiKebalikan += b[i][s - 1];
        for (int j = 1; j <= s - 2; j++) nilaiKebalikan += b[0][j];

        int perbedaan = Math.abs(nilaiL - nilaiKebalikan);

        int dominan;
        if (nilaiL > nilaiKebalikan) {
            dominan = nilaiL;
        } else if (nilaiKebalikan > nilaiL) {
            dominan = nilaiKebalikan;
        } else {
            dominan = tengah;
        }

        response.append("Nilai L: ").append(nilaiL).append("\n");
        response.append("Nilai Kebalikan L: ").append(nilaiKebalikan).append("\n");
        response.append("Nilai Tengah: ").append(tengah).append("\n");
        response.append("Perbedaan: ").append(perbedaan).append("\n");
        response.append("Dominan: ").append(dominan).append("\n");
        
        return response.toString();
    }
    
    /**
     * Endpoint untuk analisis statistik mencari nilai "paling ter" dari serangkaian angka.
     * @param strBase64 Input multi-baris (list angka diakhiri '---') yang di-encode dengan Base64.
     * @return String yang berisi hasil analisis statistik.
     */
    @GetMapping("/paling-ter/{strBase64}")
    public String palingTer(@PathVariable String strBase64) {
        byte[] decodedBytes = Base64.getDecoder().decode(strBase64);
        String decodedInput = new String(decodedBytes);
        Scanner scanner = new Scanner(decodedInput);
        StringBuilder response = new StringBuilder();

        HashMap<Integer, Integer> hashMapNilai = new HashMap<>();
        ArrayList<Integer> daftarNilai = new ArrayList<>();
        
        while (scanner.hasNextLine()) {
            String masukan = scanner.nextLine();
            if (masukan.equals("---")) break;
            
            int nilai = Integer.parseInt(masukan);
            int count = hashMapNilai.getOrDefault(nilai, 0) + 1;
            
            hashMapNilai.put(nilai, count);
            daftarNilai.add(nilai);
        }
        
        if (daftarNilai.isEmpty()) {
            return "Tidak ada data untuk diproses.";
        }

        int[] arrayNilai = daftarNilai.stream().mapToInt(Integer::intValue).toArray();
        
        int nilaiTertinggi = arrayNilai[0];
        int nilaiTerendah = arrayNilai[0];
        
        for (int nilai : arrayNilai) {
            if (nilaiTertinggi < nilai) nilaiTertinggi = nilai;
            if (nilaiTerendah > nilai) nilaiTerendah = nilai;
        }

        int nilaiJumlahTerendah = -1;
        int jumlahTerendah = Integer.MAX_VALUE;

        for (HashMap.Entry<Integer, Integer> entry : hashMapNilai.entrySet()) {
            int total = entry.getKey() * entry.getValue();
            if (total < jumlahTerendah) {
                jumlahTerendah = total;
                nilaiJumlahTerendah = entry.getKey();
            } else if (total == jumlahTerendah) {
                if (entry.getKey() < nilaiJumlahTerendah) {
                    nilaiJumlahTerendah = entry.getKey();
                }
            }
        }
        
        int nilaiTerbanyak = -1;
        int frekuensiTerbanyak = 0;
        for (int i = arrayNilai.length - 1; i >= 0; i--) {
            int nilai = arrayNilai[i];
            int frekuensi = hashMapNilai.get(nilai);
            if (frekuensi > frekuensiTerbanyak) {
                frekuensiTerbanyak = frekuensi;
                nilaiTerbanyak = nilai;
            } else if (frekuensi == frekuensiTerbanyak) {
                if (nilai > nilaiTerbanyak) {
                    nilaiTerbanyak = nilai;
                }
            }
        }

        int nilaiTerdikit = -1;
        int frekuensiTerdikit = Integer.MAX_VALUE;
        for (int i = arrayNilai.length - 1; i >= 0; i--) {
            int nilai = arrayNilai[i];
            int frekuensi = hashMapNilai.get(nilai);
            if (frekuensi < frekuensiTerdikit) {
                frekuensiTerdikit = frekuensi;
                nilaiTerdikit = nilai;
            } else if (frekuensi == frekuensiTerdikit) {
                if (nilai < nilaiTerdikit) {
                    nilaiTerdikit = nilai;
                }
            }
        }

        int jumlahTertinggi = 0;
        int nilaiJumlahTertinggi = 0;
        
        for (HashMap.Entry<Integer, Integer> entry : hashMapNilai.entrySet()) {
            int jumlah = entry.getKey() * entry.getValue();
            if (jumlah > jumlahTertinggi) {
                jumlahTertinggi = jumlah;
                nilaiJumlahTertinggi = entry.getKey();
            } else if (jumlah == jumlahTertinggi) {
                if (entry.getKey() > nilaiJumlahTertinggi) {
                    nilaiJumlahTertinggi = entry.getKey();
                }
            }
        }
        int frekuensiNilaiJumlahTertinggi = hashMapNilai.get(nilaiJumlahTertinggi);

        response.append("Tertinggi: ").append(nilaiTertinggi).append("\n");
        response.append("Terendah: ").append(nilaiTerendah).append("\n");
        response.append("Terbanyak: ").append(nilaiTerbanyak).append(" (").append(frekuensiTerbanyak).append("x)\n");
        response.append("Tersedikit: ").append(nilaiTerdikit).append(" (").append(frekuensiTerdikit).append("x)\n");
        response.append("Jumlah Tertinggi: ").append(nilaiJumlahTertinggi).append(" * ").append(frekuensiNilaiJumlahTertinggi).append(" = ").append(jumlahTertinggi).append("\n");
        response.append("Jumlah Terendah: ").append(nilaiJumlahTerendah).append(" * ").append(hashMapNilai.get(nilaiJumlahTerendah)).append(" = ").append(jumlahTerendah).append("\n");

        scanner.close();
        return response.toString();
    }
}
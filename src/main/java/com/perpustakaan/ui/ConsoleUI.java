package com.perpustakaan.ui;

import java.util.Scanner;

public class ConsoleUI {
    private final Scanner scanner;

    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
    }

    public void tampilkanHeader(String judul) {
        System.out.println("\n========================================");
        System.out.println(" " + judul);
        System.out.println("========================================");
    }

    public void tampilkanPesan(String pesan) {
        System.out.println(pesan);
    }

    public void tampilkanError(String pesanError) {
        System.out.println("ERROR: " + pesanError);
    }

    public void tampilkanSukses(String pesanSukses) {
        System.out.println(pesanSukses);
    }

    public String mintaInput(String prompt) {
        System.out.print(prompt + ": ");
        return scanner.nextLine();
    }
}
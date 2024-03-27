package com.example.demo.Service;

import com.example.demo.books.Medi;

import java.util.List;

public interface MediService {
    List<Medi> findAllMedi();

    Medi findMediByid(int id);

    void addMedi(Medi medi);

    void updateMedi(Medi medi);

    void deleteMedi(int id);
}

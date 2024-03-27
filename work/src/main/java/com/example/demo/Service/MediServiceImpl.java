package com.example.demo.Service;

import com.example.demo.books.Medi;
import com.example.demo.interfacebook.MediMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MediServiceImpl implements MediService {
    @Autowired

    private MediMapper mediMapper;

    @Override
    public List<Medi> findAllMedi(){
        return mediMapper.selectList(null);
    }

    @Override
    public Medi findMediByid(int id){
        return mediMapper.selectById(id);
    }

    @Override
    public void addMedi(Medi medi){
        mediMapper.insert(medi);
    }

    @Override
    public void updateMedi(Medi medi){
        mediMapper.updateById(medi);
    }

    @Override
    public void deleteMedi(int id){
        mediMapper.deleteById(id);
    }
}

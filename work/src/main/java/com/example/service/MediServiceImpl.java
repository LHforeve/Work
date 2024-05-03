package com.example.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.MediMapper;
import com.example.medis.Medi;
import com.example.service.IMediService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

@Service
public class MediServiceImpl extends ServiceImpl<MediMapper, Medi> implements IMediService {
    @Override
    public boolean list(int id) {
        return false;
    }

    @Override
    public void deleteRefundedMedis() {

    }

    // 如果有自定义的方法，可以在这里进行实现
    // 注意：MyBatis-Plus会自动实现基本的增删改查方法，无需额外实现。

}
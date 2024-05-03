package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.medis.Medi;

public interface IMediService extends IService<Medi>{

    boolean list(int id);

    void deleteRefundedMedis();
}



package com.onetoo.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onetoo.entity.Company;
import com.onetoo.mapper.CompanyMapper;
import com.onetoo.service.CompanyService;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements CompanyService {
}

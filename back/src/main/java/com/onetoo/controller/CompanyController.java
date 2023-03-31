package com.onetoo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.onetoo.common.R;
import com.onetoo.entity.Company;

import com.onetoo.entity.Member;
import com.onetoo.service.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    CompanyService companyService;


    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        if (name == null)
            log.info("分页查询！");
        else
            log.info("按名字要求条件分页查询");
//        构造分页查询器
        Page pageInfo = new Page(page, pageSize);
//        构造条件构造器
        LambdaQueryWrapper<Company> queryWrapper = new LambdaQueryWrapper<>();
//        按照name的值添加条件
        queryWrapper.like(StringUtils.isNotEmpty(name), Company::getCompanyName, name);
//        添加排序条件
        queryWrapper.orderByDesc(Company::getUpdateTime);
//      这一步将结果已经返回到了pageInfo中
        companyService.page(pageInfo, queryWrapper);

        log.info(String.valueOf(pageInfo));
        return R.success(pageInfo);

    }
    @PostMapping("/save")
    public R<String> save(HttpServletRequest request, @RequestBody Company company) {
        log.info("新增门店，门店信息：{}", company.toString());

        // employee.setCreateTime(LocalDateTime.now());
        //employee.setUpdateTime(LocalDateTime.now());

        //Long empId = (Long) request.getSession().getAttribute("employee");

        //employee.setCreateUser(empId);
        //employee.setUpdateUser(empId);

        companyService.save(company);
        return R.success("新增员工成功");
    }

    @DeleteMapping("/delete")
    public R<String> deletecompany(Long id){
        log.info("id为：{}",id);
        if(companyService.removeById(id))
            return R.success("删除成功");
        else return R.error("删除失败");

    }
    @PostMapping("/getbyid")
    public R<Company> getById(@RequestBody Company company){
//如何接受lang型数据？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？

        log.info("根据ID查询员工信息");
        Company company1 = companyService.getById(company.getId());


        if(company1!=null){
            return R.success(company1);
        }
        return R.error("没有查询到对应的员工信息");
    }
    @PostMapping("/updatecompany")
    public R<String> updatecompany(@RequestBody Company company) {

        log.info(company.toString());

        companyService.updateById(company);
        return R.success("信息修改成功");

    }
    @GetMapping("/getNames")
    public R<List<String>> GetNames(){
        LambdaQueryWrapper<Company> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Company::getStatus,1);
        List<Company> companyList = companyService.list(lambdaQueryWrapper);
        ArrayList<String> arrayList = new ArrayList<>();
        for(Company company:companyList){
            arrayList.add(company.getCompanyName());
        }
        log.info(String.valueOf(arrayList));
        return R.success(arrayList);
    }
    @GetMapping("/getCompanies")
    public R<List<Company>> GetCompanies(){
        List<Company> companies = companyService.list();
        return R.success(companies);
    }
}

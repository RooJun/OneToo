package com.onetoo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.onetoo.common.BaseContext;
import com.onetoo.common.R;
import com.onetoo.entity.Member;
import com.onetoo.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @PostMapping("/login")
    public R<Member> login(HttpServletRequest request, @RequestBody Member member) {
        log.info("访问成功");
        String password = member.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        LambdaQueryWrapper<Member> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Member::getUsername, member.getUsername());
        Member one = memberService.getOne(queryWrapper);

        if (one == null) {
            return R.error("登录失败,该用户不存在");
        }
        if (!one.getPassword().equals(password)) {
            return R.error("登录失败！密码不正确");
        }

        if (one.getStatus() == 0) {
            return R.error("账号已禁用！");
        }
        request.getSession().setAttribute("member", one.getId());
        log.info(String.valueOf(request.getSession().getAttribute("member")));
        BaseContext.setCurrentId(one.getId());
        return R.success(one);
    }

    @GetMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("member");
        return R.success("退出成功");
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        if (name == null)
            log.info("分页查询！");
        else
            log.info("按名字要求条件分页查询");


//        构造分页查询器
        Page pageInfo = new Page(page, pageSize);
//        构造条件构造器
        LambdaQueryWrapper<Member> queryWrapper = new LambdaQueryWrapper<>();
//        按照name的值添加条件
        queryWrapper.like(StringUtils.isNotEmpty(name), Member::getName, name);
//        添加排序条件
        queryWrapper.orderByDesc(Member::getUpdateTime);
//      这一步将结果已经返回到了pageInfo中
        memberService.page(pageInfo, queryWrapper);
        return R.success(pageInfo);

    }

    @PostMapping("/save")
    public R<String> save(HttpServletRequest request, @RequestBody Member member) {
        log.info("新增员工，员工信息：{}", member.toString());

        member.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        // employee.setCreateTime(LocalDateTime.now());
        //employee.setUpdateTime(LocalDateTime.now());

        //Long empId = (Long) request.getSession().getAttribute("employee");

        //employee.setCreateUser(empId);
        //employee.setUpdateUser(empId);

        memberService.save(member);
        return R.success("新增员工成功");
    }

    @PostMapping("/register")
    public R<String> register(@RequestBody Member member) {
        log.info(member.toString());

        String password = member.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        member.setPassword(password);
        memberService.save(member);
        return R.success("注册成功");
    }

    @PostMapping("/findpassword")
    public R<String> Findpassword(@RequestBody Member member) {
        log.info(member.toString());

        String password = member.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        member.setPassword(password);
        LambdaQueryWrapper<Member> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Member::getPhone, member.getPhone());
        memberService.update(member, queryWrapper);

        return R.success("密码修改成功");
    }

    @PostMapping("/update member")
    public R<String> updatemember(@RequestBody Member member) {

        log.info(member.toString());

        memberService.updateById(member);
        return R.success("信息修改成功");

    }

    @PostMapping("/getbyid")
    public R<Member> getById(@RequestBody Member member) {
//如何接受lang型数据？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？

        log.info("根据ID查询员工信息");
        Member member1 = memberService.getById(member.getId());


        if (member1 != null) {
            return R.success(member);
        }
        return R.error("没有查询到对应的员工信息");
    }
}

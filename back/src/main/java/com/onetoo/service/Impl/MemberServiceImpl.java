package com.onetoo.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onetoo.entity.Member;
import com.onetoo.mapper.MemberMapper;
import com.onetoo.service.MemberService;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {
}

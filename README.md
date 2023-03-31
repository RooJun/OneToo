# OneToo（连锁门店外卖订餐系统）
## 说明：

本项目为本人的第一个项目，用于在学习的过程中的练手项目；当时学完Spring、SpringBoot后想弄个项目练练手，正好当时不久就是Web课设，于是我又去学了一点Vue，时间比较赶，只学到了store部分。这个项目的雏形就是在这次Web课设，前端用了一个Vue工程，但是它有两个界面，一个是模拟移动端网页，一个是商家的后台管理页面，学的前端知识有限，写的很一般，可能有很多Bug；后端用的是SpringBoot，使用的技术比较简单，就是一些CRUD。这个项目后面还用了两个课设（数据库课设完善表结构设计和软件工程课设完成项目报告，报告是几个人的功劳就不开源了），又完善了两遍，才到它现在的样子。后来的那个暑假，想模拟企业部署上线的过程，于是成功将它部署在虚拟机上。这个项目算我的“处女座”（哈哈哈），虽然简单，但是我学到了很多东西！！！

## 简介：

基于Vue+SpringBoot+Redis+MySQL的网上订餐系统，能够实现商家进行人员、菜品、门店和订单管理，用户能注册登录和订餐。

### 总体设计：

![image-20230331194104639](https://gitee.com/looojun/images/raw/master/image-20230331194104639.png)

#### 技术特点：

- 基于本系统是连锁门店的管理，连锁门店足够多的情况下，就需要考虑并发问题，而数据库的访问是非常慢的，所以本系统采用mysql主从复制的模式。该模式最大的特点就是读写分离，由于部分报表SQL语句非常的慢，导致锁表，影响前台服务。如果前台使用master，报表使用slave，那么报表sql将不会造成前台锁，保证了前台速度。还可以保证数据的可靠性，这个模式相当于一份数据存储了两份，当某一台机器宕机时，用户数据依然不会丢失。
- 本系统主要使用的是redis缓存的功能，还是基于门店的数量增多而产生的并发问题，如果所有查询操作都走数据库，而数据库的访问速度又很慢，导致系统的效率大大降低，用户的体验感也会随之下降。如果将一些访问率高的数据存储在redis中，就可以大大提高系统效率，在本系统中主要指的是客户端要显示的菜品数据，这些菜品数据会出现在所有用户的首页中，访问率很高。
- 本系统使用Nginx的代理功能，代理客户端页面的数据，同时它的代理可以很好的保护服务器。

### 界面设计：

##### 1、后台界面

###### （1）登录界面

![img](https://gitee.com/looojun/images/raw/master/wps1.jpg)

###### （2）门店管理

① 门店展示

![image-20230331201250812](https://gitee.com/looojun/images/raw/master/image-20230331201250812.png) 

② 添加门店

![img](https://gitee.com/looojun/images/raw/master/wps3.jpg)

###### （3）人员管理

① 用户展示

![image-20230331201238045](https://gitee.com/looojun/images/raw/master/image-20230331201238045.png) 

② 添加用户

![image-20230331201230356](https://gitee.com/looojun/images/raw/master/image-20230331201230356.png) 

###### （4）菜品管理

① 菜品展示

![img](https://gitee.com/looojun/images/raw/master/wps6.jpg)

② 添加菜品![img](https://gitee.com/looojun/images/raw/master/wps7.jpg)

###### （5）订单管理

![image-20230331201213537](https://gitee.com/looojun/images/raw/master/image-20230331201213537.png) 

###### （6）配送管理

![image-20230331201201705](https://gitee.com/looojun/images/raw/master/image-20230331201201705.png) 

##### 2、APP界面

###### （1）登录界面

![img](https://gitee.com/looojun/images/raw/master/wps10.jpg) 

###### （2）注册界面

![image-20230331201145798](https://gitee.com/looojun/images/raw/master/image-20230331201145798.png)

###### （3）找回密码界面

![image-20230331201133352](https://gitee.com/looojun/images/raw/master/image-20230331201133352.png)

###### （4）用户首页

![img](https://gitee.com/looojun/images/raw/master/wps13.jpg)

###### （5）用户订单界面

![img](https://gitee.com/looojun/images/raw/master/wps14.jpg) 

###### （6）购物车界面

![img](https://gitee.com/looojun/images/raw/master/wps15.jpg) 

###### （7）支付界面

![img](https://gitee.com/looojun/images/raw/master/wps16.jpg) 

## 项目目录简介

### back部分：

```
│  .gitignore
│  list.txt
│  pom.xml（坐标依赖）
│  
├─.idea
│  
│      
├─src
│  ├─main
│  │  ├─java
│  │  │  └─com
│  │  │      └─onetoo
│  │  │          │  OnetooApplication.java
│  │  │          │  
│  │  │          ├─common
│  │  │          │      BaseContext.java（基于ThreadLocal封装工具类，用于保存和获取当前登录用户的id）
│  │  │          │      CustomException.java（自定义业务异常类）
│  │  │          │      GlobalExceptionHandler.java（全局异常处理器）
│  │  │          │      JacksonObjectMapper.java（对象映射器:基于jackson将Java对象转为json，或者将json转为Java对象）
│  │  │          │      MyMetaObjectHandler.java（公共字段自动填充）
│  │  │          │      R.java（统一返回类）
│  │  │          │      
│  │  │          ├─config
│  │  │          │      MybatisPlusConfig.java（配置MP的分页查询插件）
│  │  │          │      RedisConfig.java（配置String序列化器）
│  │  │          │      WebMvcConfig.java
│  │  │          │      
│  │  │          ├─controller（表示层）
│  │  │          │      CategoryController.java
│  │  │          │      CommonController.java
│  │  │          │      CompanyController.java
│  │  │          │      DishController.java
│  │  │          │      MemberController.java
│  │  │          │      OrderDetailController.java
│  │  │          │      OrdersController.java
│  │  │          │      ShoppingCartController.java
│  │  │          │      
│  │  │          ├─entity（实体，匹配数据库表的各个字段）
│  │  │          │      Category.java
│  │  │          │      Company.java
│  │  │          │      Dish.java
│  │  │          │      Member.java
│  │  │          │      OrderDetail.java
│  │  │          │      Orders.java
│  │  │          │      ShoppingCart.java
│  │  │          │      
│  │  │          ├─entityPlus（返回类）
│  │  │          │      DishDto.java
│  │  │          │      
│  │  │          ├─filter（登录拦截）
│  │  │          │      LoginCheckFilter.java
│  │  │          │      
│  │  │          ├─mapper
│  │  │          │      CategoryMapper.java
│  │  │          │      CompanyMapper.java
│  │  │          │      DishMapper.java
│  │  │          │      MemberMapper.java
│  │  │          │      OrderDetailMapper.java
│  │  │          │      OrdersMapper.java
│  │  │          │      ShoppingCartMapper.java
│  │  │          │      
│  │  │          └─service
│  │  │              │  CategoryService.java
│  │  │              │  CompanyService.java
│  │  │              │  DishService.java
│  │  │              │  MemberService.java
│  │  │              │  OrderDetailService.java
│  │  │              │  OrdersService.java
│  │  │              │  ShoppingCartService.java
│  │  │              │  
│  │  │              └─Impl
│  │  │                      CategoryServiceImpl.java
│  │  │                      CompanyServiceImpl.java
│  │  │                      DishServiceImpl.java
│  │  │                      MemberServiceImpl.java
│  │  │                      OrderDetailServiceImpl.java
│  │  │                      OrdersServiceImpl.java
│  │  │                      ShoppingCartServiceImpl.java
│  │  │                      
│  │  └─resources（配置）
│  │          application.yml
│  │          
│  └─test
│      └─java
└─target
    

```

### front部分：

```
│  App.vue
│  list.txt
│  main.js
│  
├─assets（图片资源）
│      cart_active.png
│      img.png
│      login-l.png
│      logo.png
│      pexels-artem-beliaikin-929192.jpg
│      
├─component（管理后台的导航和头部组件）
│      Header.vue
│      Navigate.vue
│      
├─pages
│  ├─back（后台管理组件）
│  │  │  Company.vue
│  │  │  Delivery.vue
│  │  │  Home.vue
│  │  │  Login.vue
│  │  │  Member.vue
│  │  │  Order.vue
│  │  │  
│  │  └─Dish
│  │          addCategory.vue
│  │          addDish.vue
│  │          Dish.vue
│  │          updateDish.vue
│  │          
│  └─front（模拟用户端组件）
│      │  Forgetpassword.vue
│      │  Register.vue
│      │  Userlogin.vue
│      │  
│      └─user
│              Dishdetail.vue
│              Pay.vue
│              Shopcart.vue
│              User.vue
│              Userhome.vue
│              Usermy.vue
│              Userorder.vue
│              
├─router（路由）
│      index.js
│      
└─store（组件间传递信息）
        dish.js
        index.js
        navigate_header.js
        shop.js
```

### img文件：

保存图片资源（菜品信息）

## 项目部署

1. 下载打开VMware，创建Linux虚拟机，网卡设置，设置静态ip

   参考[ VMware创建Linux虚拟机，网卡设置，设置静态ip_finashell如何设置为静态ip_怎特的博客-CSDN博客](https://blog.csdn.net/m0_52765511/article/details/126177138?spm=1001.2014.3001.5502)

2. 虚拟机上安装MySQL、redis，其中MySQL安装可以参考：[ Linux虚拟机安装MySQL并启动服务，Windows下访问Linux上的mysql_虚拟机启动mysql_怎特的博客-CSDN博客](https://blog.csdn.net/m0_52765511/article/details/126193626?spm=1001.2014.3001.5502)

3. MySQL主从复制配置：[(读写分离，MySQL主从复制，简单实现_mysql读写分离如何实现主从复制_怎特的博客-CSDN博客](https://blog.csdn.net/m0_52765511/article/details/126193541?spm=1001.2014.3001.5502)

4. 用Nginx部署前端：[(Nginx简介，Linux上简单实现部署静态资源、反向代理、负载均衡_怎特的博客-CSDN博客](https://blog.csdn.net/m0_52765511/article/details/126212249?spm=1001.2014.3001.5502)

   - 将dist文件上传到Nginx的html目录下

   - Nginx配置文件nginx.conf

     ```
     server{
       listen 80;
       server_name localhost;
     
       location /{
         root html/dist;
         index index.html;
       }
     
       location ^~ /api/{
               rewrite ^/api/(.*)$ /$1 break;
               proxy_pass http://192.168.188.101:8080;
       }
     
       error_page 500 502 503 504 /50x.html;
       location = /50x.html{
           root html;
       }
     }
     
     ```

5. 部署后端

   参考[Linux系统下手动部署Web项目_web项目部署在linux哪个文件夹_怎特的博客-CSDN博客](https://blog.csdn.net/m0_52765511/article/details/125981571?spm=1001.2014.3001.5502)



*（注：后端项目参考自黑马，黑马yyds）*

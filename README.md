# douban_parent
蓝山考核项目--基于微服务架构的豆瓣电影项目

**前情提要：首先感谢大佬能来看这个项目，项目相关的任何问题劳烦微信</br>
接口文档可以参照各个项目的端口号访问swagger文档，每一个模块（除了微信扫码登录需要指定url）都已经整合swagger</br>
数据库方面对原来的数据做了微调，使得联表查询更加简单方便**

**项目实现技术栈：**SpringCloud，Springboot，MybatisPlus,mybatis-plus-log日志，nacos服务注册，配置中心，接口远程调用，JJwt token认证，第三方登录（微信登录）（OAuth2.0），Mysql，Redis缓存，SpringCloudGateway网关，ribbon，SpringSecurity权限管理（未使用），nginx（改用Gateway），阿里云相关组件，swagger。</br>
后续可能添加：RabbitMQ，sentinel，seata分布式事务，redis集群处理

**项目结构：** </br>
本项目采用微服务架构编写，下面是各模块介绍</br>
&nbsp;&nbsp;**common：**</br>
&nbsp;&nbsp;&nbsp;&nbsp;该模块主要是项目工具和组件配置模块，下有三个子模块</br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;common_utils:&nbsp;存放工具类和统一返回结果类，包括但不限于jjwt工具，MD5密码加密</br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;service_base:&nbsp;存放插件配置类，异常处理类，包括但不限于swagger配置，redis配置（开启缓存，避免缓存雪崩等等）</br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;spring_security:&nbsp;springSecurity权限管理框架配置，token认证（未使用）</br>

&nbsp;&nbsp;**infrastructures:** </br>
&nbsp;&nbsp;&nbsp;&nbsp;该模块主要是网关设置，该项目使用spring.cloud.gateway作为网关实现服务发现统一端口访问，负载均衡，跨域处理</br>

&nbsp;&nbsp;**service:**</br>
&nbsp;&nbsp;&nbsp;&nbsp;该大型业务模块包含7个子业务模块，（因为最近有其他事时间不太够导致有部分业务省略了一些重复接口，请见谅）下面是各个模块具体功能和一些自认为重要的内容实现</br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;service_celebrites:&nbsp;影人模块，具体查看影人出演电影等接口转移至电影管理模块。</br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;service_comment:&nbsp;评论模块，包括长评短评业务，一级二级评论以实现讨论区效果，点赞效果，部分业务接口需要token认证</br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;service_film:&nbsp;电影模块，**该项目核心模块**，电影查询实现了redis缓存查询，MybatisPlus分页查询，多表查询</br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;service_msmali:&nbsp;阿里云短信服务，实现用户短信验证注册功能</br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;service_oss:&nbsp;阿里云上传图片功能，实现电影图片上传，用户头像上传功能（文件夹以日期分类）</br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;service_ucneter:&nbsp;用户模块，实现了用户jwt token验证，用户登录，用户注册，上传头像（nacos远程调用）,微信扫码登录（oAutho2）等功能，用户登出功能可以通过删除cookie中session实现。</br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;service_wanna:&nbsp;用户添加想看，看过功能

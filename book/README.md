####功能实现

---
####2020年7月
    
- 新增对象复制功能
- mybatisAop实现字段默认值
- 集成redis
- 新增借阅功能，当借阅天数过期，利用redis缓存失效监听功能，在逾期表记录日志
- 书籍编号生成规则
    
        萧十一郎 -> X -> book_no_config表中查询
        a.若表中没有prefix = X 的记录，则 prefix = X,serialNumber = 000001 bookNo = X000001
        b.若表中没有prefix = X 的记录,假设serialNumber = 000002，则 bookNo = X + serialNumber+1 即 bookNo = X000003 并更新book_no_config表的serialNumber = 000003
        
- 新增定时任务，使用乐观锁更新数据
- 集成mongo，使用aop完成用户行为日志记录，并记录mongo中
- 学生每逾期一天，需罚款0.5天
- 引入雪花算法生成id
- 新增防重复提交功能

---

####2020年8月
    
- 集成分布式定时任务
- 完成学生信息的crud --已完成
- 完成mybatis自动生成 --已完成
- 完成老师信息的crud --已完成
- 完成非校内人的crud --已完成
- 用户登录，用户信息存入 --已完成
- 使用策略模式，完成用户借书的操作 --已完成
- 使用策略模式，完成书籍的录入
- 完成数据字典的维护
- 集成rabbitmq，数据过期，通知学生还书
- 添加入参校验
- 新增baseservice，抽取常用功能
- 用户可以同时借多本书，借书时active = 1，还书时active = 0
- 根据登录名查询借书信息，将多条记录合并重新组装数据
- redis集群docker上搭建
- 添加挡板

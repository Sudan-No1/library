**1.ip地址存储到MySQL**

    a.直接存字符串
    b.存数字 入库时调用MySQL函数 INET_ATON('192.168.0.1') 查询时调用MySQL函数INET_NTOA(3232235521)
    
**2.书籍编号生成规则**

    萧十一郎 -> X -> book_no_config表中查询
        a.若表中没有prefix = X 的记录，则 prefix = X,serialNumber = 000001 bookNo = X000001
        b.若表中没有prefix = X 的记录,假设serialNumber = 000002，则 bookNo = X + serialNumber+1 即 bookNo = X000003 并更新book_no_config表的serialNumber = 000003
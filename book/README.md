**1.ip地址存储到MySQL**

    a.直接存字符串
    b.存数字 入库时调用MySQL函数 INET_ATON('192.168.0.1') 查询时调用MySQL函数INET_NTOA(3232235521)


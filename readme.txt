程序为股票头寸维护系统，支持股票持仓实时查询，并将股票交易写入mysql数据库。

1.数据准备
下单请求报文
http://localhost:8080/order?tradeID=1&version=1&quantity=50&securityCode=REL&command=INSERT&tradeMark=Buy
http://localhost:8080/order?tradeID=2&version=1&quantity=40&securityCode=ITC&command=INSERT&tradeMark=Sell
http://localhost:8080/order?tradeID=3&version=1&quantity=70&securityCode=INF&command=INSERT&tradeMark=Buy
http://localhost:8080/order?tradeID=1&version=2&quantity=60&securityCode=REL&command=UPDATE&tradeMark=Buy
http://localhost:8080/order?tradeID=2&version=2&quantity=30&securityCode=ITC&command=CANCEL&tradeMark=Buy
http://localhost:8080/order?tradeID=4&version=1&quantity=20&securityCode=INF&command=INSERT&tradeMark=Sell

股票当前持仓查询
http://localhost:8080/getRTPositions

2.单元测试类介绍
包路径com.equity.demo.ut
OrderMapperTest---为mapping测试类，主要负责数据库操作
TransControllerTest---对controller的junit测试
TransServiceTest---对service里主要方法的junit测试，主要测试并发情况下的持仓计算是否正确
ConcurrentTests---性能测试(本次没有用到)

3.设计思路：
因为是股票交易系统，所以设计原则应该是支持多并发，股市持仓行情也需要实时更新，所以需要持仓存入内存实时计算，
更新持仓时需要对当前股票的持仓加锁再进行计算。

并发的场景下，高优先级的指令可能会延迟到达，所以设计了排序列表，支持延迟交易排队机制，
待交易到达后在对持仓量进行一次合并计算，确保持仓的正确性。

4.order.sql为建表脚本。mysql的连接密码是123456



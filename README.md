# RxJava
一个小的rx demo，常见的几个操作符使用
1、普通用法
2、action——subscribe方法有一个重载版本，接受三个Action1类型的参数，分别对应	   OnNext，OnComplete， OnError函数。
3、just——创建只发出一个事件就结束的observable对象
4、lambda写法
5、from——获取数组，转换为单个元素分发
6、map——返回一个observable对象，可以实现复式链接
7、flatmap——把输入的数组映射为多个值，一次分发
8、reduce——把多个数组值组合成一个数据
9、android中异步使用
10、ViewObservable使用
11、取消订阅

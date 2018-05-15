
## 概念
### BIO
BIO(Blocking-IO), 同步阻塞IO

### NIO
NIO(New IO 或 Non-block IO)，非阻塞，是同步IO（IO多路复用）

### AIO
AIO(Asynchronous IO)，异步非阻塞IO


### 几种IO对比

| | | 同步阻塞IO（BIO）| 伪异步IO | 非阻塞IO（NIO） | 异步IO（AIO）
| - | :-: | :-: | :-: | :-: |
|客户端个数：IO线程| 1:1 | M:N（其中M可以大于N） |M:1（1个IO线程处理多个客户端连接）|M:0（不需要启动额外的IO线程，被动回调）|
|IO类型（阻塞）|阻塞IO|阻塞IO|非阻塞IO|非阻塞IO|
|IO类型（同步）|同步IO|同步IO|同步IO（IO多路复用）|异步IO|
|API使用难度|简单|简单|非常复杂|复杂|
|调试难度|简单|简单|复杂|复杂|
|可靠性|非常差|差|高|高|
|吞吐量|低|中|高|高|


## 概念
### BIO
BIO(Blocking-IO), 同步阻塞IO

### NIO
NIO(New IO 或 Non-block IO)，非阻塞，是同步IO（IO多路复用）

### AIO
AIO(Asynchronous IO)，异步非阻塞IO


### 几种IO对比

<table>
    <tr>
        <td></td>
        <td>同步阻塞IO（BIO）</td>
        <td>伪异步IO</td>
        <td>非阻塞IO（NIO）</td>
        <td>异步IO（AIO）</td>
    </tr>
    <tr>
        <td>客户端个数：IO线程</td>
        <td>1:1</td>
        <td>M:N（其中M可以大于N）</td>
        <td>M:1（1个IO线程处理多个客户端连接）</td>
        <td>M:0（不需要启动额外的IO线程，被动回调）</td>
    </tr>
    <tr>
        <td>IO类型（阻塞）</td>
        <td>阻塞IO</td>
        <td>阻塞IO</td>
        <td>非阻塞IO</td>
        <td>非阻塞IO</td>
    </tr>
    <tr>
        <td>IO类型（同步）</td>
        <td>同步IO</td>
        <td>同步IO</td>
        <td>同步IO（IO多路复用）</td>
        <td>异步IO</td>
    </tr>
    <tr>
        <td>API使用难度</td>
        <td>简单</td>
        <td>简单</td>
        <td>非常复杂</td>
        <td>复杂</td>
    </tr>
    <tr>
        <td>调试难度</td>
        <td>简单</td>
        <td>简单</td>
        <td>复杂</td>
        <td>复杂</td>
    </tr>
    <tr>
        <td>可靠性</td>
        <td>非常差</td>
        <td>差</td>
        <td>高</td>
        <td>高</td>
    </tr>
    <tr>
        <td>吞吐量</td>
        <td>低</td>
        <td>中</td>
        <td>高</td>
        <td>高</td>
    </tr>    
    
</table>

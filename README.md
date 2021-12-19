# werewolf使用快应用开发狼人杀
技术栈：后端Springboot，前端快应用，数据库mysql，使用jpa与数据库交互，使用快应用的fetch调用后端数据
注意：本项目asr为快应用1079版本，其余版本不存在，需要注释掉asr组件的使用代码
![image](https://user-images.githubusercontent.com/39187594/146669501-4f0f371b-4c58-45ae-ad29-1765e47f2030.png)
![image](https://user-images.githubusercontent.com/39187594/146669496-91ef64a5-615a-4d9d-ad91-c1aa27d2d284.png)

## game文件夹为快应用做的界面
## springboot为后端代码
## 数据库结构为
### 1、一个room表
![image](https://user-images.githubusercontent.com/39187594/146669332-cbc03fdb-2ada-4cf1-8646-1a3343ea709d.png)

### 2、一个talk表
![image](https://user-images.githubusercontent.com/39187594/146669341-f0b2b3a3-fedc-4872-a56e-902521af8407.png)
项目成果
## 1、实现界面
### 1.1、创建房间和加入房间：
输入房间号和名字创建加入房间
![image](https://user-images.githubusercontent.com/39187594/146669526-785b9186-89ee-4561-9d67-268fdd53da3a.png)
### 1.2、狼人夜晚投票：
点击头像投票，投票结束后进入下一阶段
  ![image](https://user-images.githubusercontent.com/39187594/146669532-5eef7100-6ed7-45c2-8ce7-31a407f1d660.png)

### 1.3、预言家预言：
点击头像预言，预言后，身份显示在预言栏，进入下一回合
 ![image](https://user-images.githubusercontent.com/39187594/146669535-cbc86183-f92a-479a-bc41-36e177416e03.png)
 
### 1.4、发言页面：
点击开始，录音并语音识别，点击结束发送
 
### 1.5、投票：
点击头像投票，投票数够进入下一阶段
 
### 1.6、胜利：
当只剩狼或只剩好人，游戏结束
  ## 2游戏扩展（狼羊杀-儿童版）
与上面一样的游戏流程，更换了介绍语音和图片，游戏中扮演小羊进行推理
### 2.1、创建房间和加入房间：
输入房间号和名字创建加入房间
 
### 2.2、狼人夜晚投票：
点击头像投票，投票结束后进入下一阶段
  
### 2.3、预言家预言：
点击头像预言，预言后，身份显示在预言栏，进入下一回合
 ![image](https://user-images.githubusercontent.com/39187594/146669558-5559e31e-4c27-4b6b-8869-1b6b19f05bcd.png)
![image](https://user-images.githubusercontent.com/39187594/146669559-204ef9d0-66c6-4fcc-b5f7-13cf35e267ac.png)
 
### 2.4、发言页面：
点击开始，录音并语音识别，点击结束发送
 ![image](https://user-images.githubusercontent.com/39187594/146669562-9e1dd8f5-ba96-47ea-8307-034a74f8aeda.png)

### 2.5、投票：
点击头像投票，投票数够进入下一阶段
 ![image](https://user-images.githubusercontent.com/39187594/146669564-cd704790-8172-4955-8982-0f72f0dc1ad5.png)

### 2.6、胜利：
当只剩狼或只剩好人，游戏结束
 ![image](https://user-images.githubusercontent.com/39187594/146669565-6a630fab-2921-4763-8e9e-5e506a98a9a6.png)


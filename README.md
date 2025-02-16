# PWBattle

## 项目简介

PWBattle 是一个简单的角色扮演游戏，玩家可以控制角色在游戏界面中移动。
游戏发生在一个法庭当中，在这里，你将使用“Objection!!”作为武器，扮演一名叫“成步堂”的律师，在法庭辩论中赢得胜利。

## 目录结构

- `src/`：包含所有的源代码文件。
- `src/img/`：包含项目的所有美术素材
- `lib/`：包含项目依赖的库文件及游戏输出的数据。
- `bin/`：编译后的输出文件目录。
- `.vscode/`：VS Code 的配置文件。

## 依赖管理

项目依赖的库文件放置在 `lib/` 目录中。可以在 `.vscode/settings.json` 文件中配置项目的依赖路径。

## 构建和运行

1. 打开终端，导航到项目根目录。
2. 编译项目：

    ```sh
    javac -d bin src/GameMain.java
    ```

3. 运行项目：

    ```sh
    java -cp bin GameMain.java
    ```

## 使用说明

- 启动游戏后，玩家可以使用键盘控制角色移动：
  - `W` 键：向上移动
  - `S` 键：向下移动
  - `A` 键：向左移动
  - `D` 键：向右移动
  - `J` 键：发射“子弹”（在游戏中实际表现为“Objection!!图片素材）

## 类说明

本项目包含10个类。

### GameMain

`GameMain` 类是游戏的主控制类，负责初始化游戏窗口和面板，并处理面板之间的切换。

### GamePanel

`GamePanel`类负责游戏的主要渲染和逻辑处理，包括背景、角色、子弹的绘制和碰撞检测。

<!-- ### StartPanel

`StartPanel` 类负责游戏的开始界面，等待用户点击特定按钮以开始游戏。 -->

### Character

`Character`类定义了游戏角色的基本属性和行为，是 `Lawyer` 和 `Procurator` 的父类。

### Lawyer

`Lawyer` 类继承自 `Character`，表示玩家控制的律师角色，包含射击敌人的方法。

### Procurator

`Procurator` 类继承自 `Character`，表示游戏中的检方角色，包含自动移动和射击的逻辑。

### BulletShot

`BulletShot` 类表示子弹，包含子弹的移动和碰撞检测逻辑。

### Defeat

`Defeat` 类表示角色被击中后的效果，包含角色被击中后的图像和生命时间。

### Node

`Node` 类用于保存检方角色的位置信息，便于游戏恢复时加载。

### Recorder

`Recorder` 类负责记录和保存游戏数据，包括玩家的成绩和检方角色的位置。

## 贡献指南

欢迎贡献代码或报告问题。请遵循以下步骤：

1. Fork 本仓库。
2. 创建一个新的分支：

    ```sh
    git checkout -b feature/your-feature-name
    ```

3. 提交您的更改：

    ```sh
    git commit -m 'Add some feature'
    ```

4. 推送到分支：

   ```sh
    git push origin feature/your-feature-name
    ```

5. 创建一个 Pull Request。

## 友情链接及后记

[作者个人博客](https://zuta39.github.io)

本项目历时一星期完成，完成了大部分自己预期中的功能，但仍有缺憾。例如，背景音乐的播放、加入环境因素（墙等）均因为自身能力不足及时间紧张没有实现。

# 更新时间：11-30
## 本周任务：整合前后台代码，优化前端代码，整理前台资源，美化排版
考察点包括：
1. 使用相对数值来设置layout以保证在不同的宽度下网页可以正常显示，网页内容要占满不要留白。
2. 点击左侧的树形结构，对应的内容（content.jsp）要显示在右侧。
3. 进行全文搜索，下方显示服务器返回的xml，请求的地址是/ftsearch，使用post方式提交，关键词变量为key。
4. 登陆界面可以正确跳转到主界面。登陆界面预留角色选择的下拉菜单：内容为：“士兵”，“教官”，“管理员”。
5. 所有网页请以jsp为后缀，方便添加后续脚本。
6. 配置变量请放在web.xml中。
7. 请使用UTF-8对源文件进行编码。批量转换编码方式可以使用项目中提供的【批量编码工具】。注意！！！转换之前备份！！！（否则失败了就傻逼了）。然后完了设置eclipse的编码方式也为utf-8，否则你们看到的都是乱码。

## 提交方式：
项目源码请上传到github。并且将项目打包成war发送给我，注意附上你们的用户数据表，以sql结尾。
打包方法参考：http://19900212.blog.51cto.com/2442096/1317416
提交日期：2015-12-6（周日）， 9:00 p.m.

## 后台代码使用说明：
1. 请求目录结构数据直接请求webcontent中的对应文件即可。
2. 请求全文搜索如上述第3条，注意：因为中文编码的问题，目前好像只能在chrome上使用get方式进行中文全文搜索。所以在脚本中，你们提交代码的时候记得把关键词变量用跟在fulltextSearch.java中解码方式一致的方式（其实我也不知道是什么方式）进行编码。当然如果你们有好的解决方案，可以联系我。
3. 请求内容页面请使用content.jsp，并提交模块对应的dmc（data model code）变量。在目录的json当中，那些id就是dmc。
4. 为了提高调试效率。第一次运行之后，可以将PMParser.java下的第81-85行代码注释掉以提高服务器启动速度。
5. 在部署我的代码时，注意要在数据库中手动新建一张表，名字叫db_pm。

## 如果遇到任何bug，请主动联系。



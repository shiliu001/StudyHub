


//启动redis
docker run --restart=always -p 6380:6379 --name myredis -d redis:7.0.12 

[//]: # (docker run --restart=always -p 6380:6379 --name myredis -d redis:7.0.12  --requirepass ningzaichun)

//启动mysql
docker run --restart=always -d -p 3307:3306 --name mysql -v /mysqldata/mysql/log:/var/log/mysql  -v /mysqldata/mysql/data:/var/lib/mysql  -v /mysqldata/mysql/conf:/etc/mysql -e MYSQL_ROOT_PASSWORD=shiliu   mysql:5.7
//启动nacos
docker run -d -p 8848:8848 -e MODE=standalone -v /opt/nacos/init.d/custom.properties:/home/nacos/init.d/custom.properties -v /opt/nacos/logs:/home/nacos/logs --restart always --name nacos nacos/nacos-server

//启动rabbitmq
docker run -d --hostname my-rabbit --name rabbit -p 15672:15672 -p 5673:5672 rabbitmq
//进入mq容器中
docker exec -it 容器id /bin/bash
//rabbitmq可视化插件
rabbitmq-plugins enable rabbitmq_management

//拉取jenkins
docker pull jenkins/jenkins:lts
//启动jenkins
docker run --restart=always -d -p 10240:8080 -p 10241:50000 -v /var/jenkins_mount:/var/jenkins_home   -v  /usr/local/src/maven/maven-3.8.5:/usr/local/maven   -v /usr/local/src/git/git/bin/git:/usr/local/git    -v /etc/localtime:/etc/localtime --name myjenkin 635634d93800

[//]: # (//进入容器)

[//]: # (docker exec -ti --user root b1fd5f08efd9  /bin/bash)

[//]: # (//cd var 然后进入名字带Jenkins的目录)

[//]: # (//修改hudson.model.UpdateCenter.xml中的url为https://mirrors.tuna.tsinghua.edu.cn/jenkins/updates/update-center.json)



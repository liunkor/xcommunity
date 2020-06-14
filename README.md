## X community
A community system driven by Spring Boot.
## Resources
https://spring.io/guides
https://pandao.github.io/editor.md/

## Script
mvn flyway:migrate

mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate

## Deploy project
### Dependencies
- git
- jdk
- maven
- MySQL

### Steps
- yum update
- yum install git
- mkdir App
- cd App
- git clone https://github.com/liunkr/xcommunity.git
- yum install maven
- mvn -v
- mvn compile package
- cp appliction.properties application-production.properties
- application-production.properties

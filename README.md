# nats-server-embedded
A spring wrapper of [Java-Nats-Server](https://github.com/YunaBraska/nats-server) which contains the original [Nats server](https://github.com/nats-io/nats-server)

[![Build][build_shield]][build_link]
[![Maintainable][maintainable_shield]][maintainable_link]
[![Coverage][coverage_shield]][coverage_link]
[![Issues][issues_shield]][issues_link]
[![Commit][commit_shield]][commit_link]
[![Dependencies][dependency_shield]][dependency_link]
[![License][license_shield]][license_link]
[![Central][central_shield]][central_link]
[![Tag][tag_shield]][tag_link]
[![Javadoc][javadoc_shield]][javadoc_link]
[![Size][size_shield]][size_shield]
![Label][label_shield]

[build_shield]: https://github.com/YunaBraska/nats-server-embedded/workflows/JAVA_CI/badge.svg
[build_link]: https://github.com/YunaBraska/nats-server-embedded/actions?query=workflow%3AJAVA_CI
[maintainable_shield]: https://img.shields.io/codeclimate/maintainability/YunaBraska/nats-server-embedded?style=flat-square
[maintainable_link]: https://codeclimate.com/github/YunaBraska/nats-server-embedded/maintainability
[coverage_shield]: https://img.shields.io/codeclimate/coverage/YunaBraska/nats-server-embedded?style=flat-square
[coverage_link]: https://codeclimate.com/github/YunaBraska/nats-server-embedded/test_coverage
[issues_shield]: https://img.shields.io/github/issues/YunaBraska/nats-server-embedded?style=flat-square
[issues_link]: https://github.com/YunaBraska/nats-server-embedded/commits/master
[commit_shield]: https://img.shields.io/github/last-commit/YunaBraska/nats-server-embedded?style=flat-square
[commit_link]: https://github.com/YunaBraska/nats-server-embedded/issues
[license_shield]: https://img.shields.io/github/license/YunaBraska/nats-server-embedded?style=flat-square
[license_link]: https://github.com/YunaBraska/nats-server-embedded/blob/master/LICENSE
[dependency_shield]: https://img.shields.io/librariesio/github/YunaBraska/nats-server-embedded?style=flat-square
[dependency_link]: https://libraries.io/github/YunaBraska/nats-server-embedded
[central_shield]: https://img.shields.io/maven-central/v/berlin.yuna/nats-server-embedded?style=flat-square
[central_link]:https://search.maven.org/artifact/berlin.yuna/nats-server-embedded
[tag_shield]: https://img.shields.io/github/v/tag/YunaBraska/nats-server-embedded?style=flat-square
[tag_link]: https://github.com/YunaBraska/nats-server-embedded/releases
[javadoc_shield]: https://javadoc.io/badge2/berlin.yuna/nats-server-embedded/javadoc.svg?style=flat-square
[javadoc_link]: https://javadoc.io/doc/berlin.yuna/nats-server-embedded
[size_shield]: https://img.shields.io/github/repo-size/YunaBraska/nats-server-embedded?style=flat-square
[label_shield]: https://img.shields.io/badge/Yuna-QueenInside-blueviolet?style=flat-square
[gitter_shield]: https://img.shields.io/gitter/room/YunaBraska/nats-server-embedded?style=flat-square
[gitter_link]: https://gitter.im/nats-server-embedded/Lobby

### Family

* Nats plain Java
    * [Nats-Server](https://github.com/YunaBraska/nats-server)
    * [Nats-Streaming-Server](https://github.com/YunaBraska/nats-streaming-server)
* Nats for spring boot
    * [Nats-Server-Embedded](https://github.com/YunaBraska/nats-server-embedded)
    * [Nats-Streaming-Server-Embedded](https://github.com/YunaBraska/nats-streaming-server-embedded)

### Usage

```xml

<dependency>
  <groupId>berlin.yuna</groupId>
  <artifactId>nats-server-embedded</artifactId>
  <version>2.2.44</version>
</dependency>
```

[Get latest version][central_link]

### Example

* One annotation to set up the powerful [Nats server](https://github.com/nats-io/nats-server)

```java

@SpringBootTest
@RunWith(SpringRunner.class)
@EnableNatsServer(port = 4222, natsServerConfig = {"user:admin", "pass:admin"})
public class SomeTest {
    [...]
}
```

*
See [NatsConfig](https://github.com/YunaBraska/nats-server/blob/master/src/main/java/berlin/yuna/natsserver/config/NatsConfig.java)
class for available properties
* @EnableNatsServer is also reading spring config
* @EnableNatsServer parameters are overwriting the spring properties

```yaml
nats:
  server:
    hb_fail_count: 3
```

```properties
nats.server.hb_fail_count=3
```

*
See [NatsSourceConfig](https://github.com/YunaBraska/nats-server/blob/master/src/main/java/berlin/yuna/natsserver/config/NatsSourceConfig.java)
class for optional available nats version configuration

```yaml
nats:
  source:
    url: "https://nats-mac.zip"
```

```
                                                             .,,.                                                             
                                                              ,/*.                                                            
                                                               *(/.                                                           
                                                               .(#*..                                                         
                                                               ,(#/..                                                         
                                                              ,(#(*..                                                         
                                                             ,/###/,,                                                         
                                                          ..*(#(**(((*                                                        
                                                         ,((#(/. ./##/.                                                       
                                                        ./##/,   ,(##/.                                                       
                                                        ,(((,   ./###*.                                                       
                                                        ,///.  ,(##//.                                                        
                                                         ,**,,/(#(*                                                           
                                                            ,(#(*.                                                            
                                                          ..*((*.                                                             
                                                          ,,((,                                                               
                                                          ..//.                                                               
                                                            .,.                                                               
                                                         .....,.........                                                      
                                            ..,**///(((((##############(((((((//*,..                                          
                                       .*//((#######################################((/*,.                                    
                                    ,/(###################################################/*..                                
                                .,,(########################################################((/.                              
                                ,((###########################################################(/.                             
                              .(#################################################################*                            
                             .(#.###############################################################*                           
                            ./#....###########################################################...,                          
                            .(#.....########################################################.....*                          
                            ,#.........##################################################.........#/.                         
                            *#...##.........##########################################........#...##(((//*,.                  
                            ,#..####...#............##########################..........#...####..........##/..               
                            ,#..####..###..............................................###..####...........##**               
                            .(#.#####.###....##..................................##...####.#####..#/,,,,/##..((.              
                             ,#..####..####..###....###..................####...###...###..####..#/.    .(#..((.              
                             ./#.#####.#####.####...####................#####..####.#####.#####.,    ./#..#//.              
                              ,#.#####.#####.#####..####................#####.####..##########..#/.    ,##.,,               
                               *#..######################..............#######################.#(.  .//#..##*                 
                                *##.#####################..............#####################..#(,.,/###..#(,                  
                                 **#######################............#####################...#####....##*.                   
                                   ,(#.#####################.........####################.........###//,                      
                                    *########################......######################..#######(/,..                       
                                     ,(#######################....########.############..#/,....                              
                                      ./###############.#####......#####.#############.##/**,,,.                              
                                        ,((#.###########..###......##...###########...#(/*********,.                          
                                         ,,(#.###########..............###########..###/************,..                       
                                            *(#.############.........###########..##//******************,.                    
                                              ,/#############......###########.##(/***********************,.                  
                                                .*((########.........#####.###/,...,,,,,,*******************,..               
                                                  ,,/########......#####.##((*.         ....,,,,*************,,.              
                                                     .,(##.............##(*.....,,,,,,,,,,,,,,,,,**************,              
                                                        .*###........##(/***********************************,..               
                                                            *(#...###/************************************,.                  
                                                             *(#.##//************************************,.                   
                                                              ./(*.,,********************************,,.                      
                                                                       .,************************,..                          
                                                                           ...,,,,******,,,,...                           
```
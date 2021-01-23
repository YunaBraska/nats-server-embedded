# nats-streaming-server-embedded
A spring wrapper of [Java-Nats-Streaming-Server](https://github.com/YunaBraska/nats-streaming-server) which contains the original [Nats streaming server](https://github.com/nats-io/nats-streaming-server)

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

[build_shield]: https://github.com/YunaBraska/nats-streaming-server-embedded/workflows/JAVA_CI/badge.svg
[build_link]: https://github.com/YunaBraska/nats-streaming-server-embedded/actions?query=workflow%3AJAVA_CI
[maintainable_shield]: https://img.shields.io/codeclimate/maintainability/YunaBraska/nats-streaming-server-embedded?style=flat-square
[maintainable_link]: https://codeclimate.com/github/YunaBraska/nats-streaming-server-embedded/maintainability
[coverage_shield]: https://img.shields.io/codeclimate/coverage/YunaBraska/nats-streaming-server-embedded?style=flat-square
[coverage_link]: https://codeclimate.com/github/YunaBraska/nats-streaming-server-embedded/test_coverage
[issues_shield]: https://img.shields.io/github/issues/YunaBraska/nats-streaming-server-embedded?style=flat-square
[issues_link]: https://github.com/YunaBraska/nats-streaming-server-embedded/commits/master
[commit_shield]: https://img.shields.io/github/last-commit/YunaBraska/nats-streaming-server-embedded?style=flat-square
[commit_link]: https://github.com/YunaBraska/nats-streaming-server-embedded/issues
[license_shield]: https://img.shields.io/github/license/YunaBraska/nats-streaming-server-embedded?style=flat-square
[license_link]: https://github.com/YunaBraska/nats-streaming-server-embedded/blob/master/LICENSE
[dependency_shield]: https://img.shields.io/librariesio/github/YunaBraska/nats-streaming-server-embedded?style=flat-square
[dependency_link]: https://libraries.io/github/YunaBraska/nats-streaming-server-embedded
[central_shield]: https://img.shields.io/maven-central/v/berlin.yuna/nats-streaming-server-embedded?style=flat-square
[central_link]:https://search.maven.org/artifact/berlin.yuna/nats-streaming-server-embedded
[tag_shield]: https://img.shields.io/github/v/tag/YunaBraska/nats-streaming-server-embedded?style=flat-square
[tag_link]: https://github.com/YunaBraska/nats-streaming-server-embedded/releases
[javadoc_shield]: https://javadoc.io/badge2/berlin.yuna/nats-streaming-server-embedded/javadoc.svg?style=flat-square
[javadoc_link]: https://javadoc.io/doc/berlin.yuna/nats-streaming-server-embedded
[size_shield]: https://img.shields.io/github/repo-size/YunaBraska/nats-streaming-server-embedded?style=flat-square
[label_shield]: https://img.shields.io/badge/Yuna-QueenInside-blueviolet?style=flat-square
[gitter_shield]: https://img.shields.io/gitter/room/YunaBraska/nats-streaming-server-embedded?style=flat-square
[gitter_link]: https://gitter.im/nats-streaming-server-embedded/Lobby

### Usage
* One annotation to set up the powerful [Nats streaming server](https://github.com/nats-io/nats-streaming-server)
```java
@SpringBootTest
@RunWith(SpringRunner.class)
@EnableNatsServer(port = 4222, natsServerConfig = {"user:admin", "pass:admin"})
public class SomeTest {
    [...]
}
```
* See [NatsConfig](https://github.com/YunaBraska/nats-streaming-server/blob/master/src/main/java/berlin/yuna/natsserver/config/NatsConfig.java) class for available properties
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

* See [NatsConfig](https://github.com/YunaBraska/nats-streaming-server/blob/master/src/main/java/berlin/yuna/natsserver/config/NatsConfig.java) class for optional available nats version configuration
```yaml
nats:
  source:
    mac: "https://nats-mac.zip"
    linux: "https://nats-linux.zip"
    solaris: "https://nats-solaris.zip"
    windows: "https://nats-windows.zip"
    default: "file://${user.dir}/nats-foo-bar.zip"
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
# TinkerDemo

热修复框架Tinker最完整讲解（01）——集成之路:http://m.blog.csdn.net/alpha58/article/details/74854680

热修复框架Tinker最完整讲解（02）——加入Walle多渠道打包:http://m.blog.csdn.net/Alpha58/article/details/74906630

热修复框架Tinker最完整讲解（03）——使用Tinker常见问题:http://m.blog.csdn.net/Alpha58/article/details/74907137

Walle（瓦力）多渠道打包（含加固）:http://blog.csdn.net/blf09/article/details/72782795

Gradle插件使用方式
配置build.gradle

在位于项目的根目录 build.gradle 文件中添加Walle Gradle插件的依赖， 如下：

buildscript {
    dependencies {
        classpath 'com.meituan.android.walle:plugin:1.1.5'
    }
}

并在当前App的 build.gradle 文件中apply这个插件，并添加上用于读取渠道号的AAR

apply plugin: 'walle'

dependencies {
    compile 'com.meituan.android.walle:library:1.1.5'
}

配置插件

walle {
    // 指定渠道包的输出路径
    apkOutputFolder = new File("${project.buildDir}/outputs/channels");
    // 定制渠道包的APK的文件名称
    apkFileNameFormat = '${appName}-${packageName}-${channel}-${buildType}-v${versionName}-${versionCode}-${buildTime}.apk';
    // 渠道配置文件
    channelFile = new File("${project.getProjectDir()}/channel")
}

配置项具体解释：

    apkOutputFolder：指定渠道包的输出路径， 默认值为new File("${project.buildDir}/outputs/apk")

    apkFileNameFormat：定制渠道包的APK的文件名称, 默认值为'${appName}-${buildType}-${channel}.apk'
    可使用以下变量:

         projectName - 项目名字
         appName - App模块名字
         packageName - applicationId (App包名packageName)
         buildType - buildType (release/debug等)
         channel - channel名称 (对应渠道打包中的渠道名字)
         versionName - versionName (显示用的版本号)
         versionCode - versionCode (内部版本号)
         buildTime - buildTime (编译构建日期时间)
         fileSHA1 - fileSHA1 (最终APK文件的SHA1哈希值)
         flavorName - 编译构建 productFlavors 名

    channelFile：包含渠道配置信息的文件路径。 具体内容格式详见：渠道配置文件示例，支持使用#号添加注释。

如何获取渠道信息

在需要渠道等信息时可以通过下面代码进行获取

String channel = WalleChannelReader.getChannel(this.getApplicationContext());

如何生成渠道包

生成渠道包的方式是和assemble${variantName}Channels指令结合，渠道包的生成目录默认存放在 build/outputs/apk/，也可以通过walle闭包中的apkOutputFolder参数来指定输出目录

用法示例：

    生成渠道包 ./gradlew clean assembleReleaseChannels
    支持 productFlavors ./gradlew clean assembleMeituanReleaseChannels

更多用法
插入额外信息

channelFile只支持渠道写入，如果想插入除渠道以外的其他信息，请在walle配置中使用configFile

walle {
    // 渠道&额外信息配置文件，与channelFile互斥
	configFile = new File("${project.getProjectDir()}/config.json")
}

configFile是包含渠道信息和额外信息的配置文件路径。
配置文件采用json格式，支持为每个channel单独配置额外的写入信息。具体内容格式详见：渠道&额外信息配置文件示例 。

注意：

    此配置项与channelFile功能互斥，开发者在使用时选择其一即可，两者都存在时configFile优先执行。
    extraInfo 不要出现以channel为key的情况

而对应的渠道信息获取方式如下：

ChannelInfo channelInfo= WalleChannelReader.getChannelInfo(this.getApplicationContext());
if (channelInfo != null) {
   String channel = channelInfo.getChannel();
   Map<String, String> extraInfo = channelInfo.getExtraInfo();
}
// 或者也可以直接根据key获取
String value = WalleChannelReader.get(context, "buildtime");

临时生成某渠道包

我们推荐使用channelFile/configFile配置来生成渠道包，但有时也可能有临时生成渠道包需求，这时可以使用：

    生成单个渠道包: ./gradlew clean assembleReleaseChannels -PchannelList=meituan

    生成多个渠道包: ./gradlew clean assembleReleaseChannels -PchannelList=meituan,dianping

    生成渠道包&写入额外信息:

    ./gradlew clean assembleReleaseChannels -PchannelList=meituan -PextraInfo=buildtime:20161212,hash:xxxxxxx

    注意: 这里的extraInfo以key:value形式提供，多个以,分隔。

    使用临时channelFile生成渠道包: ./gradlew clean assembleReleaseChannels -PchannelFile=/Users/xx/Documents/channel

    使用临时configFile生成渠道包: ./gradlew clean assembleReleaseChannels -PconfigFile=/Users/xx/Documents/config.json

使用上述-P参数后，本次打包channelFile/configFile配置将会失效，其他配置仍然有效。 -PchannelList,-PchannelFile, -PconfigFile三者不可同时使用。

Android Signature V2 Scheme签名下的新一代渠道包打包神器 :https://github.com/Meituan-Dianping/walle#%E6%9B%B4%E5%A4%9A%E7%94%A8%E6%B3%95
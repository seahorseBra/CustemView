# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\SDK/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript allinterface
# class:
#-keepclassmembers class fqcn.of.javascript.allinterface.for.webview {
#   public *;
#}

#-keep public class * extends android.app.Activity
#-keep public class * extends android.app.Application
#-keep public class * extends android.app.Service
#-keep public class * extends android.content.BroadcastReceiver
#-keep public class * extends android.content.ContentProvider
#-keep public class * extends android.app.backup.BackupAgentHelper
#-keep public class * extends android.preference.Preference



 # mode 混淆

-keep class testLib.** { *; }

# FastJson 混淆

# 如果是有mode的get和set方法，需要设置下面这条语句

-dontwarn com.alibaba.fastjson.**

-keep class com.alibaba.fastjson.** { *; }

-keepattributes Signature

-keepattributes *Annotation*



-keepclasseswithmembers class * {

    public <init>(android.content.Context, android.util.AttributeSet);

}

-keepclasseswithmembers class * {

    public <init>(android.content.Context, android.util.AttributeSet, int);

}

-keepclassmembers class * implements android.os.Parcelable {

    static android.os.Parcelable$Creator CREATOR;

}







-keep public class * extends android.app.Activity
-keep public class * extends com.youloft.nad.YLNAModule
-keep public class * extends com.youloft.nad.LenovoSDK
-keep public class * extends com.youloft.nad.INativeAdData
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference

#support
-dontwarn android.support.v4.**
-keep class android.support.v4.** { *; }
-keep interface android.support.v4.app.** { *; }
-keep public class * extends android.support.v4.**
-keep public class * extends android.app.Fragment

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep public class * implements java.io.Serializable {
        public *;
}
-keep public class * implements java.io.Parcelable {
        public *;
}

#okhttp
-dontwarn okio.*
#butterknife
-dontwarn butterknife.internal.**
-keep class **$$ViewInjector { *; }
-keepnames class * { @butterknife.InjectView *;}

#retrofit
-keep class com.squareup.okhttp.*
-dontwarn retrofit.**
-keep class retrofit.** { *; }

#GSON
-keep class com.google.gson.IJsonObject{*;}
-keep class com.google.gson.stream.**{*;}
-keep class * implements com.google.gson.IJsonObject{*;}


-dontwarn com.facebook.**
-keep class com.facebook.**


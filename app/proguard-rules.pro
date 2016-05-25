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

#butterknife
-dontwarn butterknife.internal.**
-keep class **$$ViewInjector { *; }
-keepnames class * { @butterknife.InjectView *;}

#retrofit
-keep class com.squareup.okhttp.*
-dontwarn retrofit.**
-keep class retrofit.** { *; }

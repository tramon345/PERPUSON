# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Firebase Database and Gson deserialization rules
# Keep the no-argument constructor and other constructors of the User class
-keepclassmembers class com.example.sonp.User {
    public <init>(...);
}

# Preserve Firebase Database classes and methods
-keep class com.google.firebase.database.** { *; }

# Keep Firebase related classes
-keep class com.google.firebase.** { *; }

# If you are using Gson for deserialization, keep these rules as well
# Gson deserialization support
-keep class com.google.gson.** { *; }
-dontwarn com.google.gson.**

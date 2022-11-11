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

 # Retrofit
 -dontwarn retrofit2.**
 -dontwarn org.codehaus.mojo.**
 -keepattributes Signature
 -keepattributes Exceptions
 -keepattributes *Annotation*

 -keepattributes RuntimeVisibleAnnotations
 -keepattributes RuntimeInvisibleAnnotations
 -keepattributes RuntimeVisibleParameterAnnotations
 -keepattributes RuntimeInvisibleParameterAnnotations

 -keepattributes EnclosingMethod

 -keepclasseswithmembers class * {
     @retrofit2.* <methods>;
 }

 -keepclasseswithmembers interface * {
     @retrofit2.* <methods>;
 }

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

-keep class retrofit2.*.** { *; }
-keep class okhttp3.internal.*.** { *; }

-dontwarn okhttp3.internal.**
-dontwarn retrofit2.**


# general setup
-dontobfuscate
-keepattributes Signature, InnerClasses, EnclosingMethod
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-dontwarn java.lang.invoke.*
-keep interface * { *; }
-dontwarn retrofit2.KotlinExtensions

-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}

-keepclasseswithmembers class * {
    @com.squareup.moshi.* <methods>;
}